package protocolsupport.protocol.utils.datawatcher.objects;

import net.minecraft.server.v1_9_R1.Vector3f;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectVector3f extends DataWatcherObject<Vector3f> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		return 7;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		value = new Vector3f(serializer.readFloat(), serializer.readFloat(), serializer.readFloat());
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeFloat(value.getX());
		serializer.writeFloat(value.getY());
		serializer.writeFloat(value.getZ());
	}

}
