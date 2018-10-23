package protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class ZombieEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final ZombieEntityMetadataRemapper INSTANCE = new ZombieEntityMetadataRemapper();

	public ZombieEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.BABY, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.HANDS_UP, 14), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.HANDS_UP, 15), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.HANDS_UP, 14), ProtocolVersionsHelper.ALL_1_9);
	}

}
