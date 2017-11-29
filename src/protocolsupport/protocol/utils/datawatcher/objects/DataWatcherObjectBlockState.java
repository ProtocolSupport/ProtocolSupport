package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectBlockState extends ReadableDataWatcherObject<Integer> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = VarNumberSerializer.readVarInt(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarInt(to, value);
	}

}
