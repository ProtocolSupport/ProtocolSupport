package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.serializer.DataWatcherSerializer;
import protocolsupport.protocol.typeremapper.pe.PEBlocks;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectSVarInt;
import protocolsupport.protocol.types.networkentity.NetworkEntityItemDataCache;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.ServerPlatform;

public class SpawnObject extends MiddleSpawnObject {

	public SpawnObject(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ArrayMap<NetworkEntityMetadataObject<?>> spawnmeta = null;
		switch (entity.getType()) {
			case ITEM: {
				((NetworkEntityItemDataCache) entity.getDataCache()).setData(x, y, z, motX / 8000F, motY / 8000F, motZ / 8000F);
				return RecyclableEmptyList.get();
			}
			case ITEM_FRAME: {
				return RecyclableEmptyList.get();
			}
			case FALLING_OBJECT: {
				spawnmeta = new ArrayMap<>(DataWatcherSerializer.MAX_USED_META_INDEX + 1);
				int pocketBlock = PEBlocks.toPocketBlock(version, ServerPlatform.get().getMiscUtils().getBlockDataByNetworkId(objectdata));
				y -= 0.1; //Freaking PE pushes block because block breaks after sand is spawned
				spawnmeta.put(PeMetaBase.VARIANT, new NetworkEntityMetadataObjectSVarInt(pocketBlock));
			}
			default: {
				PEDataValues.PEEntityData typeData = PEDataValues.getEntityData(entity.getType());
				if (typeData != null && typeData.getOffset() != null) {
					PEDataValues.PEEntityData.Offset offset = typeData.getOffset();
					x += offset.getX();
					y += offset.getY();
					z += offset.getZ();
					pitch += offset.getPitch();
					yaw += offset.getYaw();
				}
				entity.getDataCache().setPos((float) x, (float) y, (float) z);
				entity.getDataCache().setYaw(yaw);
				entity.getDataCache().setPitch(pitch);
				return RecyclableSingletonList.create(SpawnLiving.create(
					version, cache.getAttributesCache().getLocale(),
					entity,
					(float) x, (float) y, (float) z,
					motX / 8000.F, motY / 8000.F, motZ / 8000.F,
					pitch * 360.F / 256.F, yaw * 360.F / 256.F, 0,
					spawnmeta
				));
			}
		}
	}

}
