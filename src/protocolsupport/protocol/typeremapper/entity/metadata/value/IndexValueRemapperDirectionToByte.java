package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectDirection;

public final class IndexValueRemapperDirectionToByte extends IndexValueRemapper<NetworkEntityMetadataObjectDirection> {

	public IndexValueRemapperDirectionToByte(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectDirection> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectDirection object) {
		return new NetworkEntityMetadataObjectByte((byte) object.getValue().ordinal());
	}

}
