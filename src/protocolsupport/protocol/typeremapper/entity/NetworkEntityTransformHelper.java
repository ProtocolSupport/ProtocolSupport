package protocolsupport.protocol.typeremapper.entity;

import javax.annotation.Nonnull;

import protocolsupport.protocol.codec.NetworkEntityMetadataCodec.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatEntry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataEntry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class NetworkEntityTransformHelper {

	private NetworkEntityTransformHelper() {
	}

	public static @Nonnull NetworkEntityType transformTypeFormat(
		@Nonnull NetworkEntityType type,
		@Nonnull NetworkEntityLegacyDataTable dataTable, @Nonnull NetworkEntityLegacyFormatTable formatTable
	) {
		NetworkEntityType lType = dataTable.get(type).getType();
		if (lType == NetworkEntityType.NONE) {
			return NetworkEntityType.NONE;
		}
		return formatTable.get(lType).getType();
	}

	public static void transformMetadata(
		@Nonnull NetworkEntity entity, @Nonnull ArrayMap<NetworkEntityMetadataObject<?>> metadata,
		@Nonnull NetworkEntityLegacyDataEntry entityLegacyDataEntry, @Nonnull NetworkEntityLegacyFormatEntry entityLegacyFormatEntry,
		@Nonnull NetworkEntityMetadataList legacyFormatMetadata
	) {
		entityLegacyDataEntry.transformMetadata(metadata);
		transformMetadataFormat(entity, metadata, entityLegacyFormatEntry, legacyFormatMetadata);
	}

	public static void transformMetadataFormat(
		@Nonnull NetworkEntity entity, @Nonnull ArrayMap<NetworkEntityMetadataObject<?>> metadata,
		@Nonnull NetworkEntityLegacyFormatEntry entityLegacyFormatEntry,
		@Nonnull NetworkEntityMetadataList fMetadata
	) {
		for (NetworkEntityMetadataFormatTransformer fMetadataTransformer : entityLegacyFormatEntry.getMetadataTransformers()) {
			fMetadataTransformer.transform(entity, metadata, fMetadata);
		}
		entity.getDataCache().unsetFirstMeta();
	}

}
