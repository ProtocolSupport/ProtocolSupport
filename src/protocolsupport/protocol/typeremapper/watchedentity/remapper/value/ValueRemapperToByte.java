package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.utils.DataWatcherObject;

public class ValueRemapperToByte implements ValueRemapper {

	@Override
	public DataWatcherObject remap(DataWatcherObject object) {
		object.toByte();
		return object;
	}

}
