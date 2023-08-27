package protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.minecart;

import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueChatToStringTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndexRegistry;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class MinecartCommandNetworkEntityMetadataFormatTransformerFactory<R extends NetworkEntityMetadataObjectIndexRegistry.MinecartCommandIndexRegistry> extends MinecartNetworkEntityMetadataFormatTransformerFactory<R> {

	public static final MinecartCommandNetworkEntityMetadataFormatTransformerFactory<NetworkEntityMetadataObjectIndexRegistry.MinecartCommandIndexRegistry> INSTANCE = new MinecartCommandNetworkEntityMetadataFormatTransformerFactory<>(NetworkEntityMetadataObjectIndexRegistry.MinecartCommandIndexRegistry.INSTANCE);

	protected MinecartCommandNetworkEntityMetadataFormatTransformerFactory(R registry) {
		super(registry);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COMMAND, 14), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COMMAND, 13), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COMMAND, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COMMAND, 11), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.COMMAND, 23), ProtocolVersionsHelper.RANGE__1_7_5__1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LAST_OUTPUT, 15), ProtocolVersionsHelper.UP_1_17);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LAST_OUTPUT, 14), ProtocolVersionsHelper.RANGE__1_14__1_16_4);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LAST_OUTPUT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(registry.LAST_OUTPUT, 12), ProtocolVersionsHelper.ALL_1_9);
		add(new NetworkEntityMetadataObjectIndexValueChatToStringTransformer(registry.LAST_OUTPUT, 24, 64), ProtocolVersionsHelper.RANGE__1_7_5__1_8);
	}

}
