package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.storage.netcache.WatchedEntityCache;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappingHelper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class MiddleEntityMetadata extends MiddleEntity {

	protected final WatchedEntityCache entityCache = cache.getWatchedEntityCache();

	public MiddleEntityMetadata(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected final ArrayMap<NetworkEntityMetadataObject<?>> metadata = new ArrayMap<>(EntityRemappersRegistry.MAX_METADATA_INDEX + 1);

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		entity = entityCache.getWatchedEntity(entityId);
		NetworkEntityMetadataSerializer.readDataTo(serverdata, metadata);
	}

	protected final EntityRemappingHelper entityRemapper = new EntityRemappingHelper(EntityRemappersRegistry.REGISTRY.getTable(version));

	@Override
	public void writeToClient() {
		if (entity == null) {
			return;
		}

		entityRemapper.remap(entity, metadata);
		writeToClient0(entityRemapper.getRemappedMetadata());
	}

	protected abstract void writeToClient0(NetworkEntityMetadataList remappedMetadata);

	@Override
	public void postHandle() {
		metadata.clear();
		entityRemapper.clear();
	}

}
