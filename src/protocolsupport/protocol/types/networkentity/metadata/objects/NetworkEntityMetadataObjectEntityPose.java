package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.types.EntityPose;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectEntityPose extends ReadableNetworkEntityMetadataObject<EntityPose> {

	@Override
	public void readFromStream(ByteBuf from) {
		value = MiscSerializer.readVarIntEnum(from, EntityPose.CONSTANT_LOOKUP);
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		MiscSerializer.writeVarIntEnum(to, value);
	}

}
