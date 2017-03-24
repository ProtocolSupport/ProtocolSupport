package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectShort extends DataWatcherObject<Short> {

	public DataWatcherObjectShort() {
	}

	public DataWatcherObjectShort(short s) {
		value = s;
	}

	@Override
	public int getTypeId(ProtocolVersion version) {
		if ((!version.equals(ProtocolVersion.MINECRAFT_PE)) && version.isAfter(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 1;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = from.readShort();
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		if(version.equals(ProtocolVersion.MINECRAFT_PE)){VarNumberSerializer.writeSVarInt(to, value);}
		else {to.writeShort(value);}
	}

}
