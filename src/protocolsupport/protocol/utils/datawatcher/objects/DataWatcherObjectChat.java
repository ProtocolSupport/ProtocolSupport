package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PacketDataSerializer;
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
	public void readFromStream(PacketDataSerializer serializer) {
		value = serializer.readString(Short.MAX_VALUE);
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeString(value);
	}

}
