package protocolsupport.protocol.typeremapper.watchedentity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;

public class WatchedDataRemapper {

	private static final TIntObjectMap<DataWatcherObject<?>> EMPTY_MAP = new TIntObjectHashMap<>();

	public static TIntObjectMap<DataWatcherObject<?>> transform(NetworkEntity entity, TIntObjectMap<DataWatcherObject<?>> originaldata, ProtocolVersion to) {
		if (entity == null) {
			return EMPTY_MAP;
		}
		TIntObjectHashMap<DataWatcherObject<?>> transformed = new TIntObjectHashMap<>();
		SpecificRemapper.fromWatchedType(entity.getType()).getRemaps(to)
		.forEach(remapper -> remapper.remap(entity, originaldata, transformed));
		entity.getDataCache().firstMeta = false;
		return transformed;
	}

}
