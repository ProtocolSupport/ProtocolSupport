package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectChat extends NetworkEntityMetadataObject<BaseComponent> {

	public NetworkEntityMetadataObjectChat(BaseComponent component) {
		this.value = component;
	}

}
