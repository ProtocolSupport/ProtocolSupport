package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class FireworkEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public FireworkEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Firework.ITEM, PeMetaBase.FIREWORK_TYPE), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Firework.ITEM, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Firework.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Firework.ITEM, 5), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Firework.ITEM, 8), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Firework.USER, 8), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectOptionalVarInt>(NetworkEntityMetadataObjectIndex.Firework.USER, 7) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectOptionalVarInt object) {
				return new NetworkEntityMetadataObjectVarInt(object.getValue() != null ? object.getValue() : 0);
			}
		}, ProtocolVersionsHelper.RANGE__1_11_1__1_13_2);
	}

}
