package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.AbstractSetPosition;

public class SetPosition extends AbstractSetPosition {

	public SetPosition(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData setposition = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_POSITION);
		setposition.writeDouble(x);
		setposition.writeDouble(y + 1.6200000047683716D);
		setposition.writeDouble(y);
		setposition.writeDouble(z);
		setposition.writeFloat(yaw);
		setposition.writeFloat(pitch);
		setposition.writeBoolean(false);
		codec.write(setposition);
	}

}
