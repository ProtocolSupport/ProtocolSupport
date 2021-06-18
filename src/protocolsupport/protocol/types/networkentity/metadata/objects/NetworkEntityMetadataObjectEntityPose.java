package protocolsupport.protocol.types.networkentity.metadata.objects;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
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
			MiscDataCodec.writeVarIntEnum(to, value);
		} else {
			if (value == EntityPose.LONG_JUMPING) {
				value = EntityPose.FALL_FLYING;
			}
			int ordinalId = value.ordinal();
			if (ordinalId > EntityPose.SNEAKING.ordinal()) {
				ordinalId--;
			}
			VarNumberCodec.writeVarInt(to, ordinalId);
		}
	}

}
