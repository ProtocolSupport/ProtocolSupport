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
//	private static int FLAG_SADDLED = 8;
//	private static int FLAG_POWERED = 9;
//	private static int FLAG_IGNITED = 10;
//	private static int FLAG_BABY = 11;
//	private static int FLAG_CONVERTING = 12;
//	private static int FLAG_CRITICAL = 13;
	private static int FLAG_SHOW_NAMETAG = 14;
	private static int FLAG_ALWAYS_SHOW_NAMETAG = 15;
//	private static int FLAG_NO_AI = 16;
//	private static int FLAG_SILENT = 17;
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
			System.out.println("Size: " + peMetadata.size());
			VarNumberSerializer.writeVarInt(to, peMetadata.size());
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject<?> object = iterator.value();
				VarNumberSerializer.writeVarInt(to, iterator.key());
				int tk = DataWatcherObjectIdRegistry.getTypeId(object, version) ;
				VarNumberSerializer.writeVarInt(to, tk);
 				System.out.println("	Key: " + iterator.key());
 				System.out.println("	Type: " + tk);
 				System.out.println("	Value: " + object.getValue());
				object.writeToStream(to, version);
			}
			return true;
		}
		return false;
	}
	
	public static Long getBaseValues(int entityId, WatchedEntity entity, NetworkDataCache cache, TIntObjectMap<DataWatcherObject<?>> pcMeta){
		byte pcBaseValue = cache.getWatchedEntityBaseMeta(entityId);
		
		long b = 0;
		if((pcBaseValue & (1 << 0)) != 0) b |= (1 << FLAG_ON_FIRE);
		if((pcBaseValue & (1 << 1)) != 0) b |= (1 << FLAG_SNEAKING);
		if((pcBaseValue & (1 << 3)) != 0) b |= (1 << FLAG_SPRINTING);
		if((pcBaseValue & (1 << 5)) != 0) b |= (1 << FLAG_INVISIBLE);
		if((pcBaseValue & (1 << 7)) != 0) b |= (1 << FLAG_GLIDING);
		
		if(pcMeta.containsKey(2)) b |= (1 << FLAG_SHOW_NAMETAG);
		
		//Player's have names on default. TODO: Fix team visibility (maybe using ScoreBoard Team packet calling Metadata change packet?)
		if(entity.getType() == SpecificRemapper.PLAYER && (!pcMeta.containsKey(3) || boolFromPc(3, pcMeta))){
			 b |= (1 << FLAG_SHOW_NAMETAG);
			 b |= (1 << FLAG_ALWAYS_SHOW_NAMETAG);
		}
		
		
		return b;
	}
	
	public static boolean boolFromPc(int key, TIntObjectMap<DataWatcherObject<?>> originaldata){
		if((originaldata.containsKey(key)) && ((DataWatcherObjectBoolean)originaldata.get(key)).getValue()) return true;
		return false;
	}
	
}
