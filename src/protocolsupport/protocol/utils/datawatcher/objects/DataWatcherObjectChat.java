package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
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
	public void readFromStream(ProtocolSupportPacketDataSerializer serializer) {
		value = serializer.readString();
	}

	@Override
	public void writeToStream(ProtocolSupportPacketDataSerializer serializer) {
		serializer.writeString(value);
	}

}
