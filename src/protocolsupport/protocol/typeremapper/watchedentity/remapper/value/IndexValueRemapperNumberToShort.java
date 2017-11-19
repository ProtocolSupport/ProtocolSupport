package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.ReadableDataWatcherObjectNumber;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShort;

public final class IndexValueRemapperNumberToShort extends IndexValueRemapper<ReadableDataWatcherObjectNumber<?>> {

	public IndexValueRemapperNumberToShort(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(ReadableDataWatcherObjectNumber<?> object) {
		return new DataWatcherObjectShort(object.getValue().shortValue());
	}


}
