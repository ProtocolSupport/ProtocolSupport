package protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class TippedArrowEntityMetadataRemapper extends ArrowEntityMetadataRemapper {

	public TippedArrowEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.TippedArrow.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.TippedArrow.COLOR, 6), ProtocolVersionsHelper.ALL_1_9);
	}

}
