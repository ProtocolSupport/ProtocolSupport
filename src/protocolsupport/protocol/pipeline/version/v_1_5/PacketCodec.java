package protocolsupport.protocol.pipeline.version.v_1_5;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.pipeline.version.util.codec.LegacyPacketCodec;

public class PacketCodec extends LegacyPacketCodec {

	protected static final PacketCodec instance = new PacketCodec();

	{
		registry.register(PacketType.CLIENTBOUND_LOGIN_DISCONNECT, 0xFF);
		registry.register(PacketType.CLIENTBOUND_LOGIN_ENCRYPTION_BEGIN, 0xFD);
		registry.register(PacketType.CLIENTBOUND_STATUS_SERVER_INFO, 0xFF);
		registry.register(PacketType.CLIENTBOUND_PLAY_KEEP_ALIVE, 0x00);
		registry.register(PacketType.CLIENTBOUND_PLAY_START_GAME, 0x01);
		registry.register(PacketType.CLIENTBOUND_PLAY_CHAT, 0x03);
		registry.register(PacketType.CLIENTBOUND_PLAY_UPDATE_TIME, 0x04);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_EQUIPMENT, 0x05);
		registry.register(PacketType.CLIENTBOUND_PLAY_SPAWN_POSITION, 0x06);
		registry.register(PacketType.CLIENTBOUND_PLAY_SET_HEALTH, 0x08);
		registry.register(PacketType.CLIENTBOUND_PLAY_RESPAWN, 0x09);
		registry.register(PacketType.CLIENTBOUND_PLAY_POSITION, 0x0D);
		registry.register(PacketType.CLIENTBOUND_PLAY_HELD_SLOT, 0x10);
		registry.register(PacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED_ID, 0x11);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_ANIMATION, 0x12);
		registry.register(PacketType.CLIENTBOUND_PLAY_SPAWN_NAMED, 0x14);
		registry.register(PacketType.CLIENTBOUND_PLAY_COLLECT_EFFECT, 0x16);
		registry.register(PacketType.CLIENTBOUND_PLAY_SPAWN_OBJECT, 0x17);
		registry.register(PacketType.CLIENTBOUND_PLAY_SPAWN_LIVING, 0x18);
		registry.register(PacketType.CLIENTBOUND_PLAY_SPAWN_PAINTING, 0x19);
		registry.register(PacketType.CLIENTBOUND_PLAY_SPAWN_EXP_ORB, 0x1A);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_VELOCITY, 0x1C);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_DESTROY, 0x1D);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_NOOP, 0x1E);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_REL_MOVE, 0x1F);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_LOOK, 0x20);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_REL_MOVE_LOOK, 0x21);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_TELEPORT, 0x22);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_HEAD_ROTATION, 0x23);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_STATUS, 0x26);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_LEASH, 0x27);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_METADATA, 0x28);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_EFFECT_ADD, 0x29);
		registry.register(PacketType.CLIENTBOUND_PLAY_ENTITY_EFFECT_REMOVE, 0x2A);
		registry.register(PacketType.CLIENTBOUND_PLAY_SET_EXPERIENCE, 0x2B);
		registry.register(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE, 0x33);
		registry.register(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI, 0x34);
		registry.register(PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE, 0x35);
		registry.register(PacketType.CLIENTBOUND_PLAY_BLOCK_ACTION, 0x36);
		registry.register(PacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION, 0x37);
		registry.register(PacketType.CLIENTBOUND_PLAY_EXPLOSION, 0x3C);
		registry.register(PacketType.CLIENTBOUND_PLAY_WORLD_EVENT, 0x3D);
		registry.register(PacketType.CLIENTBOUND_PLAY_WORLD_CUSTOM_SOUND, 0x3E);
		registry.register(PacketType.CLIENTBOUND_PLAY_WORLD_PARTICLES, 0x3F);
		registry.register(PacketType.CLIENTBOUND_PLAY_GAME_STATE_CHANGE, 0x46);
		registry.register(PacketType.CLIENTBOUND_PLAY_SPAWN_GLOBAL, 0x47);
		registry.register(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN, 0x64);
		registry.register(PacketType.CLIENTBOUND_PLAY_WINDOW_CLOSE, 0x65);
		registry.register(PacketType.CLIENTBOUND_PLAY_WINDOW_SET_SLOT, 0x67);
		registry.register(PacketType.CLIENTBOUND_PLAY_WINDOW_SET_ITEMS, 0x68);
		registry.register(PacketType.CLIENTBOUND_PLAY_WINDOW_DATA, 0x69);
		registry.register(PacketType.CLIENTBOUND_PLAY_WINDOW_TRANSACTION, 0x6A);
		registry.register(PacketType.CLIENTBOUND_LEGACY_PLAY_UPDATE_SIGN_ID, 0x82);
		registry.register(PacketType.CLIENTBOUND_PLAY_UPDATE_MAP, 0x83);
		registry.register(PacketType.CLIENTBOUND_PLAY_BLOCK_TILE, 0x84);
		registry.register(PacketType.CLIENTBOUND_PLAY_PLAYER_INFO, 0xC9);
		registry.register(PacketType.CLIENTBOUND_PLAY_PLAYER_ABILITIES, 0xCA);
		registry.register(PacketType.CLIENTBOUND_PLAY_TAB_COMPLETE, 0xCB);
		registry.register(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_OBJECTIVE, 0xCE);
		registry.register(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_SCORE, 0xCF);
		registry.register(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_DISPLAY_SLOT, 0xD0);
		registry.register(PacketType.CLIENTBOUND_PLAY_SCOREBOARD_TEAM, 0xD1);
		registry.register(PacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD, 0xFA);
		registry.register(PacketType.CLIENTBOUND_PLAY_KICK_DISCONNECT, 0xFF);
	}

}
