package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectChat extends DataWatcherObject<String> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 4;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		value = StringSerializer.readString(from, version);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		StringSerializer.writeString(to, version, value);
	}

}
