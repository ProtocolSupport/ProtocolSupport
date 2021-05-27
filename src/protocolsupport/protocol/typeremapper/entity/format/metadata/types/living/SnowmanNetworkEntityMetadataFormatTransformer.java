package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class SnowmanNetworkEntityMetadataFormatTransformer extends InsentientNetworkEntityMetadataFormatTransformerFactory {

	public static final SnowmanNetworkEntityMetadataFormatTransformer INSTANCE = new SnowmanNetworkEntityMetadataFormatTransformer();

	protected SnowmanNetworkEntityMetadataFormatTransformer() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Snowman.NO_HAT, 15), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Snowman.NO_HAT, 14), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Snowman.NO_HAT, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Snowman.NO_HAT, 11), ProtocolVersionsHelper.ALL_1_9);
	}

}
