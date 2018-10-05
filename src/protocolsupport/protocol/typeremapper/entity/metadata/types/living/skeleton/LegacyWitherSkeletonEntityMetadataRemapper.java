package protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.FirstDataWatcherUpdateObjectAddRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;

public class LegacyWitherSkeletonEntityMetadataRemapper extends SkeletonEntityMetadataRemapper {

	public LegacyWitherSkeletonEntityMetadataRemapper() {
		addRemap(new FirstDataWatcherUpdateObjectAddRemapper(12, new DataWatcherObjectVarInt(1)), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new FirstDataWatcherUpdateObjectAddRemapper(11, new DataWatcherObjectVarInt(1)), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new FirstDataWatcherUpdateObjectAddRemapper(13, new DataWatcherObjectByte((byte) 1)), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
