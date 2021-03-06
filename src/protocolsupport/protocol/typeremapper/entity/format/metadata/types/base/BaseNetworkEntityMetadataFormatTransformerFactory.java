package protocolsupport.protocol.typeremapper.entity.format.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueNumberToShortTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.value.NetworkEntityMetadataObjectIndexValueOptionalChatToLegacyTextTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.NetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BaseNetworkEntityMetadataFormatTransformerFactory extends NetworkEntityMetadataFormatTransformerFactory {

	public static final BaseNetworkEntityMetadataFormatTransformerFactory INSTANCE = new BaseNetworkEntityMetadataFormatTransformerFactory();

	protected BaseNetworkEntityMetadataFormatTransformerFactory() {
		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Entity.BASE_FLAGS, 0), ProtocolVersionsHelper.ALL_PC);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.UP_1_9);
		add(new NetworkEntityMetadataObjectIndexValueNumberToShortTransformer(NetworkEntityMetadataObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.DOWN_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 2), ProtocolVersionsHelper.UP_1_13);
		add(new NetworkEntityMetadataObjectIndexValueOptionalChatToLegacyTextTransformer(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 2), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		add(new NetworkEntityMetadataObjectIndexValueOptionalChatToLegacyTextTransformer(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 2), ProtocolVersion.MINECRAFT_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersionsHelper.UP_1_9);
		add(new NetworkEntityMetadataObjectIndexValueBooleanToByteTransformer(NetworkEntityMetadataObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersion.MINECRAFT_1_8);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Entity.SILENT, 4), ProtocolVersionsHelper.UP_1_9);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Entity.NO_GRAVITY, 5), ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_10));

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Entity.POSE, 6), ProtocolVersionsHelper.UP_1_14);

		add(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Entity.FROZEN_TIME, 7), ProtocolVersionsHelper.UP_1_17);
	}

}
