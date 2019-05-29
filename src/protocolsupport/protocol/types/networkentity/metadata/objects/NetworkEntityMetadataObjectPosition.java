package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectPosition extends ReadableNetworkEntityMetadataObject<Position> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = PositionSerializer.readPosition(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		writePositionL(to, version, value);
	}

	protected static void writePositionL(ByteBuf to, ProtocolVersion version, Position position) {
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_14)) {
			PositionSerializer.writePosition(to, position);
		} else {
			PositionSerializer.writeLegacyPositionL(to, position);
		}
	}

}
