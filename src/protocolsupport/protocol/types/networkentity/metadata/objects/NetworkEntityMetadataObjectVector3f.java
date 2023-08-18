package protocolsupport.protocol.types.networkentity.metadata.objects;

import org.bukkit.util.Vector;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVector3f extends NetworkEntityMetadataObject<Vector> {

	public NetworkEntityMetadataObjectVector3f(Vector v) {
		this.value = v;
	}

}
