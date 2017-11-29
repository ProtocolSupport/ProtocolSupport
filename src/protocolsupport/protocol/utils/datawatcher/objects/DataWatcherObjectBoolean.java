package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectBoolean extends ReadableDataWatcherObject<Boolean> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = from.readBoolean();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeBoolean(value);
	}

}
