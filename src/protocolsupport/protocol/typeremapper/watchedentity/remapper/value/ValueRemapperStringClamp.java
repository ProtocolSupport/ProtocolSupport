package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.utils.Utils;
import protocolsupport.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.datawatcher.objects.DataWatcherObjectString;

public class ValueRemapperStringClamp implements ValueRemapper<DataWatcherObjectString> {

	private int limit;
	public ValueRemapperStringClamp(int limit) {
		this.limit = limit;
	}

	@Override
	public DataWatcherObject<?> remap(DataWatcherObjectString object) {
		return new DataWatcherObjectString(Utils.clampString(object.getValue(), limit));
	}



}
