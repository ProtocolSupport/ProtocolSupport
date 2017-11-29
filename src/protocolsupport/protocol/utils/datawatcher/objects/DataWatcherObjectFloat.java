package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class DataWatcherObjectFloat extends ReadableDataWatcherObjectNumber<Float> {

	public DataWatcherObjectFloat() {
	}

	public DataWatcherObjectFloat(float value) {
		this.value = value;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = from.readFloat();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloat(value);
	}

}
