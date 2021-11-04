package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleSetExperience;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;

public class SetExperience extends MiddleSetExperience implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public SetExperience(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData setexperience = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_SET_EXPERIENCE);
		setexperience.writeFloat(exp);
		setexperience.writeShort(level);
		setexperience.writeShort(totalExp);
		io.writeClientbound(setexperience);
	}

}
