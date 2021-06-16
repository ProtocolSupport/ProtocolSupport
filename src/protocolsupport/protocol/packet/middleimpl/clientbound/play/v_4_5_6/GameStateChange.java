package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

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
		switch (action) {
			case SET_RAIN_STRENGTH: {
				boolean raining = value > 0.2;
				if (clientCache.updateRain(raining)) {
					codec.writeClientbound(createRain(raining));
				}
				break;
			}
			case RAIN_START: {
				if (clientCache.updateRain(false)) {
					codec.writeClientbound(createRain(false));
				}
				break;
			}
			case RAIN_END: {
				if (clientCache.updateRain(true)) {
					codec.writeClientbound(createRain(true));
				}
				break;
			}
			default: {
				ClientBoundPacketData gamestatechange = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_GAME_STATE_CHANGE);
				MiscSerializer.writeByteEnum(gamestatechange, action);
				gamestatechange.writeByte((int) value);
				codec.writeClientbound(gamestatechange);
				break;
			}
		}
	}

	protected static ClientBoundPacketData createRain(boolean raining) {
		ClientBoundPacketData gamestatechange = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_GAME_STATE_CHANGE);
		MiscSerializer.writeByteEnum(gamestatechange, raining ? Action.RAIN_START : Action.RAIN_END);
		gamestatechange.writeByte(0);
		return gamestatechange;
	}

}
