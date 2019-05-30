package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataShortLe extends NetworkEntityMetadataObject<Short> {

	public NetworkEntityMetadataShortLe() {
	}

	public NetworkEntityMetadataShortLe(short s) {
		value = s;
	}

	public NetworkEntityMetadataShortLe(int i) {
		value = (short) i;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeShortLE(value);
	}

}