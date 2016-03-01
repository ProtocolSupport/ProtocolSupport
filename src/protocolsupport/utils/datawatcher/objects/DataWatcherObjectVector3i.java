package protocolsupport.utils.datawatcher.objects;

import net.minecraft.server.v1_9_R1.BlockPosition;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectVector3i extends DataWatcherObject<BlockPosition> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isAfter(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 6;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		value = new BlockPosition(serializer.readInt(), serializer.readInt(), serializer.readInt());
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeInt(value.getX());
		serializer.writeInt(value.getY());
		serializer.writeInt(value.getZ());
	}

}
