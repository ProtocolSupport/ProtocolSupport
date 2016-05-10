package protocolsupport.protocol.utils.datawatcher.objects;

import net.minecraft.server.v1_9_R2.BlockPosition;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectOptionalPosition extends DataWatcherObject<BlockPosition> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 9;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		if (serializer.readBoolean()) {
			value = BlockPosition.fromLong(serializer.readLong());
		}
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.writeBoolean(value != null);
		if (value != null) {
			serializer.writeLong(value.asLong());
		}
	}

}
