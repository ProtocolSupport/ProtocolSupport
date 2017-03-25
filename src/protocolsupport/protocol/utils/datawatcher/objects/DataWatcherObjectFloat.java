package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectFloat extends DataWatcherObject<Float> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = from.readFloat();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		to.writeFloat(value);
	}

}
