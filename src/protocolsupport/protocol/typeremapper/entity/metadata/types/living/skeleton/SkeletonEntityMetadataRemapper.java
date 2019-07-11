package protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.InsentientAttackingToLegacySwingingHadsIndexValesRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class SkeletonEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final SkeletonEntityMetadataRemapper INSTANCE = new SkeletonEntityMetadataRemapper();

	public SkeletonEntityMetadataRemapper() {
		addRemap(new InsentientAttackingToLegacySwingingHadsIndexValesRemapper(12), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new InsentientAttackingToLegacySwingingHadsIndexValesRemapper(13), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new InsentientAttackingToLegacySwingingHadsIndexValesRemapper(12), ProtocolVersionsHelper.ALL_1_9);
	}



}
