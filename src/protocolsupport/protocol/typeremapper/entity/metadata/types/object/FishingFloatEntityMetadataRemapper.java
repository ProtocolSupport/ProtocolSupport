package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class FishingFloatEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public FishingFloatEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.FishingFloat.HOOKED_ENTITY, PeMetaBase.OWNER), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.FishingFloat.HOOKED_ENTITY, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.FishingFloat.HOOKED_ENTITY, 5), ProtocolVersionsHelper.ALL_1_9);
	}

}
