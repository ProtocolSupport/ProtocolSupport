package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalChat extends NetworkEntityMetadataObject<BaseComponent> {

	public NetworkEntityMetadataObjectOptionalChat() {
	}

	public NetworkEntityMetadataObjectOptionalChat(BaseComponent component) {
		this.value = component;
	}

}
