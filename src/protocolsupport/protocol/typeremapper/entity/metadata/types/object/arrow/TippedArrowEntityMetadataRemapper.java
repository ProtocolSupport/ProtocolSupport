package protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TippedArrowEntityMetadataRemapper extends ArrowEntityMetadataRemapper {

	public static final TippedArrowEntityMetadataRemapper INSTANCE = new TippedArrowEntityMetadataRemapper();

	protected TippedArrowEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 10), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 6), ProtocolVersionsHelper.ALL_1_9);
	}

}
