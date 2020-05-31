package protocolsupport.protocol.packet;

import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class PacketDataCodec {

	public abstract void write(ClientBoundPacketData packetadata);

	public abstract void read(ServerBoundPacketData packetdata);

	public void writeAndFlush(ClientBoundPacketData packetdata) {
		write(packetdata);
		flush();
	}

	public void readAndComplete(ServerBoundPacketData packetadata) {
		read(packetadata);
		readComplete();
	}

	public abstract void flush();

	public abstract void readComplete();

}
