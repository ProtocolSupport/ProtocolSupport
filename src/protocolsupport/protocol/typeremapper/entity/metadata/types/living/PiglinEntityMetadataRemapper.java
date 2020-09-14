package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PiglinEntityMetadataRemapper extends BasePiglingEntityMetadataRemapper {

	public static final PiglinEntityMetadataRemapper INSTANCE = new PiglinEntityMetadataRemapper();

	protected PiglinEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Piglin.IS_BABY, 16), ProtocolVersionsHelper.UP_1_16_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Piglin.IS_BABY, 15), ProtocolVersionsHelper.RANGE__1_16__1_16_1);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Piglin.USING_CROSSBOW, 17), ProtocolVersionsHelper.UP_1_16);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Piglin.DANDCING, 18), ProtocolVersionsHelper.UP_1_16);
	}

}
