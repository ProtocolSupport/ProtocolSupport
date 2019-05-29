package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeSimpleFlagAdder;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TNTEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public TNTEntityMetadataRemapper() {
		addRemap(new PeSimpleFlagAdder(
				new int[] {PeMetaBase.FLAG_IGNITED}, new boolean[] {true}
		), ProtocolVersionsHelper.ALL_PE);
		addRemap(new IndexValueRemapperNumberToSVarInt(NetworkEntityMetadataObjectIndex.Tnt.FUSE, PeMetaBase.FUSE_LENGTH), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Tnt.FUSE, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Tnt.FUSE, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Tnt.FUSE, 5), ProtocolVersionsHelper.ALL_1_9);
	}

}
