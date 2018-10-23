package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class SlimeEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final SlimeEntityMetadataRemapper INSTANCE = new SlimeEntityMetadataRemapper();

	public SlimeEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Slime.SIZE, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Slime.SIZE, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Slime.SIZE, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
