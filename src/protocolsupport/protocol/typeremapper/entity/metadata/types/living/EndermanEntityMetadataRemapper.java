package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectShort;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EndermanEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public EndermanEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 14), ProtocolVersionsHelper.UP_1_14);
		addRemapPerVersion(version -> new IndexValueRemapper<NetworkEntityMetadataObjectBlockData>(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 12) {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
			final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectBlockData(BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_13);
		addRemapPerVersion(version -> new IndexValueRemapper<NetworkEntityMetadataObjectBlockData>(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 12) {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectBlockData(BlockRemappingHelper.remapPreFlatteningBlockDataM12(blockDataRemappingTable, object.getValue()));
			}
		}, ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		addRemapPerVersion(version -> new IndexValueRemapper<NetworkEntityMetadataObjectBlockData>(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 11) {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectBlockData(BlockRemappingHelper.remapPreFlatteningBlockDataM12(blockDataRemappingTable, object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectBlockData>(NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK, 16) {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(ProtocolVersion.MINECRAFT_1_8);
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBlockData object) {
				return new NetworkEntityMetadataObjectShort((short) BlockRemappingHelper.remapPreFlatteningBlockDataM12(blockDataRemappingTable, object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		addRemapPerVersion(version -> new NetworkEntityMetadataObjectRemapper() {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
			@Override
			public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
				NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK.getValue(original).ifPresent(object -> {
					int lId = BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, object.getValue());
					remapped.add(16, new NetworkEntityMetadataObjectByte((byte) PreFlatteningBlockIdData.getIdFromCombinedId(lId)));
					remapped.add(17, new NetworkEntityMetadataObjectByte((byte) PreFlatteningBlockIdData.getDataFromCombinedId(lId)));
				});
			}
		}, ProtocolVersionsHelper.BEFORE_1_8);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Enderman.SCREAMING, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Enderman.SCREAMING, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Enderman.SCREAMING, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.Enderman.SCREAMING, 18), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
