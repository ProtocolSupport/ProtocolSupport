package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectByte extends ReadableNetworkEntityMetadataObject<Byte> {

	public NetworkEntityMetadataObjectByte() {
	}

	public NetworkEntityMetadataObjectByte(byte b) {
		this.value = b;
	}

	@Override
	public void readFromStream(ByteBuf from) {
		value = from.readByte();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeByte(value);
	}

}
