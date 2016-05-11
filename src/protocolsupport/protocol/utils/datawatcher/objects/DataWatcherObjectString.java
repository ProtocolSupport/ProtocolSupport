package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectString extends DataWatcherObject<String> {

	public DataWatcherObjectString() {
	}

	public DataWatcherObjectString(String string) {
		value = string;
	}

	@Override
	public int getTypeId(ProtocolVersion version) {
		return version.isAfter(ProtocolVersion.MINECRAFT_1_8) ? 3 : 4;
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
