package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;
import protocolsupport.protocol.utils.types.Position;

public class DataWatcherObjectVector3vi extends ReadableDataWatcherObject<Position> {

	public DataWatcherObjectVector3vi() {
		value = new Position(0, 0, 0);
	}

	public DataWatcherObjectVector3vi(Position position) {
		value = position;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		PositionSerializer.readPEPositionTo(from, value);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		PositionSerializer.writePEPosition(to, value);
	}

}