package protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.misc.NetworkEntityMetadataObjectAddOnFirstUpdateTransformer;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory extends LegacySkeletonNetworkEntityMetadataFormatTransformerFactory {

	public static final LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory INSTANCE = new LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory();

	protected LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory() {
		//legacy skeleton type
		addTransformer(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(12, new NetworkEntityMetadataObjectVarInt(1)), ProtocolVersion.MINECRAFT_1_10);
		addTransformer(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(11, new NetworkEntityMetadataObjectVarInt(1)), ProtocolVersionsHelper.ALL_1_9);
		addTransformer(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(13, new NetworkEntityMetadataObjectByte((byte) 1)), ProtocolVersionsHelper.DOWN_1_8);
	}

}
