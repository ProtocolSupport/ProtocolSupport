package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.Position;

public class DataWatcherObjectVector3i extends DataWatcherObject<Position> {

	public DataWatcherObjectVector3i(Position pos) {
		this.value = pos;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		PositionSerializer.writeLegacyPositionI(to, value);
	}

}
