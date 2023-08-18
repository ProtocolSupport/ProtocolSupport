package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectBlockData extends NetworkEntityMetadataObject<Integer> {

	public NetworkEntityMetadataObjectBlockData(int blockdata) {
		this.value = blockdata;
	}

}
