package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectShort extends DataWatcherObject<Short> {

	public DataWatcherObjectShort(short s) {
		value = s;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeShort(value);
	}

}
