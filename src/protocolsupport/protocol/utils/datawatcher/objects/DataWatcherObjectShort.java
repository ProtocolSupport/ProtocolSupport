package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectShort extends DataWatcherObject<Short> {

	public DataWatcherObjectShort() {
	}

	public DataWatcherObjectShort(short s) {
		value = s;
	}

	public DataWatcherObjectShort(int i) {
		value = (short) i;
	}
	
	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = from.readShort();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		if(version.equals(ProtocolVersion.MINECRAFT_PE)){MiscSerializer.writeLShort(to, value);}
		else {to.writeShort(value);}
	}

}
