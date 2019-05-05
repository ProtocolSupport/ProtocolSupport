package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;

public class IndexValueRemapperNoOp extends IndexValueRemapper<NetworkEntityMetadataObject<Object>> {

	@SuppressWarnings("unchecked")
	public IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex<? extends NetworkEntityMetadataObject<?>> fromIndex, int toIndex) {
		super((NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObject<Object>>) fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObject<Object> object) {
		return object;
	}

}
