package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class BoatEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public BoatEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 5), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 17), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 18), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.BEFORE_1_6);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.VARIANT, 9), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.LEFT_PADDLE, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.RIGHT_PADDLE, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.SPLASH_TIMER, 12), ProtocolVersionsHelper.UP_1_13);
	}

}
