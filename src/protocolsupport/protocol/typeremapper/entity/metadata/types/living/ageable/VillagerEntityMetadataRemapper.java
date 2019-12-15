package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperVillagerDataToVarInt;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AbstractMerchantEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class VillagerEntityMetadataRemapper extends AbstractMerchantEntityMetadataRemapper {

	public VillagerEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Villager.VDATA, 17), ProtocolVersionsHelper.UP_1_15);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Villager.VDATA, 16), ProtocolVersionsHelper.RANGE__1_14_1__1_14_4);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Villager.VDATA, 15), ProtocolVersion.MINECRAFT_1_14);
		addRemap(new IndexValueRemapperVillagerDataToVarInt(NetworkEntityMetadataObjectIndex.Villager.VDATA, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperVillagerDataToVarInt(NetworkEntityMetadataObjectIndex.Villager.VDATA, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectVillagerData>(NetworkEntityMetadataObjectIndex.Villager.VDATA, 16) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVillagerData object) {
				return new NetworkEntityMetadataObjectInt(object.getValue().getProfession());
			}
		}, ProtocolVersionsHelper.BEFORE_1_9);
	}

}
