package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.BlockDirection;

public class DataWatcherObjectDirection extends DataWatcherObject<BlockDirection> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = MiscSerializer.readEnum(from, BlockDirection.class);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		MiscSerializer.writeEnum(to, value);
	}

}
