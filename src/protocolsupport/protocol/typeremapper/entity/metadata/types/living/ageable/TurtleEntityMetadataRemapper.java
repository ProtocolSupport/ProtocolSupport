package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class TurtleEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public TurtleEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.HOME_POS, 13), ProtocolVersionsHelper.UP_1_13);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.HAS_EGG, 14), ProtocolVersionsHelper.UP_1_13);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.LAYING_EGG, 15), ProtocolVersionsHelper.UP_1_13);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.TRAVEL_POS, 16), ProtocolVersionsHelper.UP_1_13);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.GOING_HOME, 17), ProtocolVersionsHelper.UP_1_13);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.TRAVELING, 18), ProtocolVersionsHelper.UP_1_13);
	}

}
