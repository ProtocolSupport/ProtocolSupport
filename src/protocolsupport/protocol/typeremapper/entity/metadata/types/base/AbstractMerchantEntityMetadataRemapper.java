package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class AbstractMerchantEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public static final AbstractMerchantEntityMetadataRemapper INSTANCE = new AbstractMerchantEntityMetadataRemapper();

	protected AbstractMerchantEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AbstractMerchant.HEAD_SHAKE_TIMER, 16), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AbstractMerchant.HEAD_SHAKE_TIMER, 15), ProtocolVersionsHelper.RANGE__1_14_1__1_14_4);
	}

}
