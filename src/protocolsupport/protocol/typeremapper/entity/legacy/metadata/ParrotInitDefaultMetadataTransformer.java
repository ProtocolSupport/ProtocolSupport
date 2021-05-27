package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import java.util.function.Consumer;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class ParrotInitDefaultMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

	public static final ParrotInitDefaultMetadataTransformer INSTANCE = new ParrotInitDefaultMetadataTransformer();

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		NetworkEntityMetadataObjectIndex.Parrot.VARIANT.setObject(t, new NetworkEntityMetadataObjectVarInt(0));
	}

}