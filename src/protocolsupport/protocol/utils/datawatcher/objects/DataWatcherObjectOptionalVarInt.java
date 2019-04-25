package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectOptionalVarInt extends ReadableDataWatcherObject<Integer> {

	@Override
	public void readFromStream(ByteBuf from) {
		int i = VarNumberSerializer.readVarInt(from);
		if (i != 0) {
			value = i - 1;
		}
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarInt(to, value != 0 ? value + 1 : 0);
	}

}
