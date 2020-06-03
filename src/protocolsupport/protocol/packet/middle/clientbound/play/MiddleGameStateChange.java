package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.storage.netcache.AttributesCache;
import protocolsupport.protocol.utils.EnumConstantLookups.EnumConstantLookup;

public abstract class MiddleGameStateChange extends ClientBoundMiddlePacket {

	protected final AttributesCache clientCache = cache.getAttributesCache();

	public MiddleGameStateChange(ConnectionImpl connection) {
		super(connection);
	}

	protected Action action;
	protected float value;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		action = MiscSerializer.readByteEnum(serverdata, Action.CONSTANT_LOOKUP);
		value = serverdata.readFloat();
	}

	@Override
	protected void handleReadData() {
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
