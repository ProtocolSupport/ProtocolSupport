package protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.misc.NetworkEntityMetadataFirstUpdateObjectAddRemapper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;

public class LegacyStrayEntityMetadataRemapper extends LegacySkeletonEntityMetadataRemapper {

	public static final LegacyStrayEntityMetadataRemapper INSTANCE = new LegacyStrayEntityMetadataRemapper();

	protected LegacyStrayEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataFirstUpdateObjectAddRemapper(12, new NetworkEntityMetadataObjectVarInt(2)), ProtocolVersion.MINECRAFT_1_10);
	}

}
