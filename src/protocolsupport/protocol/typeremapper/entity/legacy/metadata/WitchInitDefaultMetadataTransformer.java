package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import java.util.function.Consumer;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class WitchInitDefaultMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

	public static final WitchInitDefaultMetadataTransformer INSTANCE = new WitchInitDefaultMetadataTransformer();

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		NetworkEntityMetadataObjectIndex.Witch.DRINKING_POTION.setObject(t, new NetworkEntityMetadataObjectBoolean(false));
	}

}