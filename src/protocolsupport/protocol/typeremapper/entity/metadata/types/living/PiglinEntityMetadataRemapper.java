package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PiglinEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final PiglinEntityMetadataRemapper INSTANCE = new PiglinEntityMetadataRemapper();

	protected PiglinEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Piglin.IS_BABY, 15), ProtocolVersionsHelper.UP_1_16);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Piglin.ZOMBIFICATION_IMMUNITY, 16), ProtocolVersionsHelper.UP_1_16);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Piglin.USING_CROSSBOW, 17), ProtocolVersionsHelper.UP_1_16);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Piglin.DANDCING, 18), ProtocolVersionsHelper.UP_1_16);
	}

}
