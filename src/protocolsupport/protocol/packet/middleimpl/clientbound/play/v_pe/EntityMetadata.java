package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIdRegistry;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		System.out.println("METAUPDATE");
		TIntObjectMap<DataWatcherObject<?>> remapped = WatchedDataRemapper.transform(cache.getWatchedEntity(entityId), metadata, version);
		if (remapped.isEmpty()) {
			System.out.println("Empty?");
			return RecyclableEmptyList.get();
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_ENTITY_DATA, version);
			VarNumberSerializer.writeVarLong(serializer, entityId);
			System.out.println("Entity: " + entityId);
			EntityMetadata.encode(serializer, version, remapped);
			return RecyclableSingletonList.create(serializer);
		}
	}
	
	public static void encode(ByteBuf to, ProtocolVersion version, TIntObjectMap<DataWatcherObject<?>> peMetadata) {
		TIntObjectIterator<DataWatcherObject<?>> iterator = peMetadata.iterator();
		VarNumberSerializer.writeVarInt(to, peMetadata.size());
		System.out.println("	Size: " + peMetadata.size());
		while (iterator.hasNext()) {
			iterator.advance();
			DataWatcherObject<?> object = iterator.value();
			VarNumberSerializer.writeVarInt(to, iterator.key());
			System.out.println("	Key: " + iterator.key());
			int tk = DataWatcherObjectIdRegistry.getTypeId(object, version) ;
			VarNumberSerializer.writeVarInt(to, tk);
			System.out.println("		Type: " + tk);
			object.writeToStream(to, version);
			System.out.println("		Object: " + object.toString());
		}
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
	
}
