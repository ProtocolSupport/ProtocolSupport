package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVector3vi extends NetworkEntityMetadataObject<Position> {

	public NetworkEntityMetadataObjectVector3vi() {
		value = new Position(0, 0, 0);
	}

	public NetworkEntityMetadataObjectVector3vi(Position position) {
		value = position;
	}

	public void readFromStream(ByteBuf from) {
		PositionSerializer.readPEPositionTo(from, value);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		PositionSerializer.writePEPosition(to, value);
	}

}