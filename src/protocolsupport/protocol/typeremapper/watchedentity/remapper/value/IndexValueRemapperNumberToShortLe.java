package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShortLe;

public final class IndexValueRemapperNumberToShortLe extends IndexValueRemapper<Number, DataWatcherObject<Number>> {

	@SuppressWarnings("unchecked")
	public IndexValueRemapperNumberToShortLe(DataWatcherObjectIndex<? extends DataWatcherObject<? extends Number>> fromIndex, int toIndex) {
		super((DataWatcherObjectIndex<DataWatcherObject<Number>>) fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObject<Number> object) {
		return new DataWatcherObjectShortLe(object.getValue().shortValue());
	}


}
