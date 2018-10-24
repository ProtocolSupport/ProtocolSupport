package protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class SkeletonEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final SkeletonEntityMetadataRemapper INSTANCE = new SkeletonEntityMetadataRemapper();

	public SkeletonEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Skeleton.SWINGING_HANDS, 12), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Skeleton.SWINGING_HANDS, 13), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Skeleton.SWINGING_HANDS, 12), ProtocolVersionsHelper.ALL_1_9);
	}

}
