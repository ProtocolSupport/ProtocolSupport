package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;

public class IndexValueRemapperNumberToInt extends IndexValueRemapper<DataWatcherObject<Number>> {

	@SuppressWarnings("unchecked")
	public IndexValueRemapperNumberToInt(DataWatcherObjectIndex<? extends DataWatcherObject<? extends Number>> fromIndex, int toIndex) {
		super((DataWatcherObjectIndex<DataWatcherObject<Number>>) fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObject<Number> object) {
		return new DataWatcherObjectInt(object.getValue().intValue());
	}

}
