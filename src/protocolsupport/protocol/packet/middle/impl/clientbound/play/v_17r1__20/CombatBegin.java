package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1__20;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleCombatBegin;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class CombatBegin extends MiddleCombatBegin implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18,
IClientboundMiddlePacketV20 {

	public CombatBegin(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData combatstartPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_COMBAT_BEGIN);
		io.writeClientbound(combatstartPacket);
	}

}
