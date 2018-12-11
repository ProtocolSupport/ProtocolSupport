package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class EndermanEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public EndermanEntityMetadataRemapper() {
		//TODO: Remap this.
//		addRemap(new DataWatcherObjectRemapper() {
//		@Override
//		public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
//			DataWatcherObjectIndex.Enderman.CARRIED_BLOCK.getValue(original).ifPresent(stateWatcher -> {
//				remapped.put(PeMetaBase.ENDERMAN_BLOCK, new DataWatcherObjectShortLe((short) MinecraftData.getBlockIdFromState(stateWatcher.getValue())));
//			});
//		}
//	}, ProtocolVersionsHelper.ALL_PE),
//		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 12), ProtocolVersionsHelper.RANGE__1_10__1_13),
//		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 11), ProtocolVersionsHelper.ALL_1_9),
//		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 16) {
//			@Override
//			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
//				return new DataWatcherObjectShort((short) MinecraftData.getBlockIdFromState(object.getValue()));
//			}
//		}, ProtocolVersion.MINECRAFT_1_8),
//		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 16) {
//			@Override
//			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
//				return new DataWatcherObjectByte((byte) MinecraftData.getBlockIdFromState(object.getValue()));
//			}
//		}, ProtocolVersionsHelper.BEFORE_1_8),
//		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 17) {
//			@Override
//			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
//				return new DataWatcherObjectByte((byte) MinecraftData.getBlockDataFromState(object.getValue()));
//			}
//		}, ProtocolVersionsHelper.BEFORE_1_9),
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.SCREAMING, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.SCREAMING, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Enderman.SCREAMING, 18), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
