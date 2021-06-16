package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatEnd;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class CombatEnd extends MiddleCombatEnd {

	public CombatEnd(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData combatendPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COMBAT_END);
		VarNumberSerializer.writeVarInt(combatendPacket, duration);
		combatendPacket.writeInt(opponentId);
		codec.writeClientbound(combatendPacket);
	}

}
