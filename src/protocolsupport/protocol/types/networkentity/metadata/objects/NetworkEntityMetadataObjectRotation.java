package protocolsupport.protocol.types.networkentity.metadata.objects;

import org.bukkit.util.Vector;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectRotation extends NetworkEntityMetadataObject<Vector> {

	public NetworkEntityMetadataObjectRotation(Vector v) {
		this.value = v;
	}

}
