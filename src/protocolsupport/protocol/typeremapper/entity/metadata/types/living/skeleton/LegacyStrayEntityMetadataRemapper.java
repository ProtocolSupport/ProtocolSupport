package protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.FirstDataWatcherUpdateObjectAddRemapper;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;

public class LegacyStrayEntityMetadataRemapper extends SkeletonEntityMetadataRemapper {

	public LegacyStrayEntityMetadataRemapper() {
		addRemap(new FirstDataWatcherUpdateObjectAddRemapper(12, new DataWatcherObjectVarInt(2)), ProtocolVersion.MINECRAFT_1_10);
	}

}
