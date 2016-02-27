package protocolsupport.utils.datawatcher.objects;

import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectOptionalUUID extends DataWatcherObject<UUID> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 11;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		if (serializer.readBoolean()) {
			value = serializer.readUUID();
		}
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeBoolean(value != null);
		if (value != null) {
			serializer.writeUUID(value);
		}
	}

}
