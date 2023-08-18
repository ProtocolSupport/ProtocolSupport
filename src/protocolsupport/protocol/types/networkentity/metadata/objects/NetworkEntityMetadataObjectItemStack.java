package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectItemStack extends NetworkEntityMetadataObject<NetworkItemStack> {

	public NetworkEntityMetadataObjectItemStack(NetworkItemStack itemstack) {
		this.value = itemstack;
	}

}
