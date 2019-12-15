package protocolsupport.protocol.packet.middle.clientbound.play;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.storage.netcache.WatchedEntityCache;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class MiddleEntityMetadata extends MiddleEntity {

	protected final WatchedEntityCache entityCache = cache.getWatchedEntityCache();

	protected final EntityRemappingTable entityRemappingTable = EntityRemappersRegistry.REGISTRY.getTable(version);

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

	protected final NetworkEntityMetadataList remappedMetadata = new NetworkEntityMetadataList();

	@Override
	public void writeToClient() {
		if (entity == null) {
			return;
		}

		Pair<NetworkEntityType, List<NetworkEntityMetadataObjectRemapper>> entityRemapper = entityRemappingTable.getRemap(entity.getType());
		if (entityRemapper == null) {
			throw new IllegalStateException(MessageFormat.format("No entity remapper exists for entity type {0}", entity.getType()));
		}
		for (NetworkEntityMetadataObjectRemapper remapper : entityRemapper.getRight()) {
			remapper.remap(entity, metadata, remappedMetadata);
		}
		entity.getDataCache().unsetFirstMeta();

		writeToClient0(remappedMetadata);
	}

	protected abstract void writeToClient0(NetworkEntityMetadataList remappedMetadata);

	@Override
	public void postHandle() {
		metadata.clear();
		remappedMetadata.clear();
	}

}
