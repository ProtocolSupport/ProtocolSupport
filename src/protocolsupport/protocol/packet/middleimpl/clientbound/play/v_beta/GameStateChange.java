package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleGameStateChange;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class GameStateChange extends MiddleGameStateChange {

	public GameStateChange(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		switch (type) {
			case 1: {
				type = 2;
				break;
			}
			case 2: {
				type = 1;
				break;
			}
			default: {
				break;
			}
		}
		ClientBoundPacketData gamestatechange = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_GAME_STATE_CHANGE);
		gamestatechange.writeByte(type);
		codec.write(gamestatechange);
	}

}
