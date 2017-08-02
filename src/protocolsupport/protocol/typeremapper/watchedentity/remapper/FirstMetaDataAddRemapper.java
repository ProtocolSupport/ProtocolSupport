package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class FirstMetaDataAddRemapper extends DataWatcherDataRemapper {

	private final int index;
	private final DataWatcherObject<?> object;
	public FirstMetaDataAddRemapper(int index, DataWatcherObject<?> object) {
		this.index = index;
		this.object = object;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
		if (entity.getDataCache().firstMeta) {
			remapped.put(index, object);
		}
	}

}
