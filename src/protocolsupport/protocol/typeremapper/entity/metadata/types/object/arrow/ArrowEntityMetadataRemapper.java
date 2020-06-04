package protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow;

import protocolsupport.api.ProtocolVersion;
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
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Arrow.CIRTICAL, 15), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Arrow.SHOOTER, 8), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Arrow.SHOOTER, 7), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_13_1, ProtocolVersion.MINECRAFT_1_13_2));

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Arrow.PIERCING_LEVEL, 9), ProtocolVersionsHelper.UP_1_14);
	}

}
