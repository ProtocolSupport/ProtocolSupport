package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalVarInt extends NetworkEntityMetadataObject<Integer> {

	public NetworkEntityMetadataObjectOptionalVarInt() {
	}

	public NetworkEntityMetadataObjectOptionalVarInt(int i) {
		this.value = i;
	}

}
