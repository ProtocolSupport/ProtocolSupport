package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public abstract class ValueRemapperNoOp<T extends DataWatcherObject<?>> extends WatchedDataIndexValueRemapper<T> {

	public ValueRemapperNoOp(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(T object) {
		return object;
	}

}
