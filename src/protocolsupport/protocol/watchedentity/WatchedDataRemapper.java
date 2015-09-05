package protocolsupport.protocol.watchedentity;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.watchedentity.remapper.RemappingEntry;
import protocolsupport.protocol.watchedentity.remapper.SpecificType;
import protocolsupport.protocol.watchedentity.types.WatchedEntity;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.DataWatcherObject.ValueType;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class WatchedDataRemapper {

	public static TIntObjectMap<DataWatcherObject> transform(WatchedEntity entity, TIntObjectMap<DataWatcherObject> originaldata, ProtocolVersion to) {
		if (entity == null) {
			return originaldata;
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
			transformed.put(31, new DataWatcherObject(ValueType.BYTE, (byte) 0));
		}
		return transformed;
	}

}
