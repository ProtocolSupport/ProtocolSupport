package protocolsupport.protocol.typeremapper.pe;

import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupportbuildprocessor.Preload;

@Preload
public class PELevelEvent {

	public static final int SOUND_CLICK = 1000;
	public static final int SOUND_CLICK_FAIL = 1001;
	public static final int SOUND_SHOOT = 1002;
	public static final int SOUND_DOOR = 1003;
	public static final int SOUND_FIZZ = 1004;
	public static final int SOUND_TNT = 1005;

	public static final int SOUND_GHAST = 1007;
	public static final int SOUND_BLAZE_SHOOT = 1008;
	public static final int SOUND_GHAST_SHOOT = 1009;
	public static final int SOUND_DOOR_BUMP = 1010;
	public static final int SOUND_DOOR_CRASH = 1012;

	public static final int SOUND_ENDERMAN_TELEPORT = 1018;

	public static final int SOUND_ANVIL_BREAK = 1020;
	public static final int SOUND_ANVIL_USE = 1021;
	public static final int SOUND_ANVIL_FALL = 1022;

	public static final int SOUND_ITEM_DROP = 1030;
	public static final int SOUND_ITEM_THROWN = 1031;

	public static final int SOUND_PORTAL = 1032;

	public static final int SOUND_ITEM_FRAME_ITEM_ADDED = 1040;
	public static final int SOUND_ITEM_FRAME_PLACED = 1041;
	public static final int SOUND_ITEM_FRAME_REMOVED = 1042;
	public static final int SOUND_ITEM_FRAME_ITEM_REMOVED = 1043;
	public static final int SOUND_ITEM_FRAME_ITEM_ROTATED = 1044;

	public static final int SOUND_CAMERA_TAKE_PICTURE = 1050;
	public static final int SOUND_EXPERIENCE_ORB = 1051;
	public static final int SOUND_BLOCK_PLACE = 1052;

	public static final int GUARDIAN_CURSE = 2006;

	public static final int SOUND_BUTTON_CLICK = 3500;
	public static final int SOUND_EXPLODE = 3501;
	public static final int CAULDRON_DYE_ARMOR = 3502;
	public static final int CAULDRON_CLEAN_ARMOR = 3503;
	public static final int CAULDRON_FILL_POTION = 3504;
	public static final int CAULDRON_TAKE_POTION = 3505;
	public static final int SOUND_SPLASH = 3506;
	public static final int CAULDRON_TAKE_WATER = 3507;
	public static final int CAULDRON_ADD_DYE = 3508;

	public static final int PARTICLE_SHOOT = 2000;
	public static final int PARTICLE_DESTROY = 2001;
	public static final int PARTICLE_EYE_DESPAWN = 2003;
	public static final int PARTICLE_SPAWN = 2004;
	public static final int PARTICLE_BONEMEAL = 2005;

	public static final int BLOCK_PARTICLES = 2014;

	public static final int START_RAIN = 3001;
	public static final int START_THUNDER = 3002;
	public static final int STOP_RAIN = 3003;
	public static final int STOP_THUNDER = 3004;

	public static final int SOUND_CAULDRON = 3501;
	public static final int SOUND_CAULDRON_DYE_ARMOR = 3502;
	public static final int SOUND_CAULDRON_FILL_POTION = 3504;
	public static final int SOUND_CAULDRON_FILL_WATER = 3506;

	public static final int BLOCK_START_BREAK = 3600;
	public static final int BLOCK_STOP_BREAK = 3601;

	public static final int SET_DATA = 4000;

	public static final int PLAYERS_SLEEPING = 9800;

