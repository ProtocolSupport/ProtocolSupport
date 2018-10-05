package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.utils.Utils;

public class IndexValueRemapperStringClamp extends IndexValueRemapper<DataWatcherObjectString> {

	protected final int limit;

	public IndexValueRemapperStringClamp(DataWatcherObjectIndex<DataWatcherObjectString> fromIndex, int toIndex, int limit) {
		super(fromIndex, toIndex);
		this.limit = limit;
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectString object) {
		return new DataWatcherObjectString(Utils.clampString(object.getValue(), limit));
	}

}
