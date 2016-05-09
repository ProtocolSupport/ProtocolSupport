package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectShort extends DataWatcherObject<Short> {

	public DataWatcherObjectShort() {
	}

	public DataWatcherObjectShort(short s) {
		value = s;
	}

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isAfter(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 1;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		value = serializer.readShort();
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeShort(value);
	}

}
