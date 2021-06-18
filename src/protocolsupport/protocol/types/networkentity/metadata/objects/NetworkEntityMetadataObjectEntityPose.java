package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.EntityPose;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectEntityPose extends NetworkEntityMetadataObject<EntityPose> {

	public NetworkEntityMetadataObjectEntityPose(EntityPose pose) {
		this.value = pose;
	}

	@Override
	public void writeToStream(ByteBuf to, ProtocolVersion version, String locale) {
		//TODO: rework it somehow, this shouldn't be there
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_17)) {
			MiscSerializer.writeVarIntEnum(to, value);
		} else {
			if (value == EntityPose.LONG_JUMPING) {
				value = EntityPose.FALL_FLYING;
			}
			int ordinalId = value.ordinal();
			if (ordinalId > EntityPose.SNEAKING.ordinal()) {
				ordinalId--;
			}
			VarNumberSerializer.writeVarInt(to, ordinalId);
		}
	}

}
