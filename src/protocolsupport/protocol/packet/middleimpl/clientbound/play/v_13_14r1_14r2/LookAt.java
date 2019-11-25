package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleLookAt;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class LookAt extends MiddleLookAt {

	public LookAt(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData lookat = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_LOOK_AT);
		lookat.writeBytes(data);
		codec.write(lookat);
	}

}
