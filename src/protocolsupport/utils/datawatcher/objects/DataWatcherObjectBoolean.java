package protocolsupport.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectBoolean extends DataWatcherObject<Boolean> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 6;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		value = serializer.readBoolean();
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeBoolean(value);
	}

}
