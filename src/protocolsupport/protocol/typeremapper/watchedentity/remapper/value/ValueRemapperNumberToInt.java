package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.datawatcher.objects.DataWatcherObjectInt;

public class ValueRemapperNumberToInt implements ValueRemapper<DataWatcherObject<?>> {

	public static final ValueRemapperNumberToInt INSTANCE = new ValueRemapperNumberToInt();

	protected ValueRemapperNumberToInt() {
	}

	@Override
	public DataWatcherObject<?> remap(DataWatcherObject<?> object) {
		Number number = (Number) object.getValue();
		return new DataWatcherObjectInt(number.byteValue());
	}

}
