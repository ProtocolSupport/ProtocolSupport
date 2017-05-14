package protocolsupport.protocol.typeremapper.watchedentity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;

public class WatchedDataRemapper {

	private static final TIntObjectMap<DataWatcherObject<?>> EMPTY_MAP = new TIntObjectHashMap<>();

	public static TIntObjectMap<DataWatcherObject<?>> transform(NetworkEntity entity, TIntObjectMap<DataWatcherObject<?>> originaldata, ProtocolVersion to) {
		if (entity == null) {
			return EMPTY_MAP;
		}
		//TODO: complex remapper
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
		TIntObjectHashMap<DataWatcherObject<?>> transformed = new TIntObjectHashMap<>();
		SpecificRemapper.fromWatchedType(entity.getType()).getRemaps(to)
		.forEach(remapper -> remapper.remap(entity, originaldata, transformed));
		entity.getDataCache().firstMeta = false;
		return transformed;
	}

}
