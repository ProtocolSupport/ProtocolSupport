package protocolsupport.protocol.typeremapper.entity.format.metadata.object.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;

public final class NetworkEntityMetadataObjectIndexValueNumberToByteTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObject<Number>> {

	@SuppressWarnings("unchecked")
	public NetworkEntityMetadataObjectIndexValueNumberToByteTransformer(NetworkEntityMetadataObjectIndex<? extends NetworkEntityMetadataObject<? extends Number>> fromIndex, int toIndex) {
		super((NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObject<Number>>) fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObject<Number> object) {
		return new NetworkEntityMetadataObjectByte(object.getValue().byteValue());
	}

}
