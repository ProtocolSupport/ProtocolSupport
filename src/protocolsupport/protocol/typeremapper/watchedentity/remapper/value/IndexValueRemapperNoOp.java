package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public abstract class IndexValueRemapperNoOp<T extends DataWatcherObject<?>> extends IndexValueRemapper<T> {

	public IndexValueRemapperNoOp(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public DataWatcherObject<?> remapValue(T object) {
		return object;
	}

}
