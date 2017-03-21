package protocolsupport.protocol.legacyremapper.pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

/***
 * 
 * Information gathered from the Nukkit project :)
 *
 */
public class PELevelEvent {
    public static final int EVENT_SOUND_CLICK = 1000;
    public static final int EVENT_SOUND_CLICK_FAIL = 1001;
    public static final int EVENT_SOUND_SHOOT = 1002;
    public static final int EVENT_SOUND_DOOR = 1003;
    public static final int EVENT_SOUND_FIZZ = 1004;
    public static final int EVENT_SOUND_TNT = 1005;

    public static final int EVENT_SOUND_GHAST = 1007;
    public static final int EVENT_SOUND_BLAZE_SHOOT = 1008;
    public static final int EVENT_SOUND_GHAST_SHOOT = 1009;
    public static final int EVENT_SOUND_DOOR_BUMP = 1010;
    public static final int EVENT_SOUND_DOOR_CRASH = 1012;

    public static final int EVENT_SOUND_ENDERMAN_TELEPORT = 1018;

    public static final int EVENT_SOUND_ANVIL_BREAK = 1020;
    public static final int EVENT_SOUND_ANVIL_USE = 1021;
    public static final int EVENT_SOUND_ANVIL_FALL = 1022;

    public static final int EVENT_SOUND_ITEM_DROP = 1030;
    public static final int EVENT_SOUND_ITEM_THROWN = 1031;

    public static final int EVENT_SOUND_PORTAL = 1032;
    
    public static final int EVENT_SOUND_ITEM_FRAME_ITEM_ADDED = 1040;
    public static final int EVENT_SOUND_ITEM_FRAME_PLACED = 1041;
    public static final int EVENT_SOUND_ITEM_FRAME_REMOVED = 1042;
    public static final int EVENT_SOUND_ITEM_FRAME_ITEM_REMOVED = 1043;
    public static final int EVENT_SOUND_ITEM_FRAME_ITEM_ROTATED = 1044;

    public static final int EVENT_SOUND_CAMERA_TAKE_PICTURE = 1050;
    public static final int EVENT_SOUND_EXPERIENCE_ORB = 1051;
    public static final int EVENT_SOUND_BLOCK_PLACE = 1052;

    public static final int EVENT_GUARDIAN_CURSE = 2006;
    
    public static final int EVENT_PARTICLE_BLOCK_FORCE_FIELD = 2008;
    
    public static final int EVENT_SOUND_BUTTON_CLICK = 3500;
    public static final int EVENT_SOUND_EXPLODE = 3501;
    public static final int EVENT_CAULDRON_DYE_ARMOR = 3502;
    public static final int EVENT_CAULDRON_CLEAN_ARMOR = 3503;
    public static final int EVENT_CAULDRON_FILL_POTION = 3504;
    public static final int EVENT_CAULDRON_TAKE_POTION = 3505;
    public static final int EVENT_SOUND_SPLASH = 3506;
    public static final int EVENT_CAULDRON_TAKE_WATER = 3507;
    public static final int EVENT_CAULDRON_ADD_DYE = 3508;
    
    public static final int EVENT_PARTICLE_SHOOT = 2000;
    public static final int EVENT_PARTICLE_DESTROY = 2001;
    public static final int EVENT_PARTICLE_SPLASH = 2002;
    public static final int EVENT_PARTICLE_EYE_DESPAWN = 2003;
    public static final int EVENT_PARTICLE_SPAWN = 2004;
    public static final int EVENT_PARTICLE_UNKNOWN = 2005;

    public static final int EVENT_START_RAIN = 3001;
    public static final int EVENT_START_THUNDER = 3002;
    public static final int EVENT_STOP_RAIN = 3003;
    public static final int EVENT_STOP_THUNDER = 3004;

    public static final int EVENT_SOUND_CAULDRON = 3501;
    public static final int EVENT_SOUND_CAULDRON_DYE_ARMOR = 3502;
    public static final int EVENT_SOUND_CAULDRON_FILL_POTION = 3504;
    public static final int EVENT_SOUND_CAULDRON_FILL_WATER = 3506;

    public static final int EVENT_SET_DATA = 4000;

    public static final int EVENT_PLAYERS_SLEEPING = 9800;

    public static final int EVENT_ADD_PARTICLE_MASK = 0x4000;
    
    public static ClientBoundPacketData ClientLevelEvent(int LevelEvent, float x, float y, float z, int data){
		ClientBoundPacketData clientLevelEvent = ClientBoundPacketData.create(PEPacketIDs.LEVEL_EVENT, ProtocolVersion.MINECRAFT_PE);
		VarNumberSerializer.writeSVarInt(clientLevelEvent, LevelEvent);
		MiscSerializer.writeLFloat(clientLevelEvent, x);
		MiscSerializer.writeLFloat(clientLevelEvent, y);
		MiscSerializer.writeLFloat(clientLevelEvent, z);
		VarNumberSerializer.writeSVarInt(clientLevelEvent, data);
		System.out.println(clientLevelEvent);
		return clientLevelEvent;
    }
    
    public static ClientBoundPacketData ClientLevelEvent(int LevelEvent, int data){
    	return ClientLevelEvent(LevelEvent, 0f, 0f, 0f, data);
    }
    
    public static ClientBoundPacketData ClientLevelEvent(int LevelEvent){
    	return ClientLevelEvent(LevelEvent, 0);
    }
}