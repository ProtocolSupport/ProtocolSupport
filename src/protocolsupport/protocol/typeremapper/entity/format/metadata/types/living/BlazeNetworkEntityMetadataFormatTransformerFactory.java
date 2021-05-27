package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BlazeNetworkEntityMetadataFormatTransformerFactory extends InsentientNetworkEntityMetadataFormatTransformerFactory {

	public static final BlazeNetworkEntityMetadataFormatTransformerFactory INSTANCE = new BlazeNetworkEntityMetadataFormatTransformerFactory();

	protected BlazeNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Blaze.ON_FIRE, 15), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Blaze.ON_FIRE, 14), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Blaze.ON_FIRE, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Blaze.ON_FIRE, 11), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Blaze.ON_FIRE, 16), ProtocolVersionsHelper.DOWN_1_8);
	}

}
