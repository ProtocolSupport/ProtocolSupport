package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectVarLong extends DataWatcherObject<Integer> {

	public DataWatcherObjectVarLong() {
	}

	public DataWatcherObjectVarLong(Integer Value) {
		this.value = Value;
	}

	public DataWatcherObjectVarLong(int Value) {
		this.value = Value;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		VarNumberSerializer.writeVarLong(to, value);
	}

}
