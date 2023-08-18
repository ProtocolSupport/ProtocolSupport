package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleCombatEnd;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class CombatEnd extends MiddleCombatEnd
implements IClientboundMiddlePacketV20 {

	public CombatEnd(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData combatendPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COMBAT_END);
		VarNumberCodec.writeVarInt(combatendPacket, duration);
		io.writeClientbound(combatendPacket);
	}

}
