package protocolsupport.protocol.utils.datawatcher.objects;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectOptionalUUID extends DataWatcherObject<UUID> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 11;
	}

	@Override
	public void readFromStream(ByteBuf from, ProtocolVersion version) {
		if (from.readBoolean()) {
			value = MiscSerializer.readUUID(from);
		}
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version) {
		to.writeBoolean(value != null);
		if (value != null) {
			MiscSerializer.writeUUID(to, value);
		}
	}

}
