package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class DataWatcherObjectByte extends DataWatcherObjectNumber<Byte> {

	public DataWatcherObjectByte() {
	}

	public DataWatcherObjectByte(byte b) {
		this.value = b;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = from.readByte();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeByte(value);
	}

}
