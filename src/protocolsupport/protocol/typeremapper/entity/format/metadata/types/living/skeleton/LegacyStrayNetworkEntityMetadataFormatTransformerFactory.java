package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.skeleton;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.misc.NetworkEntityMetadataObjectAddOnFirstUpdateTransformer;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;

public class LegacyStrayNetworkEntityMetadataFormatTransformerFactory extends LegacySkeletonNetworkEntityMetadataFormatTransformerFactory {

	public static final LegacyStrayNetworkEntityMetadataFormatTransformerFactory INSTANCE = new LegacyStrayNetworkEntityMetadataFormatTransformerFactory();

	protected LegacyStrayNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(12, new NetworkEntityMetadataObjectVarInt(2)), ProtocolVersion.MINECRAFT_1_10);
	}

}
