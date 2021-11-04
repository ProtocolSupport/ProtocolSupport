package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.utils.EnumConstantLookup;

public abstract class MiddleGameStateChange extends ClientBoundMiddlePacket {

	protected final ClientCache clientCache = cache.getClientCache();

	protected MiddleGameStateChange(IMiddlePacketInit init) {
		super(init);
	}

	protected Action action;
	protected float value;

	@Override
	protected void decode(ByteBuf serverdata) {
		action = MiscDataCodec.readByteEnum(serverdata, Action.CONSTANT_LOOKUP);
		value = serverdata.readFloat();
	}

	@Override
	protected void handle() {
		if (action == Action.SET_RESPAWN_SCREEN_ENABLED) {
			clientCache.setRespawnScreenEnabled(value == 0);
		}
	}

	protected enum Action {
		SHOW_MESSAGE_INVALID_BED,
		RAIN_START,
		RAIN_END,
		SET_GAMEMODE,
		PERFORM_EXIT_END,
		SHOW_MESSAGE_DEMO,
		PLAY_SOUND_ARROW_HIT,
		SET_RAIN_STRENGTH,
		SET_THUNDER_STRENGTH,
		PLAY_SOUND_PUFFERFISH_STING,
		PLAY_EFFECT_ELDERGUARDIAN,
		SET_RESPAWN_SCREEN_ENABLED;

		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

}
