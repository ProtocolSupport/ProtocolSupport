package protocolsupport.protocol.types.networkentity.metadata.objects;

import java.util.UUID;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalUUID extends NetworkEntityMetadataObject<UUID> {

	public NetworkEntityMetadataObjectOptionalUUID() {
	}

	public NetworkEntityMetadataObjectOptionalUUID(UUID uuid) {
		this.value = uuid;
	}

}
