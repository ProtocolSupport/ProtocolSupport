package protocolsupport.protocol.types.networkentity.metadata.objects;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalUUID extends NetworkEntityMetadataObject<UUID> {

	public NetworkEntityMetadataObjectOptionalUUID() {
	}

	public NetworkEntityMetadataObjectOptionalUUID(UUID uuid) {
		this.value = uuid;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeBoolean(value != null);
		if (value != null) {
			UUIDCodec.writeUUID2L(to, value);
		}
	}

}
