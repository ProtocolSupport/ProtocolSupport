package protocolsupport.protocol.typeremapper.entity.metadata.object.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;

public class NetworkEntityMetadataIndexValueRemapperBooleanToByte extends NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObjectBoolean> {

	public NetworkEntityMetadataIndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectBoolean object) {
		return new NetworkEntityMetadataObjectByte((byte) (object.getValue() ? 1 : 0));
	}

}
