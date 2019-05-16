package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class AbstractMerchantEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public AbstractMerchantEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.AbstractMerchant.HEAD_SHAKE_TIMER, 15), ProtocolVersionsHelper.UP_1_14_1);
	}

}
