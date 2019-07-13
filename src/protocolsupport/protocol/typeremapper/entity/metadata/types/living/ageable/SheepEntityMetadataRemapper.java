package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.PeFlagRemapper;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;

public class SheepEntityMetadataRemapper extends AgeableEntityMetadataRemapper {

	public SheepEntityMetadataRemapper() {
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectByte>(NetworkEntityMetadataObjectIndex.Sheep.FLAGS, PeMetaBase.COLOR) {
			@Override
			public NetworkEntityMetadataObjectByte remapValue(NetworkEntityMetadataObjectByte object) {
				return new NetworkEntityMetadataObjectByte((byte) (object.getValue() & 0x0F));
			}
		}, ProtocolVersionsHelper.ALL_PE);
		addRemap(new PeFlagRemapper(NetworkEntityMetadataObjectIndex.Sheep.FLAGS,
			new int[] {5}, new int[] {PeMetaBase.FLAG_SHEARED}),
		ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Sheep.FLAGS, 15), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Sheep.FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Sheep.FLAGS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Sheep.FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
