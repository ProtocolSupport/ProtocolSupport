package protocolsupport.protocol.typeremapper.watchedentity.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.utils.Utils;

public class IndexValueRemapperOptionalChatToString extends IndexValueRemapper<DataWatcherObjectOptionalChat> {

	protected final int limit;

	public IndexValueRemapperOptionalChatToString(DataWatcherObjectIndex<DataWatcherObjectOptionalChat> fromIndex, int toIndex, int limit) {
		super(fromIndex, toIndex);
		this.limit = limit;
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectOptionalChat object) {
		//TODO: pass locale
		return new DataWatcherObjectString(object.getValue() != null ? Utils.clampString(object.getValue().toLegacyText(), limit) : "");
	}

}
