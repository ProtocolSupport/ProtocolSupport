package protocolsupport.protocol.typeremapper.watchedentity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.MappingEntry;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapper;
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
		SpecificRemapper stype = entity.getType();
		for (MappingEntry entry : stype.getRemaps(to)) {
			DataWatcherObject<?> object = originaldata.get(entry.getIdFrom());
			if (object != null) {
				ValueRemapper<DataWatcherObject<?>> remapper = entry.getValueRemapper();
				try {
					if (remapper.isValid(object)) {
						DataWatcherObject<?> remapped = remapper.remap(object);
						remapped.getTypeId(to);
						transformed.put(entry.getIdTo(), remapped);
					}
				} catch (Exception e) {
					throw new MetadataRemapException(
						"Unable to remap "+
						"metadata(index: " + entry.getIdFrom() + ") "+
						"for entity "+
						"(id: " + entity.getId()+
						", type: " + entity.getType() + ")"+
						" to protocol version " + to
						,e
					);
				}
			}
		}
		return transformed;
	}

	public static class MetadataRemapException extends RuntimeException {

		public MetadataRemapException(String string, Exception e) {
			super(string, e);
		}

		private static final long serialVersionUID = 1L;
		
	}

}
