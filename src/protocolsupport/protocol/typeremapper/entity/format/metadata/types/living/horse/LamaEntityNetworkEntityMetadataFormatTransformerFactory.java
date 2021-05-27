package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class LamaEntityNetworkEntityMetadataFormatTransformerFactory extends CargoHorseNetworkEntityMetadataFormatTransformerFactory {

	public static final LamaEntityNetworkEntityMetadataFormatTransformerFactory INSTANCE = new LamaEntityNetworkEntityMetadataFormatTransformerFactory();

	protected LamaEntityNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Lama.STRENGTH, 19), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Lama.STRENGTH, 18), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Lama.STRENGTH, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Lama.CARPET_COLOR, 20), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Lama.CARPET_COLOR, 19), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Lama.CARPET_COLOR, 17), ProtocolVersionsHelper.RANGE__1_11__1_13_2);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Lama.VARIANT, 21), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Lama.VARIANT, 20), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Lama.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
