package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.utils.Utils;

public class ValueRemapperStringClamp extends ValueRemapper<DataWatcherObjectString> {

	private int limit;
	public ValueRemapperStringClamp(int limit) {
		this.limit = limit;
	}

	@Override
	public DataWatcherObject<?> remap(DataWatcherObjectString object) {
		return new DataWatcherObjectString(Utils.clampString(object.getValue(), limit));
	}



}
