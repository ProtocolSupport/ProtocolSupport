package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectLong extends DataWatcherObject<Long>{
	
	public DataWatcherObjectLong(){
	}
	
	public DataWatcherObjectLong(Long Value) {
		this.value = Value;
	}

	public DataWatcherObjectLong(int Value) {
		this.value = (long) Value;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = VarNumberSerializer.readVarLong(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		VarNumberSerializer.writeSVarLong(to, value);
	}
}
