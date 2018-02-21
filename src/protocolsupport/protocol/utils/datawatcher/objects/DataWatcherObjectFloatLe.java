package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class DataWatcherObjectFloatLe extends ReadableDataWatcherObjectNumber<Float> {

	public DataWatcherObjectFloatLe() {
	}

	public DataWatcherObjectFloatLe(float value) {
		this.value = value;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = from.readFloatLE();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloatLE(value);
	}

}
