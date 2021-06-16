package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatBegin;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class CombatBegin extends MiddleCombatBegin {

	public CombatBegin(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData combatstartPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COMBAT_BEGIN);
		VarNumberSerializer.writeVarInt(combatstartPacket, 0); //action (0 - start)
		codec.writeClientbound(combatstartPacket);
	}

}
