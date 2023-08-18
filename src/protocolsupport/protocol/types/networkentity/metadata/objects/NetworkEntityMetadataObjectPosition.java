package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectPosition extends NetworkEntityMetadataObject<Position> {

	public NetworkEntityMetadataObjectPosition(Position position) {
		this.value = position;
	}

}
