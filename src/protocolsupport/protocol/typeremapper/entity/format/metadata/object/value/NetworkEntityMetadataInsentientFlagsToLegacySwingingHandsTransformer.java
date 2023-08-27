package protocolsupport.protocol.typeremapper.entity.format.metadata.object.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.utils.BitUtils;

public class NetworkEntityMetadataInsentientFlagsToLegacySwingingHandsTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectByte> {

	public NetworkEntityMetadataInsentientFlagsToLegacySwingingHandsTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectByte object) {
		return new NetworkEntityMetadataObjectBoolean(BitUtils.isIBitSet(object.getValue(), NetworkEntityMetadataObjectIndexRegistry.InsentientIndexRegistry.INS_FLAGS_BIT_ATTACKING));
	}

}
