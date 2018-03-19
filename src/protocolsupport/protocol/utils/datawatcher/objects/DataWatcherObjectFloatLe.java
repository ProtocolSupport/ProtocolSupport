package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectFloatLe extends DataWatcherObject<Float> {

	public DataWatcherObjectFloatLe() {
	}

	public DataWatcherObjectFloatLe(float value) {
		this.value = value;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeFloatLE(value);
	}

}
