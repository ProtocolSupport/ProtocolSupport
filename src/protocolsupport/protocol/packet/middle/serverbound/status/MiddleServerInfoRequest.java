package protocolsupport.protocol.packet.middle.serverbound.status;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleServerInfoRequest extends ServerBoundMiddlePacket {

	public MiddleServerInfoRequest(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToServer() {
		codec.read(ServerBoundPacketData.create(PacketType.SERVERBOUND_STATUS_REQUEST));
	}

}
