package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectFloatLe extends NetworkEntityMetadataObject<Float> {

	public NetworkEntityMetadataObjectFloatLe() {
	}

	public NetworkEntityMetadataObjectFloatLe(float value) {
		this.value = value;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloatLE(value);
	}

}
