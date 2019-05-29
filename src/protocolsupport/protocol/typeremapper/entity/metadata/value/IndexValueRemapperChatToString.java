package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;

public class IndexValueRemapperChatToString extends IndexValueRemapper<NetworkEntityMetadataObjectChat> {

	protected final int limit;

	public IndexValueRemapperChatToString(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectChat> fromIndex, int toIndex, int limit) {
		super(fromIndex, toIndex);
		this.limit = limit;
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectChat object) {
		//TODO: pass locale
		return new NetworkEntityMetadataObjectString(LegacyChat.clampLegacyText(object.getValue().toLegacyText(), limit));
	}

}
