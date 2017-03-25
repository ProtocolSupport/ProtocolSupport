package protocolsupport.protocol.legacyremapper.pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

//Ids are copied from the Nukkit project :)
//TODO: recheck all ids for wrong and missing ones
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

	public static final int PARTICLE_BLOCK_FORCE_FIELD = 2008;

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
	public static final int PARTICLE_SPLASH = 2002;
	public static final int PARTICLE_EYE_DESPAWN = 2003;
	public static final int PARTICLE_SPAWN = 2004;
	public static final int PARTICLE_BONEMEAL = 2005;

	public static final int START_RAIN = 3001;
	public static final int START_THUNDER = 3002;
	public static final int STOP_RAIN = 3003;
	public static final int STOP_THUNDER = 3004;

	public static final int SOUND_CAULDRON = 3501;
	public static final int SOUND_CAULDRON_DYE_ARMOR = 3502;
	public static final int SOUND_CAULDRON_FILL_POTION = 3504;
	public static final int SOUND_CAULDRON_FILL_WATER = 3506;

	public static final int SET_DATA = 4000;

	public static final int PLAYERS_SLEEPING = 9800;

	public static final int ADD_PARTICLE_MASK = 0x4000;

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