package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetExperience;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class SetExperience extends MiddleSetExperience {

	public SetExperience(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData setexperience = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SET_EXPERIENCE);
		setexperience.writeFloat(exp);
		VarNumberCodec.writeVarInt(setexperience, level);
		VarNumberCodec.writeVarInt(setexperience, totalExp);
		codec.writeClientbound(setexperience);
	}

}
