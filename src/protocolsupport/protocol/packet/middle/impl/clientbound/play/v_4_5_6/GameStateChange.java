package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleGameStateChange;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;

public class GameStateChange extends MiddleGameStateChange implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	public GameStateChange(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		switch (action) {
			case SET_RAIN_STRENGTH: {
				boolean raining = value > 0.2;
				if (clientCache.updateRain(raining)) {
					io.writeClientbound(createRain(raining));
				}
				break;
			}
			case RAIN_START: {
				if (clientCache.updateRain(false)) {
					io.writeClientbound(createRain(false));
				}
				break;
			}
			case RAIN_END: {
				if (clientCache.updateRain(true)) {
					io.writeClientbound(createRain(true));
				}
				break;
			}
			default: {
				ClientBoundPacketData gamestatechange = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_GAME_STATE_CHANGE);
				MiscDataCodec.writeByteEnum(gamestatechange, action);
				gamestatechange.writeByte((int) value);
				io.writeClientbound(gamestatechange);
				break;
			}
		}
	}

	protected static ClientBoundPacketData createRain(boolean raining) {
		ClientBoundPacketData gamestatechange = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_GAME_STATE_CHANGE);
		MiscDataCodec.writeByteEnum(gamestatechange, raining ? Action.RAIN_START : Action.RAIN_END);
		gamestatechange.writeByte(0);
		return gamestatechange;
	}

}
