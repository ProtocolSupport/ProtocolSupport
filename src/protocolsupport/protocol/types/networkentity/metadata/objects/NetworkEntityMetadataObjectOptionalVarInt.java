package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalVarInt extends ReadableNetworkEntityMetadataObject<Integer> {

	@Override
	public void readFromStream(ByteBuf from) {
		int i = VarNumberSerializer.readVarInt(from);
		if (i != 0) {
			value = i - 1;
		}
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarInt(to, value != null ? value + 1 : 0);
	}

}
