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
	public int getTypeId(ProtocolVersion version) {
		if(version.equals(ProtocolVersion.MINECRAFT_PE)){return 7;}
		else{return 1;} //It is technically PE only though.
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		if(version.equals(ProtocolVersion.MINECRAFT_PE)){value = VarNumberSerializer.readVarLong(from);}
		else {value = (long) from.readInt();}
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		if(version.equals(ProtocolVersion.MINECRAFT_PE)){VarNumberSerializer.writeSVarLong(to, value);}
		else {to.writeInt(Math.toIntExact(value));}
	}
}
