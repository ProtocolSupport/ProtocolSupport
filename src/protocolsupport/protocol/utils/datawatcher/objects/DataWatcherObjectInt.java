package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectInt extends DataWatcherObject<Integer> {

	public DataWatcherObjectInt() {
	}

	public DataWatcherObjectInt(int i) {
		this.value = i;
	}

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isAfter(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 2;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		value = serializer.readInt();
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeInt(value);
	}

}
