package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.RaidParticipantEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class EvokerEntityMetadataRemapper extends RaidParticipantEntityMetadataRemapper {

	public static final EvokerEntityMetadataRemapper INSTANCE = new EvokerEntityMetadataRemapper();

	public EvokerEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Evoker.SPELL, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Evoker.SPELL, 13), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
