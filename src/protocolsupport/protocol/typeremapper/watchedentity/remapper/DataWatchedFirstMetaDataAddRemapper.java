package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;

public class DataWatchedFirstMetaDataAddRemapper extends DataWatcherDataRemapper {

	private final int index;
	private final DataWatcherObject<?> object;
	public DataWatchedFirstMetaDataAddRemapper(int index, DataWatcherObject<?> object) {
		this.index = index;
		this.object = object;
	}

	@Override
	public void remap(NetworkEntity entity, TIntObjectMap<DataWatcherObject<?>> original, TIntObjectMap<DataWatcherObject<?>> remapped) {
		if (entity.getDataCache().firstMeta) {
			remapped.put(index, object);
		}
	}

}
