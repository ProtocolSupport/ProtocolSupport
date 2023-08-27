package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import protocolsupport.protocol.types.networkentity.data.VillagerData;
import protocolsupport.protocol.types.networkentity.data.VillagerProfession;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class VillagerInitDefaultMetadataTransformer extends EntityMetadataTransformer<NetworkEntityMetadataObjectIndexRegistry.VillagerIndexRegistry> {

	public static final VillagerInitDefaultMetadataTransformer INSTANCE = new VillagerInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.VillagerIndexRegistry.INSTANCE);

	protected VillagerInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.VillagerIndexRegistry registry) {
		super(registry);
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		registry.VDATA.setObject(t, new NetworkEntityMetadataObjectVillagerData(new VillagerData(0, VillagerProfession.NONE, 0)));
	}

}