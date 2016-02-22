package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.Utils;

public class ValueRemapperStringClamp implements ValueRemapper {

	private int limit;
	public ValueRemapperStringClamp(int limit) {
		this.limit = limit;
	}

	@Override
	public DataWatcherObject remap(DataWatcherObject object) {
		object.value = Utils.clampString((String) object.value, limit);
		return object;
	}



}
