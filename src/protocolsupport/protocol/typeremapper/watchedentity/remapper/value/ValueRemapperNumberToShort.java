package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectNumber;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShort;

public final class ValueRemapperNumberToShort extends WatchedDataIndexValueRemapper<DataWatcherObjectNumber<?>> {

	public ValueRemapperNumberToShort(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectNumber<?> object) {
		return new DataWatcherObjectShort(object.getValue().shortValue());
	}


}
