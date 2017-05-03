package protocolsupport.protocol.legacyremapper.pe;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIdRegistry;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;

public class PEEntityMetaData {
	private static int FLAG_ON_FIRE = 0;
	private static int FLAG_SNEAKING = 1;
//	private static int FLAG_RIDING = 2; TODO: Implement more flags and update getBaseValues() to find them out.
	private static int FLAG_SPRINTING = 3;
//	private static int FLAG_USING_ITEM = 4;
	private static int FLAG_INVISIBLE = 5;
//	private static int FLAG_TEMPTED = 6;
//	private static int FLAG_IN_LOVE = 7;
	private static int FLAG_SADDLED = 8;
//	private static int FLAG_POWERED = 9;
//	private static int FLAG_IGNITED = 10;
//	private static int FLAG_BABY = 11;
//	private static int FLAG_CONVERTING = 12;
//	private static int FLAG_CRITICAL = 13;
	private static int FLAG_SHOW_NAMETAG = 14;
	private static int FLAG_ALWAYS_SHOW_NAMETAG = 15;
//	private static int FLAG_NO_AI = 16;
	private static int FLAG_SILENT = 17;
//	private static int FLAG_CLIMBING = 18;
//	private static int FLAG_RESTING = 19;
//	private static int FLAG_SITTING = 20;
//	private static int FLAG_ANGRY = 21;
//	private static int FLAG_INTERESTED = 22;
//	private static int FLAG_CHARGED = 23;
//	private static int FLAG_TAMED = 24;
//	private static int FLAG_LEASHED = 25;
//	private static int FLAG_SHEARED = 26;
	private static int FLAG_GLIDING = 27;
//	private static int FLAG_ELDER = 28;
//	private static int FLAG_MOVING = 29;
//	private static int FLAG_BREATHING = 30;
//	private static int FLAG_CHESTED = 31;
//	private static int FLAG_STACKABLE = 32;
//	private static int FLAG_IDLING = 36;
	
	
	public static boolean writeMetadata(ByteBuf to, ProtocolVersion version, TIntObjectMap<DataWatcherObject<?>> peMetadata) {
		if (!peMetadata.isEmpty()) {
			TIntObjectIterator<DataWatcherObject<?>> iterator = peMetadata.iterator();
			VarNumberSerializer.writeVarInt(to, peMetadata.size());
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject<?> object = iterator.value();
				VarNumberSerializer.writeVarInt(to, iterator.key());
				int tk = DataWatcherObjectIdRegistry.getTypeId(object, version) ;
				VarNumberSerializer.writeVarInt(to, tk);
				object.writeToStream(to, version);
			}
			return true;
		}
		return false;
	}
	
	public static Long getBaseValues(int entityId, WatchedEntity entity, NetworkDataCache cache, TIntObjectMap<DataWatcherObject<?>> pcMeta){
		byte basevalue = cache.getWatchedEntityBaseMeta(entityId);
		long b = 0;
		if((basevalue & (1 << 0)) != 0) b |= (1 << FLAG_ON_FIRE);
		if((basevalue & (1 << 1)) != 0) b |= (1 << FLAG_SNEAKING);
		if((basevalue & (1 << 3)) != 0) b |= (1 << FLAG_SPRINTING);
		if((basevalue & (1 << 5)) != 0) b |= (1 << FLAG_INVISIBLE);
		if((basevalue & (1 << 7)) != 0) b |= (1 << FLAG_GLIDING);
		if(boolFromPc(4, pcMeta)) b |= (1 << FLAG_SILENT);
		if(showName(entity, pcMeta) >= NAME_SHOW_CLOSE) b |= (1 << FLAG_SHOW_NAMETAG);
		if(showName(entity, pcMeta) >= NAME_SHOW_FAR) b |= (1 << FLAG_ALWAYS_SHOW_NAMETAG);
		//Specifics:
		if(entity.getType() == SpecificRemapper.PIG)  b |= (1 << FLAG_SADDLED); //Test
		//if((entity.getType() == SpecificRemapper.AGEABLE) && boolFromPc(12, pcMeta)) b |= (1 << FLAG_BABY);
		//if(((entity.getType() == SpecificRemapper.PIG) && boolFromPc(13, pcMeta)) || ((entity.getType() == SpecificRemapper.BASE_HORSE) && boolFromPcFlag(13, 2, pcMeta))) b |= (1 << FLAG_SADDLED);
		//if((entity.getType() == SpecificRemapper.SHEEP) && boolFromPcFlag(13, 7, pcMeta)) b |= (1 << FLAG_SHEARED);
		return b;
	}
	
	private static int NAME_DONT_SHOW = 0;
	private static int NAME_SHOW_CLOSE = 1;
	private static int NAME_SHOW_FAR = 2;
	
	private static int showName(WatchedEntity entity, TIntObjectMap<DataWatcherObject<?>> pcMeta){
		if(entity.getType() == SpecificRemapper.PLAYER && (!pcMeta.containsKey(3) || boolFromPc(3, pcMeta))){
			return NAME_SHOW_FAR;
		}else if (pcMeta.containsKey(2)){
			return NAME_SHOW_CLOSE;
		}
		return NAME_DONT_SHOW;
	}
	
	private static boolean boolFromPc(int key, TIntObjectMap<DataWatcherObject<?>> originaldata){
		if((originaldata.containsKey(key)) && ((DataWatcherObjectBoolean)originaldata.get(key)).getValue()) return true;
		return false;
	}
	
	//Allright, this had to be done differently altogether since minecraft doesn't always send all Metadata of animals etc.
	//Just like the basecash it should be stored inside the NetworkDataCashe. Eg:
	/*
 			DataWatcherObject<?> horseFlags = originaldata.get(13);
			if (horseFlags != null) {
				if (horseFlags.getValue() instanceof Number) {
					cache.addWatchedEntityHorseMeta(entityId, ((Number) horseFlags.getValue()).byteValue());
				}
			}
	 */
	//Also information such as isBaba (which is set to 12) doesn't seem to be send always (updated) so should also be Cashed. ;(
	
	/*private static boolean boolFromPcFlag(int key, int flagKey, TIntObjectMap<DataWatcherObject<?>> pcMeta){
		if(pcMeta.containsKey(key)){
			byte flags = ((Number) pcMeta.get(key).getValue()).byteValue();
			return ((flags & (1 << flagKey)) != 0);
		}
		return false; 
	}*/
	
}
