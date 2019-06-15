package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectBoolean extends ReadableNetworkEntityMetadataObject<Boolean> {

	public NetworkEntityMetadataObjectBoolean() {
	}

	public NetworkEntityMetadataObjectBoolean(boolean value) {
		this.value = value;
	}

	@Override
	public void readFromStream(ByteBuf from) {
		value = from.readBoolean();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeBoolean(value);
	}

}
