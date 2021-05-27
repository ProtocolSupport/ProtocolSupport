package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AgeableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PigNetworkEntityMetadataFormatTransformerFactory extends AgeableNetworkEntityMetadataFormatTransformerFactory {

	public static final PigNetworkEntityMetadataFormatTransformerFactory INSTANCE = new PigNetworkEntityMetadataFormatTransformerFactory();

	protected PigNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Pig.HAS_SADLLE, 16), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Pig.HAS_SADLLE, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Pig.HAS_SADLLE, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Pig.HAS_SADLLE, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer(NetworkEntityMetadataObjectIndex.Pig.HAS_SADLLE, 16), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Pig.BOOST_TIME, 17), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Pig.BOOST_TIME, 16), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Pig.BOOST_TIME, 14), ProtocolVersionsHelper.RANGE__1_11_1__1_13_2);
	}

}
