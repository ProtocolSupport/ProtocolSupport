package protocolsupport.protocol.typeremapper.watchedentity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.RemappingEntry;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificType;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.DataWatcherObject.ValueType;

public class WatchedDataRemapper {

	private static final TIntObjectMap<DataWatcherObject> FAKE_EMPTY_MAP = new TIntObjectHashMap<DataWatcherObject>();
	static {
		FAKE_EMPTY_MAP.put(31, new DataWatcherObject(ValueType.BYTE, (byte) 0));
	}

	public static TIntObjectMap<DataWatcherObject> transform(WatchedEntity entity, TIntObjectMap<DataWatcherObject> originaldata, ProtocolVersion to) {
		if (entity == null) {
			return FAKE_EMPTY_MAP;
		}
		TIntObjectHashMap<DataWatcherObject> transformed = new TIntObjectHashMap<DataWatcherObject>();
		SpecificType stype = entity.getType();
		for (RemappingEntry entry : stype.getRemaps(to)) {
			DataWatcherObject object = originaldata.get(entry.getIdFrom());
			if (object != null) {
				transformed.put(entry.getIdTo(), entry.getValueRemapper().remap(object));
			}
		}
		if (transformed.isEmpty()) {
			return FAKE_EMPTY_MAP;
		}
		return transformed;
	}

}
