package protocolsupport.protocol.packet.middle.serverbound.status;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddlePing extends ServerBoundMiddlePacket {

	public MiddlePing(ConnectionImpl connection) {
		super(connection);
	}

	protected long pingId;

	@Override
	public void writeToServer() {
		ServerBoundPacketData ping = ServerBoundPacketData.create(PacketType.SERVERBOUND_STATUS_PING);
		ping.writeLong(pingId);
		codec.read(ping);
	}

}
