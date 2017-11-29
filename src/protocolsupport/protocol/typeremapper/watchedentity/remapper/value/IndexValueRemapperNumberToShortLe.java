package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShortLe;
import protocolsupport.protocol.utils.datawatcher.objects.ReadableDataWatcherObjectNumber;

public final class IndexValueRemapperNumberToShortLe extends IndexValueRemapper<ReadableDataWatcherObjectNumber<?>> {

	public IndexValueRemapperNumberToShortLe(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(ReadableDataWatcherObjectNumber<?> object) {
		return new DataWatcherObjectShortLe(object.getValue().shortValue());
	}


}
