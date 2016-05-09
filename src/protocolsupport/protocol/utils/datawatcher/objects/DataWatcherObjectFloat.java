package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectFloat extends DataWatcherObject<Float> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		return version.isAfter(ProtocolVersion.MINECRAFT_1_8) ? 2 : 3;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		value = serializer.readFloat();
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeFloat(value);
	}

}
