package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TurtleEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public TurtleEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.HOME_POS, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.HOME_POS, 13), ProtocolVersionsHelper.ALL_1_13);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.HAS_EGG, 16), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.HAS_EGG, 14), ProtocolVersionsHelper.ALL_1_13);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.LAYING_EGG, 17), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.LAYING_EGG, 15), ProtocolVersionsHelper.ALL_1_13);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.TRAVEL_POS, 18), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.TRAVEL_POS, 16), ProtocolVersionsHelper.ALL_1_13);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.GOING_HOME, 19), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.GOING_HOME, 17), ProtocolVersionsHelper.ALL_1_13);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.TRAVELING, 20), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Turtle.TRAVELING, 18), ProtocolVersionsHelper.ALL_1_13);
	}

}
