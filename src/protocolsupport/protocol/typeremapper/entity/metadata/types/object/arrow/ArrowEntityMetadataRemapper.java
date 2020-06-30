package protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ArrowEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public static final ArrowEntityMetadataRemapper INSTANCE = new ArrowEntityMetadataRemapper();

	protected ArrowEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Arrow.CIRTICAL, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Arrow.CIRTICAL, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Arrow.CIRTICAL, 5), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Arrow.CIRTICAL, 15), ProtocolVersionsHelper.DOWN_1_8);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Arrow.PIERCING_LEVEL, 8), ProtocolVersionsHelper.UP_1_16);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Arrow.PIERCING_LEVEL, 9), ProtocolVersionsHelper.RANGE__1_14__1_15_2);
	}

}
