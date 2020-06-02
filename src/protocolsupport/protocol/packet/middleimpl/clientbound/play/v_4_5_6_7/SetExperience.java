package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetExperience;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class SetExperience extends MiddleSetExperience {

	public SetExperience(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData setexperience = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SET_EXPERIENCE);
		setexperience.writeFloat(exp);
		setexperience.writeShort(level);
		setexperience.writeShort(totalExp);
		codec.write(setexperience);
	}

}
