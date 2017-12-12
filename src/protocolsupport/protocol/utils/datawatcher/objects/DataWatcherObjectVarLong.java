package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class DataWatcherObjectVarLong extends ReadableDataWatcherObjectNumber<Integer> {

	public DataWatcherObjectVarLong() {
	}

	public DataWatcherObjectVarLong(Integer Value) {
		this.value = Value;
	}

	public DataWatcherObjectVarLong(int Value) {
		this.value = Value;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = (int) VarNumberSerializer.readVarLong(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarLong(to, value);
	}

}
