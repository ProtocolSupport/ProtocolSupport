package protocolsupport.protocol.typeremapper.entity;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.DataWatcherSerializer;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EntityRemapper {

	protected final EntityRemappingTable table;
	public EntityRemapper(ProtocolVersion version) {
		this.table = EntityRemappersRegistry.REGISTRY.getTable(version);
	}

	protected NetworkEntity originalEntity;
	protected final ArrayMap<NetworkEntityMetadataObject<?>> originalMetadata = new ArrayMap<>(DataWatcherSerializer.MAX_USED_META_INDEX + 1);

	protected NetworkEntityType remappedEntityType;
	protected final ArrayMap<NetworkEntityMetadataObject<?>> remappedMetadata = new ArrayMap<>(DataWatcherSerializer.MAX_USED_META_INDEX + 1);

	public void readEntity(NetworkEntity entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity can't be null");
		}
		originalEntity = entity;
	}

	public void readEntityWithMetadata(NetworkEntity entity, ByteBuf serverdata) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity can't be null");
		}
		originalEntity = entity;
		originalMetadata.clear();
		DataWatcherSerializer.readDataTo(serverdata, originalMetadata);
	}

	public void remap(boolean metadata) {
		Pair<NetworkEntityType, List<NetworkEntityMetadataObjectRemapper>> entityRemapper = table.getRemap(originalEntity.getType());
		if (entityRemapper == null) {
			throw new IllegalStateException(MessageFormat.format("Missing entity remapper entry for entity type {0}", originalEntity.getType()));
		}
		remappedEntityType = entityRemapper.getLeft();
		if (metadata) {
			remappedMetadata.clear();
			entityRemapper.getRight().forEach(remapper -> remapper.remap(originalEntity, originalMetadata, remappedMetadata));
			originalEntity.getDataCache().setFirstMeta(false);
		}
	}

	public ArrayMap<NetworkEntityMetadataObject<?>> getOriginalMetadata() {
		return originalMetadata;
	}

	public NetworkEntityType getRemappedEntityType() {
		return remappedEntityType;
	}

	public ArrayMap<NetworkEntityMetadataObject<?>> getRemappedMetadata() {
		return remappedMetadata;
	}

}
