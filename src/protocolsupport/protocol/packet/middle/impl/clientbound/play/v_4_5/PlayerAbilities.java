package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;

public class PlayerAbilities extends MiddlePlayerAbilities implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5
{

	public PlayerAbilities(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData abilities = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_ABILITIES);
		abilities.writeByte(flags);
		abilities.writeByte((int) (flyspeed * 255.0F));
		abilities.writeByte((int) (walkspeed * 255.0F));
		io.writeClientbound(abilities);
	}

}
