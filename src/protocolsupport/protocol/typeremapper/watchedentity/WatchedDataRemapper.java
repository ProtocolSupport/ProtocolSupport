package protocolsupport.protocol.typeremapper.watchedentity;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class WatchedDataRemapper {

	public static ArrayMap<DataWatcherObject<?>> transform(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> originaldata, ProtocolVersion to) {
		if (entity == null) {
			return null;
		}
		ArrayMap<DataWatcherObject<?>> transformed = new ArrayMap<>(256);
		SpecificRemapper.fromWatchedType(entity.getType()).getRemaps(to)
		.forEach(remapper -> remapper.remap(entity, originaldata, transformed));
		entity.getDataCache().firstMeta = false;
		return transformed;
	}

}
