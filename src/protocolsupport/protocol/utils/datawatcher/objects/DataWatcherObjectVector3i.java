package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.Position;

public class DataWatcherObjectVector3i extends DataWatcherObject<Position> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = PositionSerializer.readLegacyPositionI(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		PositionSerializer.writeLegacyPositionI(to, value);
	}

}
