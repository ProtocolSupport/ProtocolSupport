package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ItemFrameNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.ItemFrameIndexRegistry> extends BaseNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final ItemFrameNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.ItemFrameIndexRegistry> INSTANCE = new ItemFrameNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.ItemFrameIndexRegistry.INSTANCE);

	protected ItemFrameNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 8, version), ProtocolVersionsHelper.UP_1_17);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 7, version), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 6, version), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 5, version), ProtocolVersionsHelper.ALL_1_9);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 8, version), ProtocolVersion.MINECRAFT_1_8);
		add(version -> new NetworkEntityMetadataObjectIndexValueItemStackToLegacyFormatTransformer(registry.ITEM, 2, version), ProtocolVersionsHelper.DOWN_1_7_10);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ROTATION, 9), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ROTATION, 8), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ROTATION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.ROTATION, 6), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToByteTransformer(registry.ROTATION, 9), ProtocolVersion.MINECRAFT_1_8);
		add(new NetworkEntityMetadataObjectIndexValueTransformer<>(registry.ROTATION, 3) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectByte((byte) (object.getValue() >> 1));
			}
		}, ProtocolVersionsHelper.DOWN_1_7_10);
	}

}
