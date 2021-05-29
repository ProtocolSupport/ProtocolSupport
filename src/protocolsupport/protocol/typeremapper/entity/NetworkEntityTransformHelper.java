package protocolsupport.protocol.typeremapper.entity;

import javax.annotation.Nonnull;

import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataEntry;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class NetworkEntityTransformHelper {

	private NetworkEntityTransformHelper() {
	}

	public static void transformMetadata(
		@Nonnull NetworkEntity entity, @Nonnull ArrayMap<NetworkEntityMetadataObject<?>> metadata,
		@Nonnull NetworkEntityLegacyDataEntry entityLegacyDataTable, @Nonnull NetworkEntityLegacyFormatTable entityLegacyFormatTable,
		@Nonnull NetworkEntityMetadataList legacyFormatMetadata
	) {
		entityLegacyDataTable.transformMetadata(metadata);
		transformMetadataFormat(entity, entityLegacyDataTable.getType(), metadata, entityLegacyFormatTable, legacyFormatMetadata);
	}

	public static void transformMetadataFormat(
		@Nonnull NetworkEntity entity, @Nonnull NetworkEntityType type, @Nonnull ArrayMap<NetworkEntityMetadataObject<?>> metadata,
		@Nonnull NetworkEntityLegacyFormatTable entityLegacyFormatTable,
		@Nonnull NetworkEntityMetadataList fMetadata
	) {
		for (NetworkEntityMetadataFormatTransformer fMetadataTransformer : entityLegacyFormatTable.get(type).getMetadataTransformers()) {
			fMetadataTransformer.transform(entity, metadata, fMetadata);
		}
		entity.getDataCache().unsetFirstMeta();
	}

}
