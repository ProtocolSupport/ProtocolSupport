package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleCombatEnd;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class CombatEnd extends MiddleCombatEnd {

	public CombatEnd(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData combatendPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COMBAT_END);
		VarNumberCodec.writeVarInt(combatendPacket, duration);
		combatendPacket.writeInt(opponentId);
		codec.writeClientbound(combatendPacket);
	}

}
