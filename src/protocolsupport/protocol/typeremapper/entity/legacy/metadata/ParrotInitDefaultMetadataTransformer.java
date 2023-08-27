package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class ParrotInitDefaultMetadataTransformer extends EntityMetadataTransformer<NetworkEntityMetadataObjectIndexRegistry.ParrotIndexRegistry> {

	public static final ParrotInitDefaultMetadataTransformer INSTANCE = new ParrotInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.ParrotIndexRegistry.INSTANCE);

	protected ParrotInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.ParrotIndexRegistry registry) {
		super(registry);
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		registry.VARIANT.setObject(t, new NetworkEntityMetadataObjectVarInt(0));
	}

}