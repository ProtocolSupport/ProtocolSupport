package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class DataWatcherDataRemapper {

	public abstract void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped);

}
