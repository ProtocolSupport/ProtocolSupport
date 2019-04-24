package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class EvokerEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final EvokerEntityMetadataRemapper INSTANCE = new EvokerEntityMetadataRemapper();

	public EvokerEntityMetadataRemapper() {
		//TODO: check remap index, looks like it changed in 1.13
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Evoker.SPELL, 12), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
