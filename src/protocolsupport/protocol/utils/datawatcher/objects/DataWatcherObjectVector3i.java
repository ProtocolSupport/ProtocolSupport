package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.Position;

public class DataWatcherObjectVector3i extends DataWatcherObject<Position> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isAfter(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version " + version);
		}
		return 6;
	}

	@Override
	public void readFromStream(ProtocolSupportPacketDataSerializer serializer) {
		value = serializer.readLegacyPositionI();
	}

	@Override
	public void writeToStream(ProtocolSupportPacketDataSerializer serializer) {
		serializer.writeLegacyPositionI(value);
	}

}
