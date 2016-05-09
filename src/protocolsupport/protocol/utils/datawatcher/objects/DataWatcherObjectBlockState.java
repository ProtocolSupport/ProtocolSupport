package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectBlockState extends DataWatcherObject<Integer> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 12;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		value = serializer.readVarInt();
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeVarInt(value);
	}

}
