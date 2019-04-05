package protocolsupport.zplatform.impl.pe;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.ChangeDimension;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Chunk;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe.PlayerAction;

import java.util.ArrayList;

//lock outbound packet stream until we get a dim switch ack
public class PEDimSwitchLock extends ChannelDuplexHandler {

	public static final String NAME = "peproxy-dimlock";

	protected static int MAX_QUEUE_SIZE = 4096;
	protected static int LOCK_AFTER_N_CHUNKS = 9;

	protected final ArrayList<ByteBuf> queue = new ArrayList<>(128);
	protected State state = State.IDLE;
	protected int nChunks = 0;

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		queue.forEach(ReferenceCountUtil::safeRelease);
		queue.clear();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ByteBuf && state == State.LOCKED && PlayerAction.isDimSwitchAck((ByteBuf) msg)) {
			final ArrayList<ByteBuf> qCopy = new ArrayList(queue);
			queue.clear();
			queue.trimToSize();
			state = State.IDLE;
			for (ByteBuf data : qCopy) {
				write(ctx, data, ctx.voidPromise());
			}
			ctx.flush();
		}
		super.channelRead(ctx, msg);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (msg instanceof ByteBuf) {
			final ByteBuf buf = (ByteBuf) msg;
			//lock after observing the pattern: dimchange -> pubupdate -> n chunks
			switch (state) {
				case IDLE: {
					if (ChangeDimension.isChangeDimension(buf)) {
						state = State.DIM_CHANGE;
					}
					break;
				}
				case DIM_CHANGE: {
					if (Chunk.isChunkPublisherUpdate(buf)) {
						state = State.CHUNKS;
						nChunks = 0;
					} else {
						warn("unexpected packet sequence.");
						state = State.IDLE;
					}
					break;
				}
				case CHUNKS: {
					if (!Chunk.isChunk(buf)) {
						warn("unexpected packet sequence.");
						state = State.IDLE;
					} else if (++nChunks >= LOCK_AFTER_N_CHUNKS) {
						state = State.LOCKED;
					}
					break;
				}
				case LOCKED: {
					queue.add(buf);
					promise.trySuccess();
					if (queue.size() > MAX_QUEUE_SIZE) {
						warn("queue got too large, closing connection.");
						ctx.channel().close();
					}
					return;
				}
				default:
					throw new RuntimeException("unknown PEDimSwitchLock state " + state);
			}
		}
		super.write(ctx, msg, promise);
	}

	protected void warn(String msg) {
		ProtocolSupport.logWarning("PEDimSwitchLock: " + msg);
	}

	enum State {
			IDLE, DIM_CHANGE, CHUNKS, LOCKED
	}

}
