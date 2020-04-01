package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import java.util.Optional;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13.AbstractEntityMetadata;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.NetworkEntityItemDataCache;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.utils.i18n.I18NData;

public class EntityMetadata extends AbstractEntityMetadata {

	public EntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient0(NetworkEntityMetadataList remappedMetadata) {
		if (entity.getType() == NetworkEntityType.ITEM) {
			NetworkEntityItemDataCache itemdatacache = (NetworkEntityItemDataCache) entity.getDataCache();
			if (itemdatacache.needsSpawn()) {
				Optional<NetworkEntityMetadataObjectItemStack> itemstackObject = NetworkEntityMetadataObjectIndex.Item.ITEM.getValue(metadata);
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
						//TODO: implement motion
						spawnitem.writeByte(0);
						spawnitem.writeByte(0);
						spawnitem.writeByte(0);

						codec.write(spawnitem);
					}
				}
			}
		}

		super.writeToClient0(remappedMetadata);
	}

	@Override
	protected ClientBoundPacketData createEntityMetadata(NetworkEntityMetadataList remappedMetadata) {
		ClientBoundPacketData entitymetadata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_METADATA);
		entitymetadata.writeInt(entityId);
		NetworkEntityMetadataSerializer.writeLegacyData(entitymetadata, version, cache.getAttributesCache().getLocale(), remappedMetadata);
		return entitymetadata;
	}

	@Override
	protected ClientBoundPacketData createUseBed(Position position) {
		ClientBoundPacketData usebed = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LEGACY_PLAY_USE_BED_ID);
		usebed.writeInt(entityId);
		usebed.writeByte(0);
		PositionSerializer.writeLegacyPositionB(usebed, position);
		return usebed;
	}

}
