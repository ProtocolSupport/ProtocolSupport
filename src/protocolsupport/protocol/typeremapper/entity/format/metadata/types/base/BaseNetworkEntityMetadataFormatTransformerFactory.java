package protocolsupport.protocol.typeremapper.entity.format.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToShortTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueOptionalChatToLegacyTextTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.NetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BaseNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.EntityIndexRegistry> extends NetworkEntityMetadataFormatTransformerFactory<R> {

	public static final BaseNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.EntityIndexRegistry> INSTANCE = new BaseNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.EntityIndexRegistry.INSTANCE);

	protected BaseNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.BASE_FLAGS, 0), ProtocolVersionsHelper.ALL_PC);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.AIR, 1), ProtocolVersionsHelper.UP_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToShortTransformer(registry.AIR, 1), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.NAMETAG, 2), ProtocolVersionsHelper.UP_1_13);
		add(new NetworkEntityMetadataObjectIndexValueOptionalChatToLegacyTextTransformer(registry.NAMETAG, 2), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		add(new NetworkEntityMetadataObjectIndexValueOptionalChatToLegacyTextTransformer(registry.NAMETAG, 2), ProtocolVersion.MINECRAFT_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.NAMETAG_VISIBLE, 3), ProtocolVersionsHelper.UP_1_9);
		add(new NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer(registry.NAMETAG_VISIBLE, 3), ProtocolVersion.MINECRAFT_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.SILENT, 4), ProtocolVersionsHelper.UP_1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.NO_GRAVITY, 5), ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_10));

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.POSE, 6), ProtocolVersionsHelper.UP_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.FROZEN_TIME, 7), ProtocolVersionsHelper.UP_1_17);
	}

}
