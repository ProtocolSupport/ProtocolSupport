package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;

public class AgeableEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final AgeableEntityMetadataRemapper INSTANCE = new AgeableEntityMetadataRemapper();

	public AgeableEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ageable.IS_BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ageable.IS_BABY, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte((byte) (object.getValue() ? -1 : 0));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		addRemap(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectInt((object.getValue() ? -1 : 0));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8);

		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Ageable.AGE_HACK, 12), ProtocolVersionsHelper.RANGE__1_6__1_7);
	}

}
