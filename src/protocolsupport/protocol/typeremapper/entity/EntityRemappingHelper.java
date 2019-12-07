package protocolsupport.protocol.typeremapper.entity;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EntityRemappingHelper {

	protected final EntityRemappingTable entityRemappingTable;
	public EntityRemappingHelper(EntityRemappingTable entityRemappingTable) {
		this.entityRemappingTable = entityRemappingTable;
	}

	protected NetworkEntityType remappedEntityType;
	protected final NetworkEntityMetadataList remappedMetadata = new NetworkEntityMetadataList();

	public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> metadata) {
		Pair<NetworkEntityType, List<NetworkEntityMetadataObjectRemapper>> entityRemapper = entityRemappingTable.getRemap(entity.getType());
		if (entityRemapper == null) {
			throw new IllegalStateException(MessageFormat.format("No entity remapper exists for entity type {0}", entity.getType()));
		}
		remappedEntityType = entityRemapper.getLeft();
		for (NetworkEntityMetadataObjectRemapper remapper : entityRemapper.getRight()) {
			remapper.remap(entity, metadata, remappedMetadata);
		}
		entity.getDataCache().unsetFirstMeta();
	}

	public NetworkEntityType getRemappedEntityType() {
		return remappedEntityType;
	}

	public NetworkEntityMetadataList getRemappedMetadata() {
		return remappedMetadata;
	}

	public void clear() {
		remappedEntityType = null;
		remappedMetadata.clear();
	}

}
