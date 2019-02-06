package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockData;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShort;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class EndermanEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public EndermanEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 12), ProtocolVersionsHelper.UP_1_13);
		addRemapPerVersion(version -> new IndexValueRemapper<DataWatcherObjectBlockData>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 12) {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockData object) {
				return new DataWatcherObjectBlockData(BlockRemappingHelper.remapBlockDataM12(blockDataRemappingTable, object.getValue()));
			}
		}, ProtocolVersionsHelper.RANGE__1_10__1_12_2);
		addRemapPerVersion(version -> new IndexValueRemapper<DataWatcherObjectBlockData>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 11) {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockData object) {
				return new DataWatcherObjectBlockData(BlockRemappingHelper.remapBlockDataM12(blockDataRemappingTable, object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<DataWatcherObjectBlockData>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 16) {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(ProtocolVersion.MINECRAFT_1_8);
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockData object) {
				return new DataWatcherObjectShort((short) BlockRemappingHelper.remapBlockDataM12(blockDataRemappingTable, object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		addRemapPerVersion(version -> new DataWatcherObjectRemapper() {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Enderman.CARRIED_BLOCK.getValue(original).ifPresent(object -> {
					int lId = BlockRemappingHelper.remapBlockDataNormal(blockDataRemappingTable, object.getValue());
					remapped.put(16, new DataWatcherObjectByte((byte) PreFlatteningBlockIdData.getIdFromCombinedId(lId)));
					remapped.put(17, new DataWatcherObjectByte((byte) PreFlatteningBlockIdData.getDataFromCombinedId(lId)));
				});
			}
		}, ProtocolVersionsHelper.BEFORE_1_8);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.SCREAMING, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.SCREAMING, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Enderman.SCREAMING, 18), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
