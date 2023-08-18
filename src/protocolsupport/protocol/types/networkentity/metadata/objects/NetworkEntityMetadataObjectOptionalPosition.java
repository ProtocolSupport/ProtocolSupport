package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalPosition extends NetworkEntityMetadataObject<Position> {

	public NetworkEntityMetadataObjectOptionalPosition() {
	}

	public NetworkEntityMetadataObjectOptionalPosition(Position position) {
		this.value = position;
	}

}
