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
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if(!cache.containsWatchedEntity(entityId)) {
			return RecyclableEmptyList.get();
		} else {
			return RecyclableSingletonList.create(create(cache.getWatchedEntity(entityId), metadata, version));
		}
	}
	
	public static ClientBoundPacketData create(NetworkEntity entity, ProtocolVersion version) {
		return create(entity, entity.getDataCache().metadata, version);
	}
	
	public static ClientBoundPacketData create(NetworkEntity entity, TIntObjectMap<DataWatcherObject<?>> metadata, ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_ENTITY_DATA, version);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		EntityMetadata.encodeMeta(serializer, version, WatchedDataRemapper.transform(entity, metadata, version));
		return serializer;
	}
	
	public static void encodeMeta(ByteBuf to, ProtocolVersion version, TIntObjectMap<DataWatcherObject<?>> peMetadata) {
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
	}

	public static class PeMetaBase {
		//PE's extra baseflags. TODO: Implement more flags (Easy Remapping)
		protected static int id = 1;
		protected static int takeNextId() {
			return id++;
		}
		
		public static int FLAG_ON_FIRE = takeNextId();
		public static int FLAG_SNEAKING = takeNextId();
		public static int FLAG_RIDING = takeNextId(); 
		public static int FLAG_SPRINTING = takeNextId();
		public static int FLAG_USING_ITEM = takeNextId();
		public static int FLAG_INVISIBLE = takeNextId();
		public static int FLAG_TEMPTED = takeNextId();
		public static int FLAG_IN_LOVE = takeNextId();
		public static int FLAG_SADDLED = takeNextId();
		public static int FLAG_POWERED = takeNextId();
		public static int FLAG_IGNITED = takeNextId();
		public static int FLAG_BABY = takeNextId();
		public static int FLAG_CONVERTING = takeNextId();
		public static int FLAG_CRITICAL = takeNextId();
		public static int FLAG_SHOW_NAMETAG = takeNextId();
		public static int FLAG_ALWAYS_SHOW_NAMETAG = takeNextId();
		public static int FLAG_NO_AI = takeNextId();
		public static int FLAG_SILENT = takeNextId();
		public static int FLAG_CLIMBING = takeNextId();
		public static int FLAG_CAN_CLIMB = takeNextId();
		public static int FLAG_CAN_SWIM = takeNextId();
		public static int FLAG_CAN_FLY = takeNextId();
		public static int FLAG_RESTING = takeNextId();
		public static int FLAG_SITTING = takeNextId();
		public static int FLAG_ANGRY = takeNextId();
		public static int FLAG_INTERESTED = takeNextId();
		public static int FLAG_CHARGED = takeNextId();
		public static int FLAG_TAMED = takeNextId();
		public static int FLAG_LEASHED = takeNextId();
		public static int FLAG_SHEARED = takeNextId();
		public static int FLAG_GLIDING = takeNextId();
		public static int FLAG_ELDER = takeNextId();
		public static int FLAG_MOVING = takeNextId();
		public static int FLAG_BREATHING = takeNextId();
		public static int FLAG_CHESTED = takeNextId();
		public static int FLAG_STACKABLE = takeNextId();
		public static int FLAG_SHOW_BASE = takeNextId();
		public static int FLAG_REARING = takeNextId();
		public static int FLAG_VIBRATING = takeNextId();
		public static int FLAG_IDLING = takeNextId();
		public static int FLAG_EVOKER_SPELL = takeNextId();
		public static int FLAG_CHARGE_ATTACK = takeNextId();
		public static int FLAG_LINGER = 46;
	}
}
