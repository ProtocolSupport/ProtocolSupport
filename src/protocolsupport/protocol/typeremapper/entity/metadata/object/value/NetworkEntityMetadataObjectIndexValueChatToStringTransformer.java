package protocolsupport.protocol.typeremapper.entity.metadata.object.value;

import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;

public class NetworkEntityMetadataObjectIndexValueChatToStringTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectChat> {

	protected final int limit;

	public NetworkEntityMetadataObjectIndexValueChatToStringTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectChat> fromIndex, int toIndex, int limit) {
		super(fromIndex, toIndex);
		this.limit = limit;
	}

	@Override
	public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectChat object) {
		//TODO: pass locale
		return new NetworkEntityMetadataObjectString(LegacyChat.clampLegacyText(object.getValue().toLegacyText(), limit));
	}

}
