package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import java.util.Optional;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.NetworkEntityItemDataCache;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata {

	public EntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_METADATA);
		entitymetadata.writeInt(entityId);
		NetworkEntityMetadataSerializer.writeLegacyData(entitymetadata, version, cache.getAttributesCache().getLocale(), entityRemapper.getRemappedMetadata());

		switch (entity.getType()) {
			case PLAYER: {
				Optional<NetworkEntityMetadataObjectOptionalPosition> bedpositionObject = NetworkEntityMetadataObjectIndex.EntityLiving.BED_LOCATION.getValue(entityRemapper.getOriginalMetadata());
				if (bedpositionObject.isPresent()) {
					Position bedposition = bedpositionObject.get().getValue();
					if (bedposition != null) {
						RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
						packets.add(entitymetadata);

						ClientBoundPacketData usebed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED_ID);
						usebed.writeInt(entityId);
						usebed.writeByte(0);
						PositionSerializer.writeLegacyPositionB(usebed, bedposition);
						packets.add(usebed);

						return packets;
					}
				}
				break;
			}
			case ITEM: {
				NetworkEntityItemDataCache itemdatacache = (NetworkEntityItemDataCache) entity.getDataCache();
				if (itemdatacache.needsSpawn()) {
					Optional<NetworkEntityMetadataObjectItemStack> itemstackObject = (NetworkEntityMetadataObjectIndex.Item.ITEM.getValue(entityRemapper.getOriginalMetadata()));
					if (itemstackObject.isPresent()) {
						NetworkItemStack itemstack = itemstackObject.get().getValue();
						if (!itemstack.isNull()) {
							itemdatacache.resetNeedsSpawn();

							ClientBoundPacketData spawnitem = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_SPAWN_ITEM);
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
				break;
			}
			default: {
				break;
			}
		}

		return RecyclableSingletonList.create(entitymetadata);
	}

}
