package protocolsupport.protocol.legacyremapper.pe;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class PEEntityMetaData {
	
	public static int FLAG_ON_FIRE = 0;
	public static int FLAG_SNEAKING = 1;
	public static int FLAG_RIDING = 2;
	public static int FLAG_SPRINTING = 3;
	public static int FLAG_USING_ITEM = 4;
	public static int FLAG_INVISIBLE = 5;
	public static int FLAG_TEMPTED = 6;
	public static int FLAG_IN_LOVE = 7;
	public static int FLAG_SADDLED = 8;
	public static int FLAG_POWERED = 9;
	public static int FLAG_IGNITED = 10;
	public static int FLAG_BABY = 11;
	public static int FLAG_CONVERTING = 12;
	public static int FLAG_CRITICAL = 13;
	public static int FLAG_SHOW_NAMETAG = 14;
	public static int FLAG_ALWAYS_SHOW_NAMETAG = 15;
	public static int FLAG_NO_AI = 16;
	public static int FLAG_SILENT = 17;
	public static int FLAG_CLIMBING = 18;
	public static int FLAG_RESTING = 19;
	public static int FLAG_SITTING = 20;
	public static int FLAG_ANGRY = 21;
	public static int FLAG_INTERESTED = 22;
	public static int FLAG_CHARGED = 23;
	public static int FLAG_TAMED = 24;
	public static int FLAG_LEASHED = 25;
	public static int FLAG_SHEARED = 26;
	public static int FLAG_GLIDING = 27;
	public static int FLAG_ELDER = 28;
	public static int FLAG_MOVING = 29;
	public static int FLAG_BREATHING = 30;
	public static int FLAG_CHESTED = 31;
	public static int FLAG_STACKABLE = 32;
	public static int FLAG_IDLING = 36;
	
	
	public static boolean writeMetadata(ByteBuf to, ProtocolVersion version, TIntObjectMap<DataWatcherObject<?>> peMetadata) {
		if (!peMetadata.isEmpty()) {
			TIntObjectIterator<DataWatcherObject<?>> iterator = peMetadata.iterator();
			System.out.println("Size: " + peMetadata.size());
			VarNumberSerializer.writeSVarInt(to, peMetadata.size());
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject<?> object = iterator.value();
				VarNumberSerializer.writeSVarInt(to, iterator.key());
				VarNumberSerializer.writeSVarInt(to, object.getTypeId(version));
				System.out.print("Key: ");System.out.println(iterator.key());
				System.out.print("Type: ");System.out.println(object.getTypeId(version));
				System.out.print("Value: ");System.out.println(object.getValue());
				object.writeToStream(to, ProtocolVersion.MINECRAFT_PE);
			}
			return true;
		}
		return false;
	}
	
	public static Long getBaseValues(NetworkDataCache cache, int entityId, TIntObjectMap<DataWatcherObject<?>> originaldata){
		byte pcBaseValue = cache.getWatchedEntityBaseMeta(entityId);
		
		long b = 0;
		b |= (pcBaseValue & 0x02) > 0 ? FLAG_ON_FIRE : 0;
		b |= (pcBaseValue & 0x08) > 0 ? FLAG_SNEAKING : 0;
		b |= (pcBaseValue & 0x10) > 0 ? FLAG_SPRINTING : 0;
		b |= (pcBaseValue & 0x20) > 0 ? FLAG_INVISIBLE : 0;
		b |= (pcBaseValue & 0x80) > 0 ? FLAG_GLIDING : 0;
		return b;
	}
	
}
