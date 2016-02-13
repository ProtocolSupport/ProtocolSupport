package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.utils.DataWatcherObject;

public interface ValueRemapper {

	public static final ValueRemapper NO_OP = new ValueRemapper() {
		@Override
		public DataWatcherObject remap(DataWatcherObject object) {
			return object;
		}
	};

	public DataWatcherObject remap(DataWatcherObject object);

}
