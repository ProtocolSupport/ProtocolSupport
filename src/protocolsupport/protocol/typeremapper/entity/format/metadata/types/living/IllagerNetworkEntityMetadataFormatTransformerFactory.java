package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.RaidParticipantNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.BitUtils;

public class IllagerNetworkEntityMetadataFormatTransformerFactory extends RaidParticipantNetworkEntityMetadataFormatTransformerFactory {

	public static final IllagerNetworkEntityMetadataFormatTransformerFactory INSTANCE = new IllagerNetworkEntityMetadataFormatTransformerFactory();

	protected IllagerNetworkEntityMetadataFormatTransformerFactory() {
		add(
			new NetworkEntityMetadataObjectIndexValueTransformer<>(NetworkEntityMetadataObjectIndex.Insentient.INS_FLAGS, 12) {
				@Override
				public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectByte object) {
					return new NetworkEntityMetadataObjectByte((byte) (BitUtils.isIBitSet(object.getValue(), NetworkEntityMetadataObjectIndex.Insentient.INS_FLAGS_BIT_ATTACKING) ? 1 : 0));
				}
			},
			ProtocolVersionsHelper.RANGE__1_11__1_13_2
		);
	}

}
