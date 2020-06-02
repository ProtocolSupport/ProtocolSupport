package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class PlayerAbilities extends MiddlePlayerAbilities {

	public PlayerAbilities(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData playerabilities = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_PLAYER_ABILITIES);
		playerabilities.writeByte(flags);
		playerabilities.writeFloat(flyspeed);
		playerabilities.writeFloat(walkspeed);
		codec.write(playerabilities);
	}

}
