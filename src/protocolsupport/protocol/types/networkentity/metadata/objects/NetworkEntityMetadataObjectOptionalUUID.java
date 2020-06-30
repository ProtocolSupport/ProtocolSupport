package protocolsupport.protocol.types.networkentity.metadata.objects;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalUUID extends ReadableNetworkEntityMetadataObject<UUID> {

	@Override
	public void readFromStream(ByteBuf from) {
		if (from.readBoolean()) {
			value = UUIDSerializer.readUUID2L(from);
		}
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeBoolean(value != null);
		if (value != null) {
			UUIDSerializer.writeUUID2L(to, value);
		}
	}

}
