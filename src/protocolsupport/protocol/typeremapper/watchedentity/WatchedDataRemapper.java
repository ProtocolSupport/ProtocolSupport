package protocolsupport.protocol.typeremapper.watchedentity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.MappingEntry;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.Utils;

public class WatchedDataRemapper {

	private static final TIntObjectMap<DataWatcherObject<?>> EMPTY_MAP = new TIntObjectHashMap<>();

	@SuppressWarnings("unchecked")
	public static TIntObjectMap<DataWatcherObject<?>> transform(NetworkEntity entity, TIntObjectMap<DataWatcherObject<?>> originaldata, ProtocolVersion to) {
		if (entity == null) {
			return EMPTY_MAP;
		}
		//copy active hand meta to base flags index 4
		if (entity.getType() == NetworkEntityType.PLAYER) {
			DataWatcherObject<?> baseflags = originaldata.get(0);
			if (baseflags != null) {
				if (baseflags.getValue() instanceof Number) {
					entity.getDataCache().baseMetaFlags = ((Number) baseflags.getValue()).byteValue();
				}
			}
			DataWatcherObject<?> activehandflags = originaldata.get(6);
			if (activehandflags != null) {
				if (activehandflags.getValue() instanceof Number) {
					byte activehandvalue = ((Number) activehandflags.getValue()).byteValue();
					byte basevalue = entity.getDataCache().baseMetaFlags;
					switch (activehandvalue) {
						case 1: {
							basevalue |= (1 << 4);
							break;
						}
						case 0: {
							basevalue &= ~(1 << 4);
							break;
						}
						default: {
							break;
						}
					}
					originaldata.put(0, new DataWatcherObjectByte(basevalue));
				}
			}
		}
		//registry based remap
		TIntObjectHashMap<DataWatcherObject<?>> transformed = new TIntObjectHashMap<>();
		SpecificRemapper stype = SpecificRemapper.fromWatchedType(entity.getType());
		for (MappingEntry entry : stype.getRemaps(to)) {
			DataWatcherObject<?> object = originaldata.get(entry.getIdFrom());
			if (object != null) {
				ValueRemapper<DataWatcherObject<?>> remapper = entry.getValueRemapper();
				try {
					if (remapper.isValid(object)) {
						DataWatcherObject<?> remapped = remapper.remap(object);
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

		public MetadataRemapException(int index, int entityId, NetworkEntityType type, ProtocolVersion to, Exception e) {
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
