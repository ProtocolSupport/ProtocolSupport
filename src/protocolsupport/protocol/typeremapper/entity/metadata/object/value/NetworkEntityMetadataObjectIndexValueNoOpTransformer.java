package protocolsupport.protocol.typeremapper.entity.metadata.object.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;

public class NetworkEntityMetadataObjectIndexValueNoOpTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObject<Object>> {

	@SuppressWarnings("unchecked")
	public NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex<? extends NetworkEntityMetadataObject<?>> fromIndex, int toIndex) {
		super((NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObject<Object>>) fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObject<Object> object) {
		return object;
	}

}
