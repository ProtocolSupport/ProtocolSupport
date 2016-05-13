package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public interface ValueRemapper<T extends DataWatcherObject<?>> {

	public static final ValueRemapper<DataWatcherObject<?>> NO_OP = new ValueRemapper<DataWatcherObject<?>>() {
		@Override
		public DataWatcherObject<?> remap(DataWatcherObject<?> object) {
			return object;
		}
	};

	public DataWatcherObject<?> remap(T object);

}
