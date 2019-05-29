package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class RabbitEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public RabbitEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNumberToSVarInt(NetworkEntityMetadataObjectIndex.Rabbit.VARIANT, PeMetaBase.VARIANT), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Rabbit.VARIANT, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Rabbit.VARIANT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Rabbit.VARIANT, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToByte(NetworkEntityMetadataObjectIndex.Rabbit.VARIANT, 18), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
