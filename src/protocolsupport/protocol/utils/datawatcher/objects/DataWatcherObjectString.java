package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectString extends DataWatcherObject<String> {

	public DataWatcherObjectString() {
	}

	public DataWatcherObjectString(String string) {
		value = string;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = StringSerializer.readString(from, version);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		StringSerializer.writeString(to, version, value);
	}

}
