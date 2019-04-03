package protocolsupport.zplatform.impl.pe;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.PlayerAction;

import java.util.ArrayList;

// ... this evil fucking class
public class PEDimSwitchLock extends ChannelDuplexHandler {

	public static final String AWAIT_DIM_ACK_MESSAGE = "ps:dimlock";

	protected boolean isLocked = false;
	protected ArrayList<ByteBuf> queue = new ArrayList<>(32);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ByteBuf) {
			if(isLocked && PlayerAction.isDimSwitchAck((ByteBuf) msg)) {
				final ArrayList<ByteBuf> qCopy = new ArrayList(queue);
				queue.clear();
				queue.trimToSize();
				isLocked = false;
				qCopy.stream().forEach(ctx::write);
				ctx.flush();
			}

		}
		super.channelRead(ctx, msg);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (msg instanceof ByteBuf) {
			if (isLocked) {
				queue.add((ByteBuf) msg);
				promise.trySuccess();
				return;
			} else if (CustomPayload.isTag((ByteBuf) msg, AWAIT_DIM_ACK_MESSAGE)) {
				isLocked = true;
			}
		}
		super.write(ctx, msg, promise);
	}

}
