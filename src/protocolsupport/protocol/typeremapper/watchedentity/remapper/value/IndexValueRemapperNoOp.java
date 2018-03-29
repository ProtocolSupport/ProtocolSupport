package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class IndexValueRemapperNoOp extends IndexValueRemapper<Object, DataWatcherObject<Object>> {

	@SuppressWarnings("unchecked")
	public IndexValueRemapperNoOp(DataWatcherObjectIndex<? extends DataWatcherObject<?>> fromIndex, int toIndex) {
		super((DataWatcherObjectIndex<DataWatcherObject<Object>>) fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(DataWatcherObject<Object> object) {
		return object;
	}

}
