package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.PEItemEntityCache.ItemEntityInfo;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		switch (entity.getType()) {
			case ITEM: {
				cache.getPEItemEntityCache().addItem(new ItemEntityInfo(entity.getId(), x, y, z, motX / 8.000F, motY / 8000.F, motZ / 8000.F));
				return RecyclableEmptyList.get();
			}
			case ITEM_FRAME: {
				return RecyclableEmptyList.get();
			}
			default: {
				return RecyclableSingletonList.create(SpawnLiving.create(
					version,
					entity,
					x, y, z,
					motX / 8.000F, motY / 8000.F, motZ / 8000.F,
					pitch, yaw, cache.getAttributesCache().getLocale(),
					null, //TODO: Add spawnmeta to something like sand.
					PEDataValues.getObjectEntityTypeId(IdRemapper.ENTITY.getTable(version).getRemap(entity.getType()))
				));
			}
		}
	}

}
