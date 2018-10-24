package protocolsupport.protocol.typeremapper.entity.metadata;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class FirstDataWatcherUpdateObjectAddRemapper extends DataWatcherObjectRemapper {

	private final int index;
	private final DataWatcherObject<?> object;
	public FirstDataWatcherUpdateObjectAddRemapper(int index, DataWatcherObject<?> object) {
		this.index = index;
		this.object = object;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
		if (entity.getDataCache().isFirstMeta()) {
			remapped.put(index, object);
		}
	}

}
