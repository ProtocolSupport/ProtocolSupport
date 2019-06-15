package protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.InsentientAttackingToLegacySwingingHadsIndexValesRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ZombieEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final ZombieEntityMetadataRemapper INSTANCE = new ZombieEntityMetadataRemapper();

	public ZombieEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zombie.BABY, 14), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zombie.BABY, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new InsentientAttackingToLegacySwingingHadsIndexValesRemapper(14), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new InsentientAttackingToLegacySwingingHadsIndexValesRemapper(15), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new InsentientAttackingToLegacySwingingHadsIndexValesRemapper(14), ProtocolVersionsHelper.ALL_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zombie.DROWNING, 16), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Zombie.DROWNING, 15), ProtocolVersionsHelper.ALL_1_13);
	}

}
