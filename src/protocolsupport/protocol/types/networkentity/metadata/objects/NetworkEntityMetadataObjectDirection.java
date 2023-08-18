package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.BlockDirection;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectDirection extends NetworkEntityMetadataObject<BlockDirection> {

	public NetworkEntityMetadataObjectDirection(BlockDirection direction) {
		this.value = direction;
	}

}
