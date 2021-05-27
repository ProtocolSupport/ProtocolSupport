package protocolsupport.protocol.typeremapper.entity;

import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataEntry;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class NetworkEntityTransformHelper {

	public static void transformMetadata(
		NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> metadata,
		NetworkEntityLegacyDataEntry legacyEntityEntry, NetworkEntityLegacyFormatTable entityFormatTable,
		NetworkEntityMetadataList fMetadata
	) {
		legacyEntityEntry.transformMetadata(metadata);
		transformMetadataFormat(entity, legacyEntityEntry.getType(), metadata, entityFormatTable, fMetadata);
	}

	public static void transformMetadataFormat(
		NetworkEntity entity, NetworkEntityType type, ArrayMap<NetworkEntityMetadataObject<?>> metadata,
		NetworkEntityLegacyFormatTable entityFormatTable,
		NetworkEntityMetadataList fMetadata
	) {
		for (NetworkEntityMetadataFormatTransformer fMetadataTransformer : entityFormatTable.get(type).getMetadataTransformers()) {
			fMetadataTransformer.transform(entity, metadata, fMetadata);
		}
		entity.getDataCache().unsetFirstMeta();
	}

}
