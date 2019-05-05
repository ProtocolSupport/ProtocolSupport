package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class SlimeEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final SlimeEntityMetadataRemapper INSTANCE = new SlimeEntityMetadataRemapper();

	public SlimeEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Slime.SIZE, 14), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Slime.SIZE, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Slime.SIZE, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToByte(NetworkEntityMetadataObjectIndex.Slime.SIZE, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
