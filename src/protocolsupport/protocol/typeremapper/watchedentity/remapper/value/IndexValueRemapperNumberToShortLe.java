package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectNumber;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShortLe;

public final class IndexValueRemapperNumberToShortLe extends IndexValueRemapper<DataWatcherObjectNumber<?>> {

	public IndexValueRemapperNumberToShortLe(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObjectNumber<?> object) {
		return new DataWatcherObjectShortLe(object.getValue().shortValue());
	}


}
