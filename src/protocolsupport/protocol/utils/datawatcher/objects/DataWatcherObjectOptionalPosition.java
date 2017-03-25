package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.Position;

public class DataWatcherObjectOptionalPosition extends DataWatcherObject<Position> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		if (from.readBoolean()) {
			value = PositionSerializer.readPosition(from);
		}
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		to.writeBoolean(value != null);
		if (value != null) {
			PositionSerializer.writePosition(to, value);
		}
	}

}
