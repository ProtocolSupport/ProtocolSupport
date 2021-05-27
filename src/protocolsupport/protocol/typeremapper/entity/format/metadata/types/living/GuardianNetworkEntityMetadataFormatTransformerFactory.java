package protocolsupport.protocol.typeremapper.entity.format.metadata.types.living;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToIntTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class GuardianNetworkEntityMetadataFormatTransformerFactory extends InsentientNetworkEntityMetadataFormatTransformerFactory {

	public static final GuardianNetworkEntityMetadataFormatTransformerFactory INSTANCE = new GuardianNetworkEntityMetadataFormatTransformerFactory();

	protected GuardianNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 15), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 14), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 12), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 12) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_10);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 11) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectBoolean>(NetworkEntityMetadataObjectIndex.Guardian.SPIKES, 16) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectBoolean object) {
				return new NetworkEntityMetadataObjectInt(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Guardian.TARGET_ID, 16), ProtocolVersionsHelper.UP_1_15);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Guardian.TARGET_ID, 15), ProtocolVersionsHelper.ALL_1_14);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Guardian.TARGET_ID, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Guardian.TARGET_ID, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToIntTransformer(NetworkEntityMetadataObjectIndex.Guardian.TARGET_ID, 17), ProtocolVersion.MINECRAFT_1_8);
	}

}
