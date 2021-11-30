package protocolsupport.protocol.pipeline.version.v_1_8;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.pipeline.version.util.codec.VarIntPacketCodec;

public class PacketCodec extends VarIntPacketCodec {

	protected static final PacketCodec instance = new PacketCodec();

	{
		registry.register(ClientBoundPacketType.LOGIN_DISCONNECT, 0x00);
		registry.register(ClientBoundPacketType.LOGIN_ENCRYPTION_BEGIN, 0x01);
		registry.register(ClientBoundPacketType.LOGIN_SUCCESS, 0x02);
		registry.register(ClientBoundPacketType.LOGIN_SET_COMPRESSION, 0x03);
		registry.register(ClientBoundPacketType.STATUS_SERVER_INFO, 0x00);
		registry.register(ClientBoundPacketType.STATUS_PONG, 0x01);
		registry.register(ClientBoundPacketType.PLAY_KEEP_ALIVE, 0x00);
		registry.register(ClientBoundPacketType.PLAY_START_GAME, 0x01);
		registry.register(ClientBoundPacketType.PLAY_CHAT, 0x02);
		registry.register(ClientBoundPacketType.PLAY_UPDATE_TIME, 0x03);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_EQUIPMENT, 0x04);
		registry.register(ClientBoundPacketType.PLAY_SPAWN_POSITION, 0x05);
		registry.register(ClientBoundPacketType.PLAY_SET_HEALTH, 0x06);
		registry.register(ClientBoundPacketType.PLAY_RESPAWN, 0x07);
		registry.register(ClientBoundPacketType.PLAY_POSITION, 0x08);
		registry.register(ClientBoundPacketType.PLAY_HELD_SLOT, 0x09);
		registry.register(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED, 0x0A);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_ANIMATION, 0x0B);
		registry.register(ClientBoundPacketType.PLAY_SPAWN_NAMED, 0x0C);
		registry.register(ClientBoundPacketType.PLAY_COLLECT_EFFECT, 0x0D);
		registry.register(ClientBoundPacketType.PLAY_SPAWN_OBJECT, 0x0E);
		registry.register(ClientBoundPacketType.PLAY_SPAWN_LIVING, 0x0F);
		registry.register(ClientBoundPacketType.PLAY_SPAWN_PAINTING, 0x10);
		registry.register(ClientBoundPacketType.PLAY_SPAWN_EXP_ORB, 0x11);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_VELOCITY, 0x12);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_DESTROY, 0x13);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_REL_MOVE, 0x15);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_LOOK, 0x16);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_REL_MOVE_LOOK, 0x17);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_TELEPORT, 0x18);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_HEAD_ROTATION, 0x19);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_STATUS, 0x1A);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_LEASH, 0x1B);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_METADATA, 0x1C);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_EFFECT_ADD, 0x1D);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_EFFECT_REMOVE, 0x1E);
		registry.register(ClientBoundPacketType.PLAY_SET_EXPERIENCE, 0x1F);
		registry.register(ClientBoundPacketType.PLAY_ENTITY_ATTRIBUTES, 0x20);
		registry.register(ClientBoundPacketType.PLAY_CHUNK_DATA, 0x21);
		registry.register(ClientBoundPacketType.PLAY_BLOCK_CHANGE_MULTI, 0x22);
		registry.register(ClientBoundPacketType.PLAY_BLOCK_CHANGE_SINGLE, 0x23);
		registry.register(ClientBoundPacketType.PLAY_BLOCK_ACTION, 0x24);
		registry.register(ClientBoundPacketType.PLAY_BLOCK_BREAK_ANIMATION, 0x25);
		registry.register(ClientBoundPacketType.PLAY_EXPLOSION, 0x27);
		registry.register(ClientBoundPacketType.PLAY_WORLD_EVENT, 0x28);
		registry.register(ClientBoundPacketType.PLAY_WORLD_CUSTOM_SOUND, 0x29);
		registry.register(ClientBoundPacketType.PLAY_WORLD_PARTICLES, 0x2A);
		registry.register(ClientBoundPacketType.PLAY_GAME_STATE_CHANGE, 0x2B);
		registry.register(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_SPAWN_GLOBAL, 0x2C);
		registry.register(ClientBoundPacketType.PLAY_WINDOW_OPEN, 0x2D);
		registry.register(ClientBoundPacketType.PLAY_WINDOW_CLOSE, 0x2E);
		registry.register(ClientBoundPacketType.PLAY_WINDOW_SET_SLOT, 0x2F);
		registry.register(ClientBoundPacketType.PLAY_WINDOW_SET_ITEMS, 0x30);
		registry.register(ClientBoundPacketType.PLAY_WINDOW_DATA, 0x31);
		registry.register(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_WINDOW_TRANSACTION, 0x32);
		registry.register(ClientBoundPacketType.CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN, 0x33);
		registry.register(ClientBoundPacketType.PLAY_UPDATE_MAP, 0x34);
		registry.register(ClientBoundPacketType.PLAY_BLOCK_TILE, 0x35);
		registry.register(ClientBoundPacketType.PLAY_SIGN_EDITOR, 0x36);
		registry.register(ClientBoundPacketType.PLAY_STATISTICS, 0x37);
		registry.register(ClientBoundPacketType.PLAY_PLAYER_INFO, 0x38);
		registry.register(ClientBoundPacketType.PLAY_PLAYER_ABILITIES, 0x39);
		registry.register(ClientBoundPacketType.PLAY_TAB_COMPLETE, 0x3A);
		registry.register(ClientBoundPacketType.PLAY_SCOREBOARD_OBJECTIVE, 0x3B);
		registry.register(ClientBoundPacketType.PLAY_SCOREBOARD_SCORE, 0x3C);
		registry.register(ClientBoundPacketType.PLAY_SCOREBOARD_DISPLAY_SLOT, 0x3D);
		registry.register(ClientBoundPacketType.PLAY_SCOREBOARD_TEAM, 0x3E);
		registry.register(ClientBoundPacketType.PLAY_CUSTOM_PAYLOAD, 0x3F);
		registry.register(ClientBoundPacketType.PLAY_KICK_DISCONNECT, 0x40);
		registry.register(ClientBoundPacketType.PLAY_SERVER_DIFFICULTY, 0x41);
		registry.register(ClientBoundPacketType.PLAY_COMBAT_BEGIN, 0x42);
		registry.register(ClientBoundPacketType.PLAY_COMBAT_END, 0x42);
		registry.register(ClientBoundPacketType.PLAY_COMBAT_DEATH, 0x42);
		registry.register(ClientBoundPacketType.PLAY_CAMERA, 0x43);
		registry.register(ClientBoundPacketType.PLAY_WORLDBORDER_INIT, 0x44);
		registry.register(ClientBoundPacketType.PLAY_WORLDBORDER_CENTER, 0x44);
		registry.register(ClientBoundPacketType.PLAY_WORLDBORDER_LERP_SIZE, 0x44);
		registry.register(ClientBoundPacketType.PLAY_WORLDBORDER_SIZE, 0x44);
		registry.register(ClientBoundPacketType.PLAY_WORLDBORDER_WARN_DELAY, 0x44);
		registry.register(ClientBoundPacketType.PLAY_WORLDBORDER_WARN_DISTANCE, 0x44);
		registry.register(ClientBoundPacketType.PLAY_TITLE_TEXT, 0x45);
		registry.register(ClientBoundPacketType.PLAY_TITLE_SUBTEXT, 0x45);
		registry.register(ClientBoundPacketType.PLAY_TITLE_ANIMATION, 0x45);
		registry.register(ClientBoundPacketType.PLAY_TITLE_CLEAR, 0x45);
		registry.register(ClientBoundPacketType.PLAY_PLAYER_LIST_HEADER_FOOTER, 0x47);
		registry.register(ClientBoundPacketType.PLAY_RESOURCE_PACK, 0x48);
	}

}
