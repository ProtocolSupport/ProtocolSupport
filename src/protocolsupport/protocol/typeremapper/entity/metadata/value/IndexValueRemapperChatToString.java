package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;

public class IndexValueRemapperChatToString extends IndexValueRemapper<DataWatcherObjectChat> {

	protected final int limit;

	public IndexValueRemapperChatToString(DataWatcherObjectIndex<DataWatcherObjectChat> fromIndex, int toIndex, int limit) {
		super(fromIndex, toIndex);
		this.limit = limit;
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectChat object) {
		//TODO: pass locale
		return new DataWatcherObjectString(LegacyChat.clampLegacyText(object.getValue().toLegacyText(), limit));
	}

}
