package protocolsupport.protocol.pipeline;

import java.util.concurrent.ScheduledExecutorService;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketData;

public interface IPacketDataIO {

	public ProtocolVersion getVersion();

	public ScheduledExecutorService getExecutor();

	public void writeClientbound(ClientBoundPacketData packetadata);

	public void writeServerbound(ServerBoundPacketData packetdata);

	public default void writeClientboundAndFlush(ClientBoundPacketData packetdata) {
		writeClientbound(packetdata);
		flushClientnbound();
	}

	public default void writeServerboundAndFlush(ServerBoundPacketData packetadata) {
		writeServerbound(packetadata);
		flushServerbound();
	}

	public void flushClientnbound();

	public void flushServerbound();

}
