package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectVarInt extends DataWatcherObject<Integer> {

	public DataWatcherObjectVarInt(){
	}
	
	public DataWatcherObjectVarInt(int Value) {
		this.value = Value;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = VarNumberSerializer.readVarInt(from);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		if(version.equals(ProtocolVersion.MINECRAFT_PE)){VarNumberSerializer.writeSVarInt(to, value);}
		else {VarNumberSerializer.writeVarInt(to, value);}
	}

}
