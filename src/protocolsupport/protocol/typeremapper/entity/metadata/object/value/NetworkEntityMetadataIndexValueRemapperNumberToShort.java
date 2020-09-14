package protocolsupport.protocol.typeremapper.entity.metadata.object.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectShort;

public class NetworkEntityMetadataIndexValueRemapperNumberToShort extends NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObject<Number>> {

	@SuppressWarnings("unchecked")
	public NetworkEntityMetadataIndexValueRemapperNumberToShort(NetworkEntityMetadataObjectIndex<? extends NetworkEntityMetadataObject<? extends Number>> fromIndex, int toIndex) {
		super((NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObject<Number>>) fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObject<Number> object) {
		return new NetworkEntityMetadataObjectShort(object.getValue().shortValue());
	}

}