	public static final int PARTICLE = 0x4000,
		PARTICLE_BUBBLE 					= PARTICLE |  1,
		PARTICLE_CRITICAL 					= PARTICLE |  2,
		PARTICLE_BLOCK_FORCE_FIELD 			= PARTICLE |  3,
		PARTICLE_SMOKE 						= PARTICLE |  4,
		PARTICLE_EXPLODE 					= PARTICLE |  5,
		PARTICLE_EVAPORTAION 				= PARTICLE |  6,
		PARTICLE_FLAME 						= PARTICLE |  7,
		PARTICLE_LAVA 						= PARTICLE |  8,
		PARTICLE_LARGE_SMOKE 				= PARTICLE |  9,
		PARTICLE_REDSTONE 					= PARTICLE | 10,
		PARTICLE_RISING_RED_DUST 			= PARTICLE | 11,
		PARTICLE_ITEM_BREAK 				= PARTICLE | 12,
		PARTICLE_SNOWBALL_POOF 				= PARTICLE | 13,
		PARTICLE_HUGE_EXPLOSION				= PARTICLE | 14,
		PARTICLE_HUGE_EXPLOSION_SEED		= PARTICLE | 15,
		PARTICLE_MOB_FLAME 					= PARTICLE | 16,
		PARTICLE_HEART 						= PARTICLE | 17,
		PARTICLE_TERRAIN 					= PARTICLE | 18,
		PARTICLE_TOWN_AURA		 			= PARTICLE | 19,
		PARTICLE_PORTAL 					= PARTICLE | 20,
		PARTICLE_SPLASH 					= PARTICLE | 21,
		PARTICLE_WATER_WAKE 				= PARTICLE | 22,
		PARTICLE_DRIP_WATER 				= PARTICLE | 23,
		PARTICLE_DRIP_LAVA 					= PARTICLE | 24,
		PARTICLE_FALLING_DUST 				= PARTICLE | 25,
		PARTICLE_MOB_SPELL 					= PARTICLE | 26,
		PARTICLE_MOB_SPELL_AMBIENT 			= PARTICLE | 27,
		PARTICLE_MOB_SPELL_INSTANTANIOUS	= PARTICLE | 28,
		PARTICLE_INK 						= PARTICLE | 29,
		PARTICLE_SLIME 						= PARTICLE | 30,
		PARTICLE_RAIN_SPLASH 				= PARTICLE | 31,
		PARTICLE_VILLAGER_ANGRY 			= PARTICLE | 32,
		PARTICLE_VILLAGER_HAPPY 			= PARTICLE | 33,
		PARTICLE_ENCHANTMENT_TABLE 			= PARTICLE | 34,
		PARTICLE_TRACKING_EMITTER 			= PARTICLE | 35,
		PARTICLE_NOTE 						= PARTICLE | 36,
		PARTICLE_WITCH_SPELL 				= PARTICLE | 37,
		PARTICLE_CARROT 					= PARTICLE | 38,
		PARTICLE_UNKNOWN_1 					= PARTICLE | 39,
		PARTICLE_END_ROT 					= PARTICLE | 40,
		PARTICLE_DRAGONS_BREATH 			= PARTICLE | 41;

	public static ClientBoundPacketData createPacket(int levelEvent, float x, float y, float z, int data) {
		ClientBoundPacketData clientLevelEvent = ClientBoundPacketData.create(PEPacketIDs.LEVEL_EVENT);
		VarNumberSerializer.writeSVarInt(clientLevelEvent, levelEvent);
		clientLevelEvent.writeFloatLE(x);
		clientLevelEvent.writeFloatLE(y);
		clientLevelEvent.writeFloatLE(z);
		VarNumberSerializer.writeSVarInt(clientLevelEvent, data);
		return clientLevelEvent;
	}

	public static ClientBoundPacketData createPacket(int levelEvent, Position position, int data) {
		return createPacket(levelEvent, position.getX(), position.getY(), position.getZ(), data);
	}

	public static ClientBoundPacketData createPacket(int levelEvent, Position position) {
		return createPacket(levelEvent, position, 0);
	}

	public static ClientBoundPacketData createPacket(int levelEvent, int data) {
		return createPacket(levelEvent, 0f, 0f, 0f, data);
	}

	public static ClientBoundPacketData createPacket(int levelEvent) {
		return createPacket(levelEvent, 0);
	}

}
