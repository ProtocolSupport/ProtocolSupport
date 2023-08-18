package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.particle.NetworkParticle;

public class NetworkEntityMetadataObjectParticle extends NetworkEntityMetadataObject<NetworkParticle> {

	public NetworkEntityMetadataObjectParticle(NetworkParticle particle) {
		this.value = particle;
	}

}
