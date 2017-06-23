package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.Position;

public class DataWatcherObjectVector3fVarInt extends DataWatcherObject<Position> {

	public DataWatcherObjectVector3fVarInt() {
	}
	
	public DataWatcherObjectVector3fVarInt(Position position) {
		value = position;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = PositionSerializer.readPEPosition(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		PositionSerializer.writePEPosition(to, value);
	}

}