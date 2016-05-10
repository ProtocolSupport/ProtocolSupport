package protocolsupport.protocol.utils.datawatcher.objects;

import net.minecraft.server.v1_9_R2.EnumDirection;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class DataWatcherObjectDirection extends DataWatcherObject<EnumDirection> {

	@Override
	public int getTypeId(ProtocolVersion version) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_8)) {
			throw new IllegalStateException("No type id exists for protocol version "+version);
		}
		return 10;
	}

	@Override
	public void readFromStream(PacketDataSerializer serializer) {
		value = serializer.a(EnumDirection.class);
	}

	@Override
	public void writeToStream(PacketDataSerializer serializer) {
		serializer.a(value);
	}

}
