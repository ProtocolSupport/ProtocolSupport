package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloatLe;

public class IndexValueRemapperNumberToFloatLe extends IndexValueRemapper<DataWatcherObject<Number>> {

	@SuppressWarnings("unchecked")
	public IndexValueRemapperNumberToFloatLe(DataWatcherObjectIndex<? extends DataWatcherObject<? extends Number>> fromIndex, int toIndex) {
		super((DataWatcherObjectIndex<DataWatcherObject<Number>>) fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObject<Number> object) {
		return new DataWatcherObjectFloatLe(object.getValue().floatValue());
	}

}