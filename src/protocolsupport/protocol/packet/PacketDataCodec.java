package protocolsupport.protocol.packet;

import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class PacketDataCodec {

	public abstract void writeClientbound(ClientBoundPacketData packetadata);

	public abstract void writeServerbound(ServerBoundPacketData packetdata);

	public void writeClientboundAndFlush(ClientBoundPacketData packetdata) {
		writeClientbound(packetdata);
		flushClientnbound();
	}

	public void writeServerboundAndFlush(ServerBoundPacketData packetadata) {
		writeServerbound(packetadata);
		flushServerbound();
	}

	public abstract void flushClientnbound();

	public abstract void flushServerbound();

}
