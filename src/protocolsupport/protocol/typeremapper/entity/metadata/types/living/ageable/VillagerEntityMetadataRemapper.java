package protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperVillagerDataToVarInt;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AbstractMerchantEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class VillagerEntityMetadataRemapper extends AbstractMerchantEntityMetadataRemapper {

	public static final VillagerEntityMetadataRemapper INSTANCE = new VillagerEntityMetadataRemapper();

	protected VillagerEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Villager.VDATA, 17), ProtocolVersionsHelper.UP_1_15);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Villager.VDATA, 16), ProtocolVersionsHelper.RANGE__1_14_1__1_14_4);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Villager.VDATA, 15), ProtocolVersion.MINECRAFT_1_14);
		addRemap(new NetworkEntityMetadataIndexValueRemapperVillagerDataToVarInt(NetworkEntityMetadataObjectIndex.Villager.VDATA, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperVillagerDataToVarInt(NetworkEntityMetadataObjectIndex.Villager.VDATA, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObjectVillagerData>(NetworkEntityMetadataObjectIndex.Villager.VDATA, 16) {
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVillagerData object) {
				return new NetworkEntityMetadataObjectInt(object.getValue().getProfession());
			}
		}, ProtocolVersionsHelper.DOWN_1_8);
	}

}
