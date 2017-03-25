package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectBoolean extends DataWatcherObject<Boolean> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = from.readBoolean();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		to.writeBoolean(value);
	}

}
