package protocolsupport.protocol.typeremapper.watchedentity;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class DataWatcherDataRemapper {

	protected final ArrayMap<DataWatcherObject<?>> originalMetadata = new ArrayMap<>(DataWatcherDeserializer.MAX_USED_META_INDEX + 1);
	protected final ArrayMap<DataWatcherObject<?>> remappedMetadata = new ArrayMap<>(DataWatcherDeserializer.MAX_USED_META_INDEX + 1);

	public void init(ByteBuf serverdata, ProtocolVersion version, String locale, NetworkEntity entity) {
		originalMetadata.clear();
		remappedMetadata.clear();
		DataWatcherDeserializer.decodeDataTo(serverdata, ProtocolVersionsHelper.LATEST_PC, locale, originalMetadata);
		remap(version, entity);
	}

	public void remap(ProtocolVersion version, NetworkEntity entity) {
		if (entity != null) {
			SpecificRemapper.fromWatchedType(entity.getType()).getRemaps(version)
			.forEach(remapper -> remapper.remap(entity, originalMetadata, remappedMetadata));
			entity.getDataCache().setFirstMeta(false);
		}
	}

	public ArrayMap<DataWatcherObject<?>> getOriginal() {
		return originalMetadata;
	}

	public ArrayMap<DataWatcherObject<?>> getRemapped() {
		return remappedMetadata;
	}

}
