package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectOptionalPosition extends ReadableNetworkEntityMetadataObject<Position> {

	@Override
	public void readFromStream(ByteBuf from) {
		if (from.readBoolean()) {
			value = PositionSerializer.readPosition(from);
		}
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeBoolean(value != null);
		if (value != null) {
			NetworkEntityMetadataObjectPosition.writePositionL(to, version, value);
		}
	}

}
