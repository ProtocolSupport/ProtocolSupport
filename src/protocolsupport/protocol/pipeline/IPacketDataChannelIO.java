package protocolsupport.protocol.pipeline;

import java.util.concurrent.ScheduledExecutorService;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.utils.netty.CombinedResultChannelPromise;

public interface IPacketDataChannelIO extends IPacketDataIO {

	public NetworkState getNetworkState();

	public int readPacketId(ByteBuf from);

	public void setWritePromise(CombinedResultChannelPromise promise);

	public void clearWritePromise();

	public void addProcessor(IPacketDataProcessor processor);


	public static interface IPacketDataProcessorContext {

		public ScheduledExecutorService getIOExecutor();

		public void writeClientbound(ClientBoundPacketData packetdata);

		public void writeServerbound(ServerBoundPacketData packetdata);

	}

	public static interface IPacketDataProcessor {

		public default void processorAdded(IPacketDataProcessorContext ctx) {
		}

		public default void processorRemoved(IPacketDataProcessorContext ctx) {
		}

		public default void processClientbound(IPacketDataProcessorContext ctx, ClientBoundPacketData packetdata) {
			ctx.writeClientbound(packetdata);
		}

		public default void processServerbound(IPacketDataProcessorContext ctx, ServerBoundPacketData packetdata) {
			ctx.writeServerbound(packetdata);
		}

	}

}
