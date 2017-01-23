package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.Position;

public class DataWatcherObjectOptionalPosition extends DataWatcherObject<Position> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 9;
	}

	@Override
	public void readFromStream(ProtocolSupportPacketDataSerializer serializer) {
		if (serializer.readBoolean()) {
			value = serializer.readPosition();
		}
	}

	@Override
	public void writeToStream(ProtocolSupportPacketDataSerializer serializer) {
		serializer.writeBoolean(value != null);
		if (value != null) {
			serializer.writePosition(value);
		}
	}

}
