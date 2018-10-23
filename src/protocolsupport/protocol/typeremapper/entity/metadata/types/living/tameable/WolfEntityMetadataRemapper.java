package protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.TameableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;

public class WolfEntityMetadataRemapper extends TameableEntityMetadataRemapper {

	public WolfEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.DAMAGE_TAKEN, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.DAMAGE_TAKEN, 14), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.DAMAGE_TAKEN, 18), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wolf.DAMAGE_TAKEN, 18), ProtocolVersionsHelper.BEFORE_1_6);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.BEGGING, 16), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.BEGGING, 15), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Wolf.BEGGING, 19), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 17), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 16), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 20), ProtocolVersion.MINECRAFT_1_8);
		addRemap(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 20) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8);
	}

}
