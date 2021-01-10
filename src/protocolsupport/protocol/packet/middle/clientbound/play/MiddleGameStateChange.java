package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;

public abstract class MiddleGameStateChange extends ClientBoundMiddlePacket {

	protected final ClientCache clientCache = cache.getClientCache();

	public MiddleGameStateChange(MiddlePacketInit init) {
		super(init);
	}

	protected Action action;
	protected float value;

	@Override
	protected void decode(ByteBuf serverdata) {
		action = MiscSerializer.readByteEnum(serverdata, Action.CONSTANT_LOOKUP);
		value = serverdata.readFloat();
	}

	@Override
	protected void handle() {
		clientCache.setRespawnScreenEnabled(value == 0);
	}

	protected static enum Action {
		SHOW_MESSAGE_INVALID_BED,
		RAIN_END,
		RAIN_START,
		SET_GAMEMODE,
		PERFORM_EXIT_END,
		SHOW_MESSAGE_DEMO,
		PLAY_SOUND_ARROW_HIT,
		SET_THUNDER_FADE_VALUE,
		SET_THUNDER_FADE_TIME,
		PLAY_SOUND_PUFFERFISH_STING,
		PLAY_EFFECT_ELDERGUARDIAN,
		SET_RESPAWN_SCREEN_ENABLED;

		public static final EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookup<>(Action.class);
	}

}
