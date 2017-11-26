package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnObject.PreparedItem;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIdRegistry;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarLong;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class EntityMetadata extends MiddleEntityMetadata {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		NetworkEntity entity = cache.getWatchedEntity(entityId);

		if(entity == null) {
			return packets;
		}
		switch(entity.getType()) {
			case ITEM: {
				DataWatcherObject<?> itemWatcher = metadata.get(DataWatcherObjectIndex.Item.ITEM);
				if(itemWatcher != null) {
					PreparedItem i = cache.getPreparedItem(entityId);
					if(i != null) {
						packets.addAll(i.updateItem(connection.getVersion(), (ItemStackWrapper) itemWatcher.getValue()));
					}
				}
			}
			default: {
				if (entity.isOfType(NetworkEntityType.LIVING)) {
					DataWatcherObject<?> healthWatcher = metadata.get(DataWatcherObjectIndex.EntityLiving.HEALTH);
					if (healthWatcher != null) {
						packets.add(EntitySetAttributes.create(connection.getVersion(), entity.getId(), EntitySetAttributes.createAttribute("minecraft:health", (Float) healthWatcher.getValue())));
					}
				}
				packets.add(create(entity, cache.getLocale(), metadata, connection.getVersion()));
			}
		}

		return packets;
	}

	public static ClientBoundPacketData createFaux(NetworkEntity entity, String locale, ArrayMap<DataWatcherObject<?>> fauxMeta, ProtocolVersion version) {
		return create(entity, locale, transform(entity, fauxMeta, version), version);
	}

	public static ClientBoundPacketData createFaux(NetworkEntity entity, String locale, ProtocolVersion version) {
		return create(entity, locale, transform(entity, new ArrayMap<DataWatcherObject<?>>(76), version), version);
	}

	public static ArrayMap<DataWatcherObject<?>> transform(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> pcMetadata, ProtocolVersion version) {
		ArrayMap<DataWatcherObject<?>> peMetadata = WatchedDataRemapper.transform(entity, pcMetadata, version);
		peMetadata.put(0, new DataWatcherObjectSVarLong(entity.getDataCache().getPeBaseFlags()));
		return peMetadata;
	}

	public static ClientBoundPacketData create(NetworkEntity entity, String locale, ArrayMap<DataWatcherObject<?>> metadata, ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_ENTITY_DATA, version);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		EntityMetadata.encodeMeta(serializer, version, locale, transform(entity, metadata, version));
		return serializer;
	}

	public static void encodeMeta(ByteBuf to, ProtocolVersion version, String locale, ArrayMap<DataWatcherObject<?>> peMetadata) {

		//For now. Iterate two times :P
		int entries = 0;
		for (int key = peMetadata.getMinKey(); key < peMetadata.getMaxKey(); key++) {
			DataWatcherObject<?> object = peMetadata.get(key);
			if (object != null) {
				entries++;
			}
		}
		//We stored that. Now write the length first and then go.
		VarNumberSerializer.writeVarInt(to, entries);
		for (int key = peMetadata.getMinKey(); key < peMetadata.getMaxKey(); key++) {
			DataWatcherObject<?> object = peMetadata.get(key);
			if (object != null) {
				VarNumberSerializer.writeVarInt(to, key);
				VarNumberSerializer.writeVarInt(to, DataWatcherObjectIdRegistry.getTypeId(object, version));
				object.writeToStream(to, version, locale);
				entries++;
			}
		}


		//TODO Fake this stuff.
		/*VarNumberSerializer.writeVarInt(to, 0); //Fake
		int entries = 0;
		for (int key = peMetadata.getMinKey(); key < peMetadata.getMaxKey(); key++) {
			DataWatcherObject<?> object = peMetadata.get(key);
			if (object != null) {
				VarNumberSerializer.writeVarInt(to, key);
				VarNumberSerializer.writeVarInt(to, DataWatcherObjectIdRegistry.getTypeId(object, version));
				object.writeToStream(to, version, locale);
				entries++;
			}
		}*/

	}

	public static class PeMetaBase {

		//PE's extra baseflags. TODO: Implement more flags (Easy Remapping)
		protected static int id = 1;
		protected static int takeNextId() {
			return id++;
		}

		public static int FLAG_ON_FIRE = takeNextId(); //0
		public static int FLAG_SNEAKING = takeNextId(); //1
		public static int FLAG_RIDING = takeNextId();
		public static int FLAG_SPRINTING = takeNextId();
		public static int FLAG_USING_ITEM = takeNextId();
		public static int FLAG_INVISIBLE = takeNextId();
		public static int FLAG_TEMPTED = takeNextId();
		public static int FLAG_IN_LOVE = takeNextId();
		public static int FLAG_SADDLED = takeNextId();
		public static int FLAG_POWERED = takeNextId(); 
		public static int FLAG_IGNITED = takeNextId(); //10
		public static int FLAG_BABY = takeNextId();
		public static int FLAG_CONVERTING = takeNextId();
		public static int FLAG_CRITICAL = takeNextId();
		public static int FLAG_SHOW_NAMETAG = takeNextId();
		public static int FLAG_ALWAYS_SHOW_NAMETAG = takeNextId();
		public static int FLAG_NO_AI = takeNextId();
		public static int FLAG_SILENT = takeNextId();
		public static int FLAG_CLIMBING = takeNextId();
		public static int FLAG_CAN_CLIMB = takeNextId(); 
		public static int FLAG_CAN_SWIM = takeNextId(); //20
		public static int FLAG_CAN_FLY = takeNextId();
		public static int FLAG_RESTING = takeNextId();
		public static int FLAG_SITTING = takeNextId();
		public static int FLAG_ANGRY = takeNextId();
		public static int FLAG_INTERESTED = takeNextId();
		public static int FLAG_CHARGED = takeNextId();
		public static int FLAG_TAMED = takeNextId();
		public static int FLAG_LEASHED = takeNextId();
		public static int FLAG_SHEARED = takeNextId();
		public static int FLAG_GLIDING = takeNextId(); //30
		public static int FLAG_ELDER = takeNextId();
		public static int FLAG_MOVING = takeNextId();
		public static int FLAG_BREATHING = takeNextId();
		public static int FLAG_CHESTED = takeNextId();
		public static int FLAG_STACKABLE = takeNextId();
		public static int FLAG_SHOW_BASE = takeNextId();
		public static int FLAG_REARING = takeNextId();
		public static int FLAG_VIBRATING = takeNextId();
		public static int FLAG_IDLING = takeNextId();
		public static int FLAG_EVOKER_SPELL = takeNextId(); //40
		public static int FLAG_CHARGE_ATTACK = takeNextId();
		public static int FLAG_WASD_CONTROLLED = takeNextId();
		public static int FLAG_CAN_POWER_JUMP = takeNextId();
		public static int FLAG_LINGER = takeNextId();
		public static int FLAG_COLLIDE = takeNextId();
		public static int FLAG_GRAVITY = takeNextId();
		public static int FLAG_FIRE_IMMUNE = takeNextId();
		public static int FLAG_DANCING = takeNextId(); //48

	}
}
