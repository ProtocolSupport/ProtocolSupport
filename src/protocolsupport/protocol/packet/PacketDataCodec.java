package protocolsupport.protocol.packet;

import java.util.function.Consumer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.pipeline.IPacketIdCodec;

public class PacketDataCodec {

	protected final ConnectionImpl connection;
	protected final IPacketIdCodec packetIdCodec;
	protected final ChannelHandlerContext transformerEncoderCtx;
	protected final ChannelHandlerContext transformerDecoderCtx;
	public PacketDataCodec(ConnectionImpl connection, IPacketIdCodec packetIdCodec, ChannelHandlerContext transformerEncoderCtx, ChannelHandlerContext transformerDecoderCtx) {
		this.connection = connection;
		this.packetIdCodec = packetIdCodec;
		this.transformerDecoderCtx = transformerDecoderCtx;
		this.transformerEncoderCtx = transformerEncoderCtx;
	}

	public ConnectionImpl getConnection() {
		return connection;
	}

	public int readPacketId(ByteBuf from) {
		return packetIdCodec.readPacketId(from);
	}

	protected ChannelPromise transformerEncoderCurrentRealChannelPromise;

	public <T> void channelWrite(ChannelPromise promise, T t, Consumer<T> consumerT) {
		transformerEncoderCurrentRealChannelPromise = promise;
		try {
			consumerT.accept(t);
		} finally {
			if (transformerEncoderCurrentRealChannelPromise != null) {
				ChannelPromise lPromise = transformerEncoderCurrentRealChannelPromise;
				transformerEncoderCurrentRealChannelPromise = null;
				lPromise.setSuccess();
			}
		}
	}

	protected ClientBoundPacketDataProcessor transformerEncoderHeadProcessor = new ClientBoundPacketDataProcessor(this);
	protected ServerBoundPacketDataProcessor transformerDecoderHeadProcessor = new ServerBoundPacketDataProcessor(this);

	/**
	 * Adds clientbound processor. Added packet processor becomes first.
	 * @param processor packet processor
	 */
	public void addClientboundPacketProcessor(ClientBoundPacketDataProcessor processor) {
		processor.next = transformerEncoderHeadProcessor;
		transformerEncoderHeadProcessor = processor;
	}

	/**
	 * Adds serverbound packet processor. Added packet processor becomes first.
	 * @param processor packet processor
	 */
	public void addServerboundPacketProcessor(ServerBoundPacketDataProcessor processor) {
		processor.next = transformerDecoderHeadProcessor;
		transformerDecoderHeadProcessor = processor;
	}

	public void write(ClientBoundPacketData packetadata) {
		transformerEncoderHeadProcessor.process(packetadata);
	}

	public void read(ServerBoundPacketData packetdata) {
		transformerDecoderHeadProcessor.process(packetdata);
	}

	public void writeAndFlush(ClientBoundPacketData packetdata) {
		write(packetdata);
		flush();
	}

	public void readAndComplete(ServerBoundPacketData packetadata) {
		read(packetadata);
		readComplete();
	}

	public void flush() {
		transformerEncoderCtx.flush();
	}

	public void readComplete() {
		transformerDecoderCtx.fireChannelReadComplete();
	}

	public void release() {
		transformerEncoderHeadProcessor.release0();
		transformerDecoderHeadProcessor.release0();
	}


	protected void write0(PacketData<?> packetdata) {
		try {
			packetIdCodec.writeClientBoundPacketId(packetdata);
			if (transformerEncoderCurrentRealChannelPromise != null) {
				ChannelPromise promise = transformerEncoderCurrentRealChannelPromise;
				transformerEncoderCurrentRealChannelPromise = null;
				transformerEncoderCtx.write(packetdata, promise);
			} else {
				transformerEncoderCtx.write(packetdata, transformerEncoderCtx.voidPromise());
			}
		} catch (Throwable t) {
			ReferenceCountUtil.safeRelease(packetdata);
			throw new EncoderException(t);
		}
	}

	protected void read0(PacketData<?> packetdata) {
		try {
			packetIdCodec.writeServerBoundPacketId(packetdata);
			transformerDecoderCtx.fireChannelRead(packetdata);
		} catch (Throwable t) {
			ReferenceCountUtil.safeRelease(packetdata);
			throw new EncoderException(t);
		}
	}


	public static class ClientBoundPacketDataProcessor {

		protected final PacketDataCodec codec;
		public ClientBoundPacketDataProcessor(PacketDataCodec connection) {
			this.codec = connection;
		}

		private ClientBoundPacketDataProcessor next;

		private void release0() {
			release();
			if (next != null) {
				next.release0();
			}
		}

		/**
		 * Actually reads/writes packet
		 * @param packet packet
		 * @param promise
		 */
		protected final void write(PacketData<?> packet) {
			if (next != null) {
				next.process(packet);
			} else {
				codec.write0(packet);
			}
		}

		/**
		 * Processes data that was created as a result of clientbound packet transformation
		 * @param packet packet
		 */
		protected void process(PacketData<?> packet) {
			write(packet);
		}

		/**
		 * Called after connection close and netty pipeline destroy
		 */
		protected void release() {
		}

	}

	public static class ServerBoundPacketDataProcessor {

		protected final PacketDataCodec codec;
		public ServerBoundPacketDataProcessor(PacketDataCodec connection) {
			this.codec = connection;
		}

		private ServerBoundPacketDataProcessor next;

		private void release0() {
			release();
			if (next != null) {
				next.release0();
			}
		}

		/**
		 * Actually reads/writes packet
		 * @param packet packet
		 */
		protected final void read(PacketData<?> packet) {
			if (next != null) {
				next.process(packet);
			} else {
				codec.read0(packet);
			}
		}

		/**
		 * Processes data that was created as a result of serverbound packet transformation.
		 * @param packet packet
		 */
		protected void process(PacketData<?> packet) {
			read(packet);
		}

		/**
		 * Called after connection close and netty pipeline destroy
		 */
		protected void release() {
		}

	}

}
