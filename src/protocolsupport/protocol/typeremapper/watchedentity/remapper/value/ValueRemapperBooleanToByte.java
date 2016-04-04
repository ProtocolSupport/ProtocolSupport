package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.utils.datawatcher.objects.DataWatcherObjectByte;

public class ValueRemapperBooleanToByte implements ValueRemapper<DataWatcherObjectBoolean> {

	public static final ValueRemapperBooleanToByte INSTANCE = new ValueRemapperBooleanToByte();

	protected ValueRemapperBooleanToByte() {
	}

	@Override
	public DataWatcherObject<?> remap(DataWatcherObjectBoolean object) {
		return new DataWatcherObjectByte((byte) (object.getValue() ? 1 : 0));
	}

}
