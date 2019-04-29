package protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class TridentEntityMetadataRemapper extends ArrowEntityMetadataRemapper {

	public TridentEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Trident.LOYALTY, 8), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Trident.LOYALTY, 7), ProtocolVersionsHelper.ALL_1_13);
	}

}
