package protocolsupport.protocol;

import java.util.concurrent.ScheduledExecutorService;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.packet.PacketDataCodec;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.pipeline.IPacketIdCodec;
import protocolsupport.utils.netty.CombinedResultChannelPromise;

public class PacketDataCodecImpl extends PacketDataCodec {

	protected final ConnectionImpl connection;
	protected final IPacketIdCodec packetIdCodec;
	protected ChannelHandlerContext transformerEncoderCtx;
	protected ChannelHandlerContext transformerDecoderCtx;

	public PacketDataCodecImpl(ConnectionImpl connection, IPacketIdCodec packetIdCodec) {
		this.connection = connection;
		this.packetIdCodec = packetIdCodec;
	}

	protected void setIOContexts(ChannelHandlerContext transformerEncoderCtx, ChannelHandlerContext transformerDecoderCtx) {
		this.transformerDecoderCtx = transformerDecoderCtx;
		this.transformerEncoderCtx = transformerEncoderCtx;
	}

	public int readPacketId(ByteBuf from) {
		return packetIdCodec.readPacketId(from);
	}

	protected ClientBoundPacketDataProcessor transformerEncoderHeadProcessor = new ClientBoundPacketDataProcessor() {
		@Override
		protected void write(PacketData<?> packet) {
			write0(packet);
		}
	};
	protected ServerBoundPacketDataProcessor transformerDecoderHeadProcessor = new ServerBoundPacketDataProcessor() {
		@Override
		protected void read(PacketData<?> packet) {
			read0(packet);
		}
	};

	/**
	 * Adds clientbound processor. Added packet processor becomes first.
	 * @param processor packet processor
	 */
	public void addClientboundPacketProcessor(ClientBoundPacketDataProcessor processor) {
		processor.attach(this);
	}

	/**
	 * Adds serverbound packet processor. Added packet processor becomes first.
	 * @param processor packet processor
	 */
	public void addServerboundPacketProcessor(ServerBoundPacketDataProcessor processor) {
		processor.attach(this);
	}

	@Override
	public void write(ClientBoundPacketData packetadata) {
		transformerEncoderHeadProcessor.process(packetadata);
	}

	@Override
	public void read(ServerBoundPacketData packetdata) {
		transformerDecoderHeadProcessor.process(packetdata);
	}

	@Override
	public void flush() {
		transformerEncoderCtx.flush();
	}

	@Override
	public void readComplete() {
		transformerDecoderCtx.fireChannelReadComplete();
	}

	protected CombinedResultChannelPromise promise;

	public void setWritePromise(CombinedResultChannelPromise promise) {
		this.promise = promise;
	}

	public void clearWritePromise() {
		this.promise = null;
	}

	protected void write0(PacketData<?> packetdata) {
		try {
			packetIdCodec.writeClientBoundPacketId(packetdata);
			CombinedResultChannelPromise combinedPromise = promise;
			if (combinedPromise != null) {
				combinedPromise.registerUsage();
				transformerEncoderCtx.write(packetdata, combinedPromise);
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

	public void release() {
		{
			ClientBoundPacketDataProcessor encodeProcessor = transformerEncoderHeadProcessor;
			do {
				encodeProcessor.release();
			} while ((encodeProcessor = encodeProcessor.next) != null);
		}
		{
			ServerBoundPacketDataProcessor decodeProcessor = transformerDecoderHeadProcessor;
			do {
				decodeProcessor.release();
			} while ((decodeProcessor = decodeProcessor.next) != null);
		}
	}


	public static class ClientBoundPacketDataProcessor {

		private PacketDataCodecImpl codec;
		private ClientBoundPacketDataProcessor next;

		private void attach(PacketDataCodecImpl codec) {
			if (this.codec != null) {
				throw new IllegalArgumentException("Already attached");
			}
			this.codec = codec;
			this.next = codec.transformerEncoderHeadProcessor;
			codec.transformerEncoderHeadProcessor = this;
		}

		protected ClientBoundPacketDataProcessor() {
		}

		protected final ScheduledExecutorService getIOExecutor() {
			return codec.connection.getIOExecutor();
		}

		/**
		 * Actually reads/writes packet
		 * @param packet packet
		 */
		protected void write(PacketData<?> packet) {
			next.process(packet);
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

		private PacketDataCodecImpl codec;
		private ServerBoundPacketDataProcessor next;

		private void attach(PacketDataCodecImpl codec) {
			if (this.codec != null) {
				throw new IllegalArgumentException("Already attached");
			}
			this.codec = codec;
			this.next = codec.transformerDecoderHeadProcessor;
			codec.transformerDecoderHeadProcessor = this;
		}

		protected ServerBoundPacketDataProcessor() {
		}

		protected final ScheduledExecutorService getIOExecutor() {
			return codec.connection.getIOExecutor();
		}

		/**
		 * Actually reads/writes packet
		 * @param packet packet
		 */
		protected void read(PacketData<?> packet) {
			next.process(packet);
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

	public static final PacketDataCodecImpl NOOP = new PacketDataCodecImpl(null, null) {
		{
			this.transformerEncoderHeadProcessor = null;
			this.transformerDecoderHeadProcessor = null;
		}
		@Override
		protected void setIOContexts(ChannelHandlerContext transformerEncoderCtx, ChannelHandlerContext transformerDecoderCtx) {
		}
		@Override
		public void setWritePromise(CombinedResultChannelPromise promise) {
		}
		@Override
		public void clearWritePromise() {
		}
		@Override
		public int readPacketId(ByteBuf from) {
			return 0;
		}
		@Override
		public void addClientboundPacketProcessor(ClientBoundPacketDataProcessor processor) {
		}
		@Override
		public void addServerboundPacketProcessor(ServerBoundPacketDataProcessor processor) {
		}
		@Override
		public void write(ClientBoundPacketData packetadata) {
		}
		@Override
		public void flush() {
		}
		@Override
		public void writeAndFlush(ClientBoundPacketData packetdata) {
		}
		@Override
		protected void write0(PacketData<?> packetdata) {
		}
		@Override
		public void read(ServerBoundPacketData packetdata) {
		}
		@Override
		public void readComplete() {
		}
		@Override
		public void readAndComplete(ServerBoundPacketData packetadata) {
		}
		@Override
		protected void read0(PacketData<?> packetdata) {
		}
		@Override
		public void release() {
		}
	};

}
