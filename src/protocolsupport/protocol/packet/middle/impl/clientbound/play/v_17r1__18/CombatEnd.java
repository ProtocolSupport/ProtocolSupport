package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__18;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleCombatEnd;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;

public class CombatEnd extends MiddleCombatEnd implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public CombatEnd(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData combatendPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COMBAT_END);
		VarNumberCodec.writeVarInt(combatendPacket, duration);
		combatendPacket.writeInt(0); //opponent id
		io.writeClientbound(combatendPacket);
	}

}
