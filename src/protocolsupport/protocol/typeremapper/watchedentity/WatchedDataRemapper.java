package protocolsupport.protocol.typeremapper.watchedentity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.MappingEntry;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.Utils;

public class WatchedDataRemapper {

	private static final TIntObjectMap<DataWatcherObject<?>> EMPTY_MAP = new TIntObjectHashMap<>();

	@SuppressWarnings("unchecked")
	public static TIntObjectMap<DataWatcherObject<?>> transform(WatchedEntity entity, TIntObjectMap<DataWatcherObject<?>> originaldata, ProtocolVersion to) {
		if (entity == null) {
			return EMPTY_MAP;
		}
		TIntObjectHashMap<DataWatcherObject<?>> transformed = new TIntObjectHashMap<>();
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
					throw new MetadataRemapException(entry.getIdFrom(), entity.getId(), entity.getType(), to, e);
				}
			}
		}
		return transformed;
	}

	public static class MetadataRemapException extends RuntimeException {

		public MetadataRemapException(int index, int entityId, SpecificRemapper type, ProtocolVersion to, Exception e) {
			super(Utils.exceptionMessage(
				"Unable to remap entity metadata",
				String.format("Metadata index: %d", index),
				String.format("Entity id: %d", entityId),
				String.format("Entity type: %s", type),
				String.format("To protocol version: %s", to)
			), e);
		}

		private static final long serialVersionUID = 1L;

	}

}
