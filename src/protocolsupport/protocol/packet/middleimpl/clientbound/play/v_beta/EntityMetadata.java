package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import java.util.Optional;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.DataWatcherSerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectItemStack;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.networkentity.NetworkEntityItemDataCache;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata {

	public EntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_METADATA_ID);
		entitymetadata.writeInt(entityId);
		DataWatcherSerializer.writeLegacyData(entitymetadata, version, cache.getAttributesCache().getLocale(), entityRemapper.getRemappedMetadata());

		if (entity != null && entity.getType() == NetworkEntityType.ITEM) {
			NetworkEntityItemDataCache itemdatacache = (NetworkEntityItemDataCache) entity.getDataCache();
			if (itemdatacache.needsSpawn()) {
				Optional<DataWatcherObjectItemStack> itemstackObject = (DataWatcherObjectIndex.Item.ITEM.getValue(entityRemapper.getOriginalMetadata()));
				if (itemstackObject.isPresent()) {
					NetworkItemStack itemstack = itemstackObject.get().getValue();
					if (!itemstack.isNull()) {
						itemdatacache.resetNeedsSpawn();

						ClientBoundPacketData spawnitem = ClientBoundPacketData.create(ClientBoundPacket.LEGACY_PLAY_SPAWN_ITEM);
						spawnitem.writeInt(entity.getId());
						ItemStackSerializer.writeItemStack(spawnitem, version, I18NData.DEFAULT_LOCALE, itemstack);
						spawnitem.writeInt((int) (itemdatacache.getX() * 32));
						spawnitem.writeInt((int) (itemdatacache.getY() * 32));
						spawnitem.writeInt((int) (itemdatacache.getZ() * 32));
						//TODO: motion
						spawnitem.writeByte(0);
						spawnitem.writeByte(0);
						spawnitem.writeByte(0);

						RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
						packets.add(spawnitem);
						packets.add(entitymetadata);
						return packets;
					}
				}
			}
		}

		return RecyclableSingletonList.create(entitymetadata);
	}

}
