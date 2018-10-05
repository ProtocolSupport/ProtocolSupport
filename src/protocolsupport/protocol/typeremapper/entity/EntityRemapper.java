package protocolsupport.protocol.typeremapper.entity;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EntityRemapper {

	protected final EntityRemappingTable table;
	public EntityRemapper(ProtocolVersion version) {
		this.table = EntityRemappersRegistry.REGISTRY.getTable(version);
	}

	protected final ArrayMap<DataWatcherObject<?>> originalMetadata = new ArrayMap<>(DataWatcherDeserializer.MAX_USED_META_INDEX + 1);
	protected final ArrayMap<DataWatcherObject<?>> remappedMetadata = new ArrayMap<>(DataWatcherDeserializer.MAX_USED_META_INDEX + 1);

	protected NetworkEntityType remappedEntityType;

	public void readEntityAndRemap(NetworkEntity entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity can't be null");
		}
		remappedEntityType = table.getRemap(entity.getType()).getLeft();
	}

	public void readEntityWithMetadataAndRemap(String locale, NetworkEntity entity, ByteBuf serverdata) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity can't be null");
		}
		originalMetadata.clear();
		remappedMetadata.clear();
		DataWatcherDeserializer.decodeDataTo(serverdata, ProtocolVersionsHelper.LATEST_PC, locale, originalMetadata);
		Pair<NetworkEntityType, List<DataWatcherObjectRemapper>> entityRemapper = table.getRemap(entity.getType());
		if (entityRemapper == null) {
			throw new IllegalStateException(MessageFormat.format("Missing entity remapper entry for entity type {0}", entity.getType()));
		}
		remappedEntityType = entityRemapper.getLeft();
		entityRemapper.getRight().forEach(remapper -> remapper.remap(entity, originalMetadata, remappedMetadata));
		entity.getDataCache().setFirstMeta(false);
	}

	public NetworkEntityType getRemappedEntityType() {
		return remappedEntityType;
	}

	public ArrayMap<DataWatcherObject<?>> getRemappedMetadata() {
		return remappedMetadata;
	}

}
