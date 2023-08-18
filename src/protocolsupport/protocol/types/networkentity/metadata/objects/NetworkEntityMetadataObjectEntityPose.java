package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.networkentity.data.EntityPose;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectEntityPose extends NetworkEntityMetadataObject<EntityPose> {

	public NetworkEntityMetadataObjectEntityPose(EntityPose pose) {
		this.value = pose;
	}

}
