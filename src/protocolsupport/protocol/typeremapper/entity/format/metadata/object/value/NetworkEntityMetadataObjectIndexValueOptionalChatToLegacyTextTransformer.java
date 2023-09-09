package protocolsupport.protocol.typeremapper.entity.format.metadata.object.value;

import protocolsupport.api.chat.ChatFormat;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;

public class NetworkEntityMetadataObjectIndexValueOptionalChatToLegacyTextTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectOptionalChat> {

	public static final int NO_LIMIT = -1;

	protected final int limit;

	public NetworkEntityMetadataObjectIndexValueOptionalChatToLegacyTextTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalChat> fromIndex, int toIndex, int limit) {
		super(fromIndex, toIndex);
		this.limit = limit;
	}

	public NetworkEntityMetadataObjectIndexValueOptionalChatToLegacyTextTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectOptionalChat> fromIndex, int toIndex) {
		this(fromIndex, toIndex, NO_LIMIT);
	}

	@Override
	public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectOptionalChat object) {
		if (object.getValue() != null) {
			String text = object.getValue().toLegacyText();
			if (limit != NO_LIMIT) {
				text = LegacyChat.clampLegacyText(object.getValue().toLegacyText(), limit);
			}
			return new NetworkEntityMetadataObjectString(!text.isEmpty() ? text : ChatFormat.BLACK.toStyle());
		} else {
			return new NetworkEntityMetadataObjectString("");
		}
	}

}
