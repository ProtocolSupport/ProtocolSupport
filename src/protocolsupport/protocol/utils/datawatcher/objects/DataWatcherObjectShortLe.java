package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectShortLe extends DataWatcherObject<Short> {

	public DataWatcherObjectShortLe() {
	}

	public DataWatcherObjectShortLe(short s) {
		value = s;
	}

	public DataWatcherObjectShortLe(int i) {
		value = (short) i;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeShortLE(value);
	}

}