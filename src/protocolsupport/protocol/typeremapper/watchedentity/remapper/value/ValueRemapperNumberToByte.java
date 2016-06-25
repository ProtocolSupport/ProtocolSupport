package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;

public class ValueRemapperNumberToByte extends ValueRemapper<DataWatcherObject<?>> {

	public static final ValueRemapperNumberToByte INSTANCE = new ValueRemapperNumberToByte();

	protected ValueRemapperNumberToByte() {
	}

	@Override
	public DataWatcherObject<?> remap(DataWatcherObject<?> object) {
		Number number = (Number) object.getValue();
		return new DataWatcherObjectByte(number.byteValue());
	}

}
