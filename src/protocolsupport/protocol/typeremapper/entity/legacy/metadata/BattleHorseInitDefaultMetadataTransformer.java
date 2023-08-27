package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class BattleHorseInitDefaultMetadataTransformer extends EntityMetadataTransformer<NetworkEntityMetadataObjectIndexRegistry.BattleHorseIndexRegistry> {

	public static final BattleHorseInitDefaultMetadataTransformer INSTANCE = new BattleHorseInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.BattleHorseIndexRegistry.INSTANCE);

	protected BattleHorseInitDefaultMetadataTransformer(NetworkEntityMetadataObjectIndexRegistry.BattleHorseIndexRegistry registry) {
		super(registry);
	}

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		registry.VARIANT.setObject(t, new NetworkEntityMetadataObjectVarInt(0));
	}

}