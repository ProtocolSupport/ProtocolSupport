package protocolsupport.zplatform.impl.pe;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.PlayerAction;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketDecoder;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;

import java.util.ArrayDeque;
import java.util.Queue;

//lock outbound packet stream until we get a dim switch ack
public class PEDimSwitchLock extends ChannelDuplexHandler {

	public static final String NAME = "peproxy-dimlock";

	protected static int MAX_QUEUE_SIZE = 4096;

	protected final Queue<ByteBuf> queue = new ArrayDeque<>(32);
	protected boolean isLocked = false;

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		queue.forEach(ReferenceCountUtil::safeRelease);
		queue.clear();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ByteBuf && isLocked && PlayerAction.isDimSwitchAck((ByteBuf) msg)) {
			isLocked = false;
			while (!queue.isEmpty() && !isLocked) {
				write(ctx, queue.remove(), ctx.voidPromise());
			}
			ctx.flush();
		}
		super.channelRead(ctx, msg);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (msg instanceof ByteBuf) {
			if (isLocked) {
				queue.add((ByteBuf) msg);
				promise.trySuccess();
				if (queue.size() > MAX_QUEUE_SIZE) {
					ProtocolSupport.logWarning("PEDimSwitchLock: queue got too large, closing connection.");
					ctx.channel().close();
				}
				return;
			} else if (PEPacketDecoder.sPeekPacketId((ByteBuf) msg) == PEPacketIDs.EXT_PS_AWAIT_DIM_SWITCH_ACK) {
				isLocked = true;
				return;
			}
		}
		super.write(ctx, msg, promise);
	}

}
