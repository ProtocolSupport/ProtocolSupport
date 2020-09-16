package protocolsupport.protocol.typeremapper.entity.metadata.object.value;

import java.util.UUID;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalUUID;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;

public class NetworkEntityMetadataObjectIndexValueOptionalUUIDToStringTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectOptionalUUID> {

	public NetworkEntityMetadataObjectIndexValueOptionalUUIDToStringTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalUUID> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectOptionalUUID object) {
		UUID uuid = object.getValue();
		return new NetworkEntityMetadataObjectString(uuid != null ? uuid.toString() : "");
	}

}
