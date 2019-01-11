package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeSimpleFlagRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class PolarBearEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public PolarBearEntityMetadataRemapper() {
		//TODO: Just like horses, disappears. Perhaps send a unknown entitystatus aswell? Meh.
		addRemap(new PeSimpleFlagRemapper(DataWatcherObjectIndex.PolarBear.STANDING_UP, PeMetaBase.FLAG_REARING), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.PolarBear.STANDING_UP, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
	}

}
