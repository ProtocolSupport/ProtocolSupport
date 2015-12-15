package protocolsupport.protocol.transformer.middlepacketimpl;

import protocolsupport.protocol.PacketDataSerializer;

public class PacketData {

	private final int packetId;
	private final PacketDataSerializer data;
	public PacketData(int packetId, PacketDataSerializer data) {
		this.packetId = packetId;
		this.data = data;
	}

	public int getPacketId() {
		return packetId;
	}

	public PacketDataSerializer getData() {
		return data;
	}

}
