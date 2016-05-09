package protocolsupport.protocol.typeremapper.watchedentity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.MappingEntry;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificType;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;

public class WatchedDataRemapper {

	private static final TIntObjectMap<DataWatcherObject<?>> EMPTY_MAP = new TIntObjectHashMap<DataWatcherObject<?>>();

	@SuppressWarnings("unchecked")
	public static TIntObjectMap<DataWatcherObject<?>> transform(WatchedEntity entity, TIntObjectMap<DataWatcherObject<?>> originaldata, ProtocolVersion to) {
		if (entity == null) {
			return EMPTY_MAP;
		}
		TIntObjectHashMap<DataWatcherObject<?>> transformed = new TIntObjectHashMap<DataWatcherObject<?>>();
		SpecificType stype = entity.getType();
		for (MappingEntry entry : stype.getRemaps(to)) {
			DataWatcherObject<?> object = originaldata.get(entry.getIdFrom());
			if (object != null) {
				transformed.put(entry.getIdTo(), entry.getValueRemapper().remap(object));
			}
		}
		return transformed;
	}

}
