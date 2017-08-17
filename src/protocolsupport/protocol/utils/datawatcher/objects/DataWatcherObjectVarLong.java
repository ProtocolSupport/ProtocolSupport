package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class DataWatcherObjectVarLong extends DataWatcherObjectNumber<Long> {

	public DataWatcherObjectVarLong() {
	}
	
	public DataWatcherObjectVarLong(Long Value) {
		this.value = Value;
	}

	public DataWatcherObjectVarLong(int Value) {
		this.value = (long) Value;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		value = VarNumberSerializer.readVarLong(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarLong(to, value);
	}

}
