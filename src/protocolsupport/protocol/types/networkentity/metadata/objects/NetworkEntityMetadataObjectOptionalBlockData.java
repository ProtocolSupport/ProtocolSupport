package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalBlockData extends NetworkEntityMetadataObject<Integer> {

	public NetworkEntityMetadataObjectOptionalBlockData(int blockdata) {
		this.value = blockdata;
	}

}
