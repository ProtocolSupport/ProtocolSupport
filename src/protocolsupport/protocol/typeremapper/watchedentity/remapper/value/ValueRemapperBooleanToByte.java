package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;

public class ValueRemapperBooleanToByte extends ValueRemapper<DataWatcherObjectBoolean> {

	public static final ValueRemapperBooleanToByte INSTANCE = new ValueRemapperBooleanToByte();

	protected ValueRemapperBooleanToByte() {
	}

	@Override
	public DataWatcherObject<?> remap(DataWatcherObjectBoolean object) {
		return new DataWatcherObjectByte((byte) (object.getValue() ? 1 : 0));
	}

}
