package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.Vector4f;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVector4f extends NetworkEntityMetadataObject<Vector4f> {

	public NetworkEntityMetadataObjectVector4f(Vector4f v) {
		this.value = v;
	}

}
