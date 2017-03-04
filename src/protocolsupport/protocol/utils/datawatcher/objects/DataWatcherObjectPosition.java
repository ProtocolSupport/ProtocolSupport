package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.Position;

public class DataWatcherObjectPosition extends DataWatcherObject<Position> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 8;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = PositionSerializer.readPosition(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		PositionSerializer.writePosition(to, value);
	}

}
