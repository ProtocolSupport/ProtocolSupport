package protocolsupport.protocol.utils.datawatcher.objects;

import org.bukkit.util.Vector;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectVector3f extends DataWatcherObject<Vector> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		return 7;
	}

	@Override
	public void readFromStream(ProtocolSupportPacketDataSerializer serializer) {
		value = new Vector(serializer.readFloat(), serializer.readFloat(), serializer.readFloat());
	}

	@Override
	public void writeToStream(ProtocolSupportPacketDataSerializer serializer) {
		serializer.writeFloat((float) value.getX());
		serializer.writeFloat((float) value.getY());
		serializer.writeFloat((float) value.getZ());
	}

}
