package protocolsupport.protocol.utils.datawatcher.objects;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;

public class DataWatcherObjectOptionalUUID extends ReadableDataWatcherObject<UUID> {

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version, String locale) {
		if (from.readBoolean()) {
			value = MiscSerializer.readUUID(from);
		}
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		to.writeBoolean(value != null);
		if (value != null) {
			MiscSerializer.writeUUID(to, version, value);
		}
	}

}
