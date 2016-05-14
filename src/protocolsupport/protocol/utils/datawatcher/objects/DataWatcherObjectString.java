package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
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
	public void readFromStream(ProtocolSupportPacketDataSerializer serializer) {
		value = serializer.readString();
	}

	@Override
	public void writeToStream(ProtocolSupportPacketDataSerializer serializer) {
		serializer.writeString(value);
	}

}
