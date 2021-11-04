package protocolsupport.protocol;

import java.util.concurrent.ScheduledExecutorService;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.pipeline.IPacketDataChannelIO;
import protocolsupport.protocol.pipeline.IPacketIdCodec;
import protocolsupport.utils.netty.CombinedResultChannelPromise;

public class PacketDataChannelIO implements IPacketDataChannelIO {

	protected final Connection connection;
	protected IPacketIdCodec packetIdCodec;
	protected ChannelHandlerContext clientboundCtx;
	protected ChannelHandlerContext serverboundCtx;
	protected PacketDataProcessorContext headProcessorCtx;

	public PacketDataChannelIO(Connection connection) {
		this.connection = connection;
		this.headProcessorCtx = new PacketDataProcessorContext(getExecutor(), new IPacketDataProcessor() {
			@Override
			public void processClientbound(IPacketDataProcessorContext ctx, ClientBoundPacketData packetdata) {
				writeClientboundInternal(packetdata);
			}
			@Override
			public void processServerbound(IPacketDataProcessorContext ctx, ServerBoundPacketData packetdata) {
				writeServerboundInternal(packetdata);
			}
		}, null);
	}

	public void init(IPacketIdCodec packetIdCodec, ChannelHandlerContext clientboundCtx, ChannelHandlerContext serverboundCtx) {
		this.packetIdCodec = packetIdCodec;
		this.clientboundCtx = clientboundCtx;
		this.serverboundCtx = serverboundCtx;
	}

	@Override
	public ProtocolVersion getVersion() {
		return connection.getVersion();
	}

	@Override
	public ScheduledExecutorService getExecutor() {
		return connection.getIOExecutor();
	}

	@Override
	public NetworkState getNetworkState() {
		return connection.getNetworkState();
	}

	@Override
	public int readPacketId(ByteBuf from) {
		return packetIdCodec.readServerBoundPacketId(from);
	}

	@Override
	public void addProcessor(IPacketDataProcessor processor) {
		headProcessorCtx = new PacketDataProcessorContext(getExecutor(), processor, headProcessorCtx);
		processor.processorAdded(headProcessorCtx);
	}

	@Override
	public void writeClientbound(ClientBoundPacketData packetadata) {
		headProcessorCtx.invokeProcessorClientbound(packetadata);
	}

	@Override
	public void writeServerbound(ServerBoundPacketData packetdata) {
		headProcessorCtx.invokeProcessorServerbound(packetdata);
	}

	@Override
	public void flushClientnbound() {
		clientboundCtx.flush();
	}

	@Override
	public void flushServerbound() {
		serverboundCtx.fireChannelReadComplete();
	}

	protected CombinedResultChannelPromise promise;

	@Override
	public void setWritePromise(CombinedResultChannelPromise promise) {
		this.promise = promise;
	}

	@Override
	public void clearWritePromise() {
		this.promise = null;
	}

	protected void writeClientboundInternal(ClientBoundPacketData packetdata) {
		try {
			packetIdCodec.writeClientBoundPacketId(packetdata);
			CombinedResultChannelPromise combinedPromise = promise;
			if (combinedPromise != null) {
				combinedPromise.registerUsage();
				clientboundCtx.write(packetdata, combinedPromise);
			} else {
				clientboundCtx.write(packetdata, clientboundCtx.voidPromise());
			}
		} catch (Throwable t) {
			ReferenceCountUtil.safeRelease(packetdata);
			throw new EncoderException(t);
		}
	}

	protected void writeServerboundInternal(ServerBoundPacketData packetdata) {
		try {
			IPacketIdCodec.writeServerBoundPacketId(packetdata);
			serverboundCtx.fireChannelRead(packetdata);
		} catch (Throwable t) {
			ReferenceCountUtil.safeRelease(packetdata);
			throw new EncoderException(t);
		}
	}

	public void destroy() {
		PacketDataProcessorContext ctx = headProcessorCtx;
		do {
			ctx.processor.processorRemoved(ctx);
		} while ((ctx = ctx.next) != null);
	}

	protected static class PacketDataProcessorContext implements IPacketDataProcessorContext {

		protected final ScheduledExecutorService executor;
		protected final IPacketDataProcessor processor;
		protected final PacketDataProcessorContext next;

		public PacketDataProcessorContext(ScheduledExecutorService executor, IPacketDataProcessor processor, PacketDataProcessorContext next) {
			this.executor = executor;
			this.processor = processor;
			this.next = next;
		}

		@Override
		public ScheduledExecutorService getIOExecutor() {
			return executor;
		}

		@Override
		public void writeClientbound(ClientBoundPacketData packetdata) {
			next.invokeProcessorClientbound(packetdata);
		}

		@Override
		public void writeServerbound(ServerBoundPacketData packetdata) {
			next.invokeProcessorServerbound(packetdata);
		}

		protected void invokeProcessorClientbound(ClientBoundPacketData packetdata) {
			processor.processClientbound(this, packetdata);
		}

		protected void invokeProcessorServerbound(ServerBoundPacketData packetdata) {
			processor.processServerbound(this, packetdata);
		}

	}

}
