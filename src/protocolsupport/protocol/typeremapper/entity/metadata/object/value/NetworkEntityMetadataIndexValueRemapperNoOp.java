package protocolsupport.protocol.typeremapper.entity.metadata.object.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;

public class NetworkEntityMetadataIndexValueRemapperNoOp extends NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObject<Object>> {

	@SuppressWarnings("unchecked")
	public NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex<? extends NetworkEntityMetadataObject<?>> fromIndex, int toIndex) {
		super((NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObject<Object>>) fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObject<Object> object) {
		return object;
	}

}
