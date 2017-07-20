package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;

public class DataWatcherObjectFloatLe extends DataWatcherObjectNumber<Float> {

	public DataWatcherObjectFloatLe() {
	}
	
	public DataWatcherObjectFloatLe(float value) {
		this.value = value;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = MiscSerializer.readLFloat(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		MiscSerializer.writeLFloat(to, value);
	}

}
