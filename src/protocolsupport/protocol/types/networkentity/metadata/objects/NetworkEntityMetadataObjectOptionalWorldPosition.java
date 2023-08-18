package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.WorldPosition;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalWorldPosition extends NetworkEntityMetadataObject<WorldPosition> {

	public NetworkEntityMetadataObjectOptionalWorldPosition() {
	}

	public NetworkEntityMetadataObjectOptionalWorldPosition(WorldPosition position) {
		this.value = position;
	}

}
