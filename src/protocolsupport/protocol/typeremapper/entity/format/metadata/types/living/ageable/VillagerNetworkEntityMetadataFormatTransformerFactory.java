package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueVillagerDataToProfessionVarIntTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AbstractMerchantNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.legacy.LegacyVillagerProfession;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class VillagerNetworkEntityMetadataFormatTransformerFactory extends AbstractMerchantNetworkEntityMetadataFormatTransformerFactory {

	public static final VillagerNetworkEntityMetadataFormatTransformerFactory INSTANCE = new VillagerNetworkEntityMetadataFormatTransformerFactory();

	protected VillagerNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Villager.VDATA, 17), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Villager.VDATA, 16), ProtocolVersionsHelper.RANGE__1_14_1__1_14_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Villager.VDATA, 15), ProtocolVersion.MINECRAFT_1_14);
		add(new NetworkEntityMetadataObjectIndexValueVillagerDataToProfessionVarIntTransformer(NetworkEntityMetadataObjectIndex.Villager.VDATA, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueVillagerDataToProfessionVarIntTransformer(NetworkEntityMetadataObjectIndex.Villager.VDATA, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectVillagerData>(NetworkEntityMetadataObjectIndex.Villager.VDATA, 16) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVillagerData object) {
				return new NetworkEntityMetadataObjectInt(LegacyVillagerProfession.toLegacyId(object.getValue().getProfession()));
			}
		}, ProtocolVersionsHelper.DOWN_1_8);
	}

}
