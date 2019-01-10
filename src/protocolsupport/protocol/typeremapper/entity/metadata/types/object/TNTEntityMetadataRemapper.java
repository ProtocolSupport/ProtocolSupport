package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeSimpleFlagAdder;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class TNTEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public TNTEntityMetadataRemapper() {
		addRemap(new PeSimpleFlagAdder(
				new int[] {PeMetaBase.FLAG_IGNITED}, new boolean[] {true}
		), ProtocolVersionsHelper.ALL_PE);
		addRemap(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Tnt.FUSE, PeMetaBase.FUSE_LENGTH), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tnt.FUSE, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tnt.FUSE, 5), ProtocolVersionsHelper.ALL_1_9);
	}

}
