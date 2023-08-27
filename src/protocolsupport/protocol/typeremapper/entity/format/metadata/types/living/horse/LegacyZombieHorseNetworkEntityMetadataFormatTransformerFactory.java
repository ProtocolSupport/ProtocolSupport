package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.misc.NetworkEntityMetadataObjectAddOnFirstUpdateTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LegacyZombieHorseNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.BattleHorseIndexRegistry> extends BattleHorseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final LegacyZombieHorseNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.BattleHorseIndexRegistry> INSTANCE = new LegacyZombieHorseNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.BattleHorseIndexRegistry.INSTANCE);

	protected LegacyZombieHorseNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		//legacy horse type
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(14, new NetworkEntityMetadataObjectVarInt(3)), ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(13, new NetworkEntityMetadataObjectVarInt(3)), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(19, new NetworkEntityMetadataObjectByte((byte) 3)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8));
	}

}
