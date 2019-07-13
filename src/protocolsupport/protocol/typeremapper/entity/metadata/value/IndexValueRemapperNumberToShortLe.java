package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataShortLe;

public final class IndexValueRemapperNumberToShortLe extends IndexValueRemapper<NetworkEntityMetadataObject<Number>> {

	@SuppressWarnings("unchecked")
	public IndexValueRemapperNumberToShortLe(NetworkEntityMetadataObjectIndex<? extends NetworkEntityMetadataObject<? extends Number>> fromIndex, int toIndex) {
		super((NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObject<Number>>) fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObject<Number> object) {
		return new NetworkEntityMetadataShortLe(object.getValue().shortValue());
	}


}
