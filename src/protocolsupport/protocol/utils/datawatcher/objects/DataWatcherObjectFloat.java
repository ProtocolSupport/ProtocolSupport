package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectFloat extends ReadableDataWatcherObject<Float> {

	public DataWatcherObjectFloat() {
	}

	public DataWatcherObjectFloat(float value) {
		this.value = value;
	}

	@Override
	public void readFromStream(ByteBuf from) {
		value = from.readFloat();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloat(value);
	}

}
