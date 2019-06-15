package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.utils.Utils;

public class InsentientAttackingToLegacySwingingHadsIndexValesRemapper extends IndexValueRemapper<NetworkEntityMetadataObjectByte> {

	public InsentientAttackingToLegacySwingingHadsIndexValesRemapper(int toIndex) {
		super(NetworkEntityMetadataObjectIndex.Insentient.FLAGS, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectByte object) {
		return new NetworkEntityMetadataObjectBoolean(Utils.isBitSet(object.getValue(), NetworkEntityMetadataObjectIndex.Insentient.FLAGS_BIT_ATTACKING));
	}

}
