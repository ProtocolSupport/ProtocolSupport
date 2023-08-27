package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.skeleton;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.misc.NetworkEntityMetadataObjectAddOnFirstUpdateTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.InsentientIndexRegistry> extends LegacySkeletonNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.InsentientIndexRegistry> INSTANCE = new LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.InsentientIndexRegistry.INSTANCE);

	protected LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		//legacy skeleton type
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(12, new NetworkEntityMetadataObjectVarInt(1)), ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(11, new NetworkEntityMetadataObjectVarInt(1)), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(13, new NetworkEntityMetadataObjectByte((byte) 1)), ProtocolVersionsHelper.DOWN_1_8);
	}

}
