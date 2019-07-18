package protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.misc.NetworkEntityMetadataFirstUpdateObjectAddRemapper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LegacyWitherSkeletonEntityMetadataRemapper extends LegacySkeletonEntityMetadataRemapper {

	public LegacyWitherSkeletonEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataFirstUpdateObjectAddRemapper(12, new NetworkEntityMetadataObjectVarInt(1)), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new NetworkEntityMetadataFirstUpdateObjectAddRemapper(11, new NetworkEntityMetadataObjectVarInt(1)), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new NetworkEntityMetadataFirstUpdateObjectAddRemapper(13, new NetworkEntityMetadataObjectByte((byte) 1)), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
