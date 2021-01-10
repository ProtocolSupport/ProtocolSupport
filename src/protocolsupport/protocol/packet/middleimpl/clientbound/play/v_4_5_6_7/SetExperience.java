package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetExperience;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class SetExperience extends MiddleSetExperience {

	public SetExperience(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData setexperience = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SET_EXPERIENCE);
		setexperience.writeFloat(exp);
		setexperience.writeShort(level);
		setexperience.writeShort(totalExp);
		codec.writeClientbound(setexperience);
	}

}
