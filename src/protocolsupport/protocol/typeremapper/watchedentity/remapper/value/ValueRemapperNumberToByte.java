package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectNumber;

public final class ValueRemapperNumberToByte extends WatchedDataIndexValueRemapper<DataWatcherObjectNumber<?>> {

	public ValueRemapperNumberToByte(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectNumber<?> object) {
		return new DataWatcherObjectByte(object.getValue().byteValue());
	}

}
