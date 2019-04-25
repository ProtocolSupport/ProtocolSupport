package protocolsupport.protocol.utils.datawatcher.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;
import protocolsupport.protocol.utils.types.EntityPose;

public class DataWatcherObjectEntityPose extends ReadableDataWatcherObject<EntityPose> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = MiscSerializer.readVarIntEnum(from, EntityPose.CONSTANT_LOOKUP);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		MiscSerializer.writeVarIntEnum(to, value);
	}

}
