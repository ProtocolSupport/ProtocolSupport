package protocolsupport.protocol.typeremapper.entity;

import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry.LegacyNetworkEntityEntry;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityDataFormatTransformRegistry.NetworkEntityDataFormatTransformerTable;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class NetworkEntityTransformHelper {

	public static void transformMetadata(
		NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> metadata,
		LegacyNetworkEntityEntry legacyEntityEntry, NetworkEntityDataFormatTransformerTable entityFormatTable,
		NetworkEntityMetadataList fMetadata
	) {
		legacyEntityEntry.transformMetadata(metadata);
		transformMetadataFormat(entity, legacyEntityEntry.getType(), metadata, entityFormatTable, fMetadata);
	}

	public static void transformMetadataFormat(
		NetworkEntity entity, NetworkEntityType type, ArrayMap<NetworkEntityMetadataObject<?>> metadata,
		NetworkEntityDataFormatTransformerTable entityFormatTable,
		NetworkEntityMetadataList fMetadata
	) {
		for (NetworkEntityMetadataFormatTransformer fMetadataTransformer : entityFormatTable.get(type).getValue()) {
			fMetadataTransformer.transform(entity, metadata, fMetadata);
		}
		entity.getDataCache().unsetFirstMeta();
	}

}
