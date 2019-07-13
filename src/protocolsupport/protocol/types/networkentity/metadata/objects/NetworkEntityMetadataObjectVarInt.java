package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVarInt extends ReadableNetworkEntityMetadataObject<Integer> {

	public NetworkEntityMetadataObjectVarInt() {
	}

	public NetworkEntityMetadataObjectVarInt(int value) {
		this.value = value;
	}

	@Override
	public void readFromStream(ByteBuf from) {
		value = VarNumberSerializer.readVarInt(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarInt(to, value);
	}

}
