package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;
import protocolsupport.protocol.utils.types.Position;

public class DataWatcherObjectPosition extends ReadableDataWatcherObject<Position> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = PositionSerializer.readPosition(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		PositionSerializer.writePosition(to, value);
	}

}
