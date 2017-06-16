package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.utils.Utils;

public final class IndexValueRemapperStringClamp extends IndexValueRemapper<DataWatcherObjectString> {

	private final int limit;
	public IndexValueRemapperStringClamp(int fromIndex, int toIndex, int limit) {
		super(fromIndex, toIndex);
		this.limit = limit;
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectString object) {
		return new DataWatcherObjectString(Utils.clampString(object.getValue(), limit));
	}

}
