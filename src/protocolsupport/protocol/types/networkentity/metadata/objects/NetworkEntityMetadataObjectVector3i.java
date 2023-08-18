package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVector3i extends NetworkEntityMetadataObject<Position> {

	public NetworkEntityMetadataObjectVector3i(Position pos) {
		this.value = pos;
	}

}
