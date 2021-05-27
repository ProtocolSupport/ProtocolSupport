package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BatEntityNetworkEntityMetadataFormatTransformerFactory extends InsentientNetworkEntityMetadataFormatTransformerFactory {

	public static final BatEntityNetworkEntityMetadataFormatTransformerFactory INSTANCE = new BatEntityNetworkEntityMetadataFormatTransformerFactory();

	protected BatEntityNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Bat.HANGING, 15), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Bat.HANGING, 14), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Bat.HANGING, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Bat.HANGING, 11), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Bat.HANGING, 16), ProtocolVersionsHelper.DOWN_1_8);
	}

}
