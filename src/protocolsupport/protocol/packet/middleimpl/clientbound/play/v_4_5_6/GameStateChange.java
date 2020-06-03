package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleGameStateChange;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;

public class GameStateChange extends MiddleGameStateChange {

	public GameStateChange(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData gamestatechange = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_GAME_STATE_CHANGE);
		MiscSerializer.writeByteEnum(gamestatechange, action);
		gamestatechange.writeByte((int) value);
		codec.write(gamestatechange);
	}

}
