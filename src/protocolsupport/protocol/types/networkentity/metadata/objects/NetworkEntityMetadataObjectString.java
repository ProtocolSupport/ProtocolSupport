package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectString extends ReadableNetworkEntityMetadataObject<String> {

	public NetworkEntityMetadataObjectString() {
	}

	public NetworkEntityMetadataObjectString(String string) {
		value = string;
	}

	@Override
	public void readFromStream(ByteBuf from) {
		value = StringSerializer.readVarIntUTF8String(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		StringSerializer.writeString(to, version, value);
	}

}
