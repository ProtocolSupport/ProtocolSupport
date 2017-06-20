package protocolsupport.protocol.typeremapper.pe;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectLong;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShortLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache;
import protocolsupport.protocol.utils.types.NetworkEntityType;

public class PEMetaData {
	
	private static final TIntObjectMap<DataWatcherObject<?>> EMPTY_MAP = new TIntObjectHashMap<>();

	public static TIntObjectMap<DataWatcherObject<?>> transform(NetworkEntity entity, TIntObjectMap<DataWatcherObject<?>> originaldata, ProtocolVersion to) {
		
		//Always cache the metadata for PE players.
		entity.getDataCache().updateMeta(originaldata);
		
		TIntObjectMap<DataWatcherObject<?>> transformed = WatchedDataRemapper.transform(entity, originaldata, to);
		if (transformed.isEmpty()) {
			return EMPTY_MAP;
		}
		
		//System.out.println("Updated. Cache is now: " + entity.getDataCache().metadata.toString());
		
		//Apply default necessary values for PE
		transformed.put(0, new DataWatcherObjectLong(mapBaseFlags(entity)));
		//TODO: CHECK IF NECESSARY/ if((entity.isOfType(NetworkEntityType.PLAYER)) && originaldata.containsKey(2)) originaldata.remove(2); //Don't put empty nametags on players. Why would you?
		if((!originaldata.containsKey(1)) || (int)(((DataWatcherObjectVarInt)originaldata.get(1)).getValue()) == 300) transformed.put(7, new DataWatcherObjectShortLe(0)); //Air is 0 when full and 0 when empty on PE.
		transformed.put(38, new DataWatcherObjectLong(-1));//TODO: Add Leash functionality.
		return transformed;
	}

	//PE's extra baseflags. TODO: Implement more flags (Easy Remapping)
	public static int FLAG_ON_FIRE = 0;
	public static int FLAG_SNEAKING = 1;
//	public static int FLAG_RIDING = 2; 
	public static int FLAG_SPRINTING = 3;
//	public static int FLAG_USING_ITEM = 4;
	public static int FLAG_INVISIBLE = 5;
//	public static int FLAG_TEMPTED = 6;
//	public static int FLAG_IN_LOVE = 7;
	public static int FLAG_SADDLED = 8;
//	public static int FLAG_POWERED = 9;
//	public static int FLAG_IGNITED = 10;
//	public static int FLAG_BABY = 11;
//	public static int FLAG_CONVERTING = 12;
//	public static int FLAG_CRITICAL = 13;
	public static int FLAG_SHOW_NAMETAG = 14;
	public static int FLAG_ALWAYS_SHOW_NAMETAG = 15;
//	public static int FLAG_NO_AI = 16;
	public static int FLAG_SILENT = 17;
//	public static int FLAG_CLIMBING = 18;
//	public static int FLAG_CAN_CLIMB = 19;
//	public static int FLAG_CAN_SWIM = 20;
//	public static int FLAG_CAN_FLY = 21;
//	public static int FLAG_RESTING = 22;
//	public static int FLAG_SITTING = 23;
//	public static int FLAG_ANGRY = 24;
//	public static int FLAG_INTERESTED = 25;
//	public static int FLAG_CHARGED = 26;
//	public static int FLAG_TAMED = 27;
//	public static int FLAG_LEASHED = 28;
//	public static int FLAG_SHEARED = 29;
	public static int FLAG_GLIDING = 30;
//	public static int FLAG_ELDER = 31;
//	public static int FLAG_MOVING = 32;
//	public static int FLAG_BREATHING = 33;
//	public static int FLAG_CHESTED = 34;
//	public static int FLAG_STACKABLE = 35;
//	public static int FLAG_SHOW_BASE = 36;
//	public static int FLAG_REARING = 37;
//	public static int FLAG_VIBRATING = 38;
//	public static int FLAG_IDLING = 39;
//	public static int FLAG_EVOKER_SPELL = 40;
//	public static int FLAG_CHARGE_ATTACK = 41;
//	public static int FLAG_LINGER = 45;
	
	private static Long mapBaseFlags(NetworkEntity entity) {
		DataCache cache = entity.getDataCache();
		byte pcbaseflags = (byte) cache.getMetaValue(0);
		long b = 0;
		//Pc base-flags
		if((pcbaseflags & (1 << 0)) != 0) b |= (1 << FLAG_ON_FIRE);
		if((pcbaseflags & (1 << 1)) != 0) b |= (1 << FLAG_SNEAKING);
		if((pcbaseflags & (1 << 3)) != 0) b |= (1 << FLAG_SPRINTING);
		if((pcbaseflags & (1 << 5)) != 0) b |= (1 << FLAG_INVISIBLE);
		if((pcbaseflags & (1 << 7)) != 0) b |= (1 << FLAG_GLIDING);
		//Boolean meta values
		if(cache.getMetaBool(4)) b |= (1 << FLAG_SILENT);
		//Names
		if(cache.getMetaBool(2)) b |= (1 << FLAG_SHOW_NAMETAG);
		if(entity.isOfType(NetworkEntityType.PLAYER)) b |= (1 << FLAG_ALWAYS_SHOW_NAMETAG);
		//Specifics:
		if(entity.isOfType(NetworkEntityType.PIG))  b |= (1 << FLAG_SADDLED); //Test
		//if((entity.getType() == SpecificRemapper.AGEABLE) && boolFromPc(12, pcMeta)) b |= (1 << FLAG_BABY);
		//if(((entity.getType() == SpecificRemapper.PIG) && boolFromPc(13, pcMeta)) || ((entity.getType() == SpecificRemapper.BASE_HORSE) && boolFromPcFlag(13, 2, pcMeta))) b |= (1 << FLAG_SADDLED);
		//if((entity.getType() == SpecificRemapper.SHEEP) && boolFromPcFlag(13, 7, pcMeta)) b |= (1 << FLAG_SHEARED);
		return b;
	}	
}
