package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class PlayerAbilities extends MiddlePlayerAbilities {

	public PlayerAbilities(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData abilities = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_PLAYER_ABILITIES);
		abilities.writeByte(flags);
		abilities.writeByte((int) (flyspeed * 255.0F));
		abilities.writeByte((int) (walkspeed * 255.0F));
		codec.write(abilities);
	}

}
