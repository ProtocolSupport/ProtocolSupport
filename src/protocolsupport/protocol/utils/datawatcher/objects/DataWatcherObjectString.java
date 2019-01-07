package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectString extends ReadableDataWatcherObject<String> {

	public DataWatcherObjectString() {
	}

	public DataWatcherObjectString(String string) {
		value = string;
	}

	@Override
	public void readFromStream(ByteBuf from) {
		value = StringSerializer.readVarIntUTF8String(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		StringSerializer.writeString(to, version, value);
	}

}
