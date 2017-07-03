package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class DataWatcherObjectShort extends DataWatcherObjectNumber<Short> {

	public DataWatcherObjectShort() {
	}

	public DataWatcherObjectShort(short s) {
		value = s;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = from.readShort();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeShort(value);
	}

}
