package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleGameStateChange;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;

public class GameStateChange extends MiddleGameStateChange {

	public GameStateChange(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData gamestatechange = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_GAME_STATE_CHANGE);
		MiscSerializer.writeByteEnum(gamestatechange, action);
		gamestatechange.writeFloat(value);
		codec.writeClientbound(gamestatechange);
	}

}
