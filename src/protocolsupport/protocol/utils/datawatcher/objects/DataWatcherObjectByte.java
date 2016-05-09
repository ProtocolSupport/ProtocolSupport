package protocolsupport.protocol.utils.datawatcher.objects;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectByte extends DataWatcherObject<Byte> {

	public DataWatcherObjectByte() {
	}

	public DataWatcherObjectByte(byte b) {
		this.value = b;
	}

	@Override
	public int getTypeId(ProtocolVersion version) {
		return 0;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		value = serializer.readByte();
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeByte(value);
	}

}
