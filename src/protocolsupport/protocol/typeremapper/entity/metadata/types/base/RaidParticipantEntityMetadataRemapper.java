package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class RaidParticipantEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public RaidParticipantEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.RaidParticipant.CELEBRATING, 14), ProtocolVersionsHelper.UP_1_14);
	}

}
