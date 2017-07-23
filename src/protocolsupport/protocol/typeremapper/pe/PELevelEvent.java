package protocolsupport.protocol.typeremapper.pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class PELevelEvent {
	//TODO: recheck all ids for wrong and missing ones
	public static final int GUARDIAN_CURSE = 2006;
	
	public static final int CAULDRON_DYE_ARMOR = 3502;
	public static final int CAULDRON_CLEAN_ARMOR = 3503;
	public static final int CAULDRON_FILL_POTION = 3504;
	public static final int CAULDRON_TAKE_POTION = 3505;
	public static final int CAULDRON_TAKE_WATER = 3507;
	public static final int CAULDRON_ADD_DYE = 3508;
	
	public static final int PARTICLE_SHOOT = 2000;
	public static final int PARTICLE_DESTROY = 2001;
	public static final int PARTICLE_EYE_DESPAWN = 2003;
	public static final int PARTICLE_SPAWN = 2004;
	public static final int PARTICLE_BONEMEAL = 2005;

	public static final int START_RAIN = 3001;
	public static final int START_THUNDER = 3002;
	public static final int STOP_RAIN = 3003;
	public static final int STOP_THUNDER = 3004;

	public static final int SET_DATA = 4000;
	
	public static final int PLAYERS_SLEEPING = 9800;
	
	public static final int PARTICLE = 0x4000,
		PARTICLE_BUBBLE 				= PARTICLE | 1,
		PARTICLE_CRITICAL 				= PARTICLE | 2,
		PARTICLE_BLOCK_FORCE_FIELD 		= PARTICLE | 3,
		PARTICLE_SMOKE 					= PARTICLE | 4,
		PARTICLE_EXPLODE 				= PARTICLE | 5,
		PARTICLE_EVAPORTAION 			= PARTICLE | 6,
		PARTICLE_FLAME 					= PARTICLE | 7,
		PARTICLE_LAVA 					= PARTICLE | 8,
		PARTICLE_LARGE_SMOKE 			= PARTICLE | 9,
		PARTICLE_REDSTONE 				= PARTICLE | 10,
		PARTICLE_RISING_RED_DUST 		= PARTICLE | 11,
		PARTICLE_ITEM_BREAK 			= PARTICLE | 12,
		PARTICLE_SNOWBALL_POOF 			= PARTICLE | 13,
		PARTICLE_HUGE_EXPLOSION			= PARTICLE | 14,
		PARTICLE_HUGE_EXPLOSION_SEED	= PARTICLE | 15,
		PARTICLE_MOB_FLAME 				= PARTICLE | 16,
		PARTICLE_HEART 					= PARTICLE | 17,
		PARTICLE_TERRAIN 				= PARTICLE | 18,
		PARTICLE_TOWN_AURA		 		= PARTICLE | 19,
		PARTICLE_PORTAL 				= PARTICLE | 20,
		PARTICLE_SPLASH 				= PARTICLE | 21,
		PARTICLE_WATER_WAKE 			= PARTICLE | 22,
		PARTICLE_DRIP_WATER 			= PARTICLE | 23,
		PARTICLE_DRIP_LAVA 				= PARTICLE | 24,
		PARTICLE_FALLING_DUST 			= PARTICLE | 25,
		PARTICLE_MOB_SPELL 				= PARTICLE | 26,
		PARTICLE_MOB_SPELL_AMBIENT 		= PARTICLE | 27,
		PARTICLE_MOB_SPELL_INSTANTANIOUS= PARTICLE | 28,
		PARTICLE_INK 					= PARTICLE | 29,
		PARTICLE_SLIME 					= PARTICLE | 30,
		PARTICLE_RAIN_SPLASH 			= PARTICLE | 31,
		PARTICLE_VILLAGER_ANGRY 		= PARTICLE | 32,
		PARTICLE_VILLAGER_HAPPY 		= PARTICLE | 33,
		PARTICLE_ENCHANTMENT_TABLE 		= PARTICLE | 34,
		PARTICLE_TRACKING_EMITTER 		= PARTICLE | 35,
		PARTICLE_NOTE 					= PARTICLE | 36,
		PARTICLE_WITCH_SPELL 			= PARTICLE | 37,
		PARTICLE_CARROT 				= PARTICLE | 38,
		PARTICLE_UNKNOWN_1 				= PARTICLE | 39,
		PARTICLE_END_ROT 				= PARTICLE | 40,
		PARTICLE_DRAGONS_BREATH 		= PARTICLE | 41;

	public static ClientBoundPacketData createPacket(int levelEvent, float x, float y, float z, int data) {
		ClientBoundPacketData clientLevelEvent = ClientBoundPacketData.create(PEPacketIDs.LEVEL_EVENT, ProtocolVersion.MINECRAFT_PE);
		VarNumberSerializer.writeSVarInt(clientLevelEvent, levelEvent);
		MiscSerializer.writeLFloat(clientLevelEvent, x);
		MiscSerializer.writeLFloat(clientLevelEvent, y);
		MiscSerializer.writeLFloat(clientLevelEvent, z);
		VarNumberSerializer.writeSVarInt(clientLevelEvent, data);
		return clientLevelEvent;
	}

	public static ClientBoundPacketData createPacket(int levelEvent, int data) {
		return createPacket(levelEvent, 0f, 0f, 0f, data);
	}

	public static ClientBoundPacketData createPacket(int levelEvent) {
		return createPacket(levelEvent, 0);
	}

}