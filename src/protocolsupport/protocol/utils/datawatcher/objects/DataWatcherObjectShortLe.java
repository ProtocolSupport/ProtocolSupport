package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class DataWatcherObjectShortLe extends DataWatcherObjectNumber<Short> {

	public DataWatcherObjectShortLe() {
	}

	public DataWatcherObjectShortLe(short s) {
		value = s;
	}
	
	public DataWatcherObjectShortLe(int i) {
		value = (short) i;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = from.readShortLE();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		to.writeShortLE(value); 
	}

}