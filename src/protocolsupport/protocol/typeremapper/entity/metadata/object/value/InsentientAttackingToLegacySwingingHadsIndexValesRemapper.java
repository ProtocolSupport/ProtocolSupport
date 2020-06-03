package protocolsupport.protocol.typeremapper.entity.metadata.object.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.utils.BitUtils;

public class InsentientAttackingToLegacySwingingHadsIndexValesRemapper extends IndexValueRemapper<NetworkEntityMetadataObjectByte> {

	public InsentientAttackingToLegacySwingingHadsIndexValesRemapper(int toIndex) {
		super(NetworkEntityMetadataObjectIndex.Insentient.INS_FLAGS, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectByte object) {
		return new NetworkEntityMetadataObjectBoolean(BitUtils.isIBitSet(object.getValue(), NetworkEntityMetadataObjectIndex.Insentient.INS_FLAGS_BIT_ATTACKING));
	}

}
