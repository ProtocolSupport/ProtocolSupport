package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import java.util.function.Consumer;

import protocolsupport.protocol.types.networkentity.data.VillagerData;
import protocolsupport.protocol.types.networkentity.data.VillagerProfession;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class VillagerInitDefaultMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

	public static final VillagerInitDefaultMetadataTransformer INSTANCE = new VillagerInitDefaultMetadataTransformer();

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		NetworkEntityMetadataObjectIndex.Villager.VDATA.setObject(t, new NetworkEntityMetadataObjectVillagerData(new VillagerData(0, VillagerProfession.NONE, 0)));
	}

}