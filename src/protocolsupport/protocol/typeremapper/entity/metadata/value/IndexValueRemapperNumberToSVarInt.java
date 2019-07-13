package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectSVarInt;

public final class IndexValueRemapperNumberToSVarInt extends IndexValueRemapper<NetworkEntityMetadataObject<Number>> {

	@SuppressWarnings("unchecked")
	public IndexValueRemapperNumberToSVarInt(NetworkEntityMetadataObjectIndex<? extends NetworkEntityMetadataObject<? extends Number>> fromIndex, int toIndex) {
		super((NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObject<Number>>) fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObject<Number> object) {
		return new NetworkEntityMetadataObjectSVarInt(object.getValue().intValue());
	}

}
