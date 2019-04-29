package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class RaidParticipantEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public RaidParticipantEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.RaidParticipant.CELEBRATING, 14), ProtocolVersionsHelper.UP_1_14);
	}

}
