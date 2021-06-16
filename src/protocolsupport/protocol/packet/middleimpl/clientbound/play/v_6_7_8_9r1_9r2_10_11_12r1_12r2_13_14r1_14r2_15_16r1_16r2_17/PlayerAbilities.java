package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class PlayerAbilities extends MiddlePlayerAbilities {

	public PlayerAbilities(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData playerabilities = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_PLAYER_ABILITIES);
		playerabilities.writeByte(flags);
		playerabilities.writeFloat(flyspeed);
		playerabilities.writeFloat(walkspeed);
		codec.writeClientbound(playerabilities);
	}

}
