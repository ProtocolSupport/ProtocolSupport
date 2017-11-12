package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.BlockDirection;

public class DataWatcherObjectDirection extends DataWatcherObject<BlockDirection> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = MiscSerializer.readVarIntEnum(from, BlockDirection.CONSTANT_LOOKUP);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		MiscSerializer.writeVarIntEnum(to, value);
	}

}
