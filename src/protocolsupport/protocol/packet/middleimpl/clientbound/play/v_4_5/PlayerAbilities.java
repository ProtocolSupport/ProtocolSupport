package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class PlayerAbilities extends MiddlePlayerAbilities {

	public PlayerAbilities(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData abilities = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_PLAYER_ABILITIES);
		abilities.writeByte(flags);
		abilities.writeByte((int) (flyspeed * 255.0F));
		abilities.writeByte((int) (walkspeed * 255.0F));
		codec.writeClientbound(abilities);
	}

}
