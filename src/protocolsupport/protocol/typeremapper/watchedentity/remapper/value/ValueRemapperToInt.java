package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.utils.DataWatcherObject;

public class ValueRemapperToInt implements ValueRemapper {

	@Override
	public DataWatcherObject remap(DataWatcherObject object) {
		object.toInt();
		return object;
	}

}
