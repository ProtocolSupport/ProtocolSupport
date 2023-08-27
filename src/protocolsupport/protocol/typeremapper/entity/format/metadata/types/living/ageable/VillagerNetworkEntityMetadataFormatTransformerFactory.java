package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueVillagerDataToProfessionVarIntTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AbstractMerchantNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.legacy.LegacyVillagerProfession;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class VillagerNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.VillagerIndexRegistry> extends AbstractMerchantNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final VillagerNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.VillagerIndexRegistry> INSTANCE = new VillagerNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.VillagerIndexRegistry.INSTANCE);

	protected VillagerNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VDATA, 18), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VDATA, 17), ProtocolVersionsHelper.RANGE__1_15__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VDATA, 16), ProtocolVersionsHelper.RANGE__1_14_1__1_14_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.VDATA, 15), ProtocolVersion.MINECRAFT_1_14);
		add(new NetworkEntityMetadataObjectIndexValueVillagerDataToProfessionVarIntTransformer(registry.VDATA, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueVillagerDataToProfessionVarIntTransformer(registry.VDATA, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.VDATA, 16) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVillagerData object) {
				return new NetworkEntityMetadataObjectInt(LegacyVillagerProfession.toLegacyId(object.getValue().getProfession()));
			}
		}, ProtocolVersionsHelper.DOWN_1_8);
	}

}
