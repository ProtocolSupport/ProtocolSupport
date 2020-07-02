package protocolsupport.protocol.typeremapper.entity.metadata.object.value;

import java.util.UUID;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalUUID;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;

public class IndexValueRemapperOptionalUUIDToString extends IndexValueRemapper<NetworkEntityMetadataObjectOptionalUUID> {

	public IndexValueRemapperOptionalUUIDToString(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalUUID> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectOptionalUUID object) {
		UUID uuid = object.getValue();
		return new NetworkEntityMetadataObjectString(uuid != null ? uuid.toString() : "");
	}

}
