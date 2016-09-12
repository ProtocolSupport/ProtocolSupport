package protocolsupport.protocol.utils.datawatcher.objects;

import java.util.UUID;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
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
	public void readFromStream(ProtocolSupportPacketDataSerializer serializer) {
		if (serializer.readBoolean()) {
			value = serializer.readUUID();
		}
	}

	@Override
	public void writeToStream(ProtocolSupportPacketDataSerializer serializer) {
		serializer.writeBoolean(value != null);
		if (value != null) {
			serializer.writeUUID(value);
		}
	}

}
