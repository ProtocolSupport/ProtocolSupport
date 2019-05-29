package protocolsupport.protocol.typeremapper.entity.metadata;

import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class FirstDataWatcherUpdateObjectAddRemapper extends DataWatcherObjectRemapper {

	private final int index;
	private final NetworkEntityMetadataObject<?> object;
	public FirstDataWatcherUpdateObjectAddRemapper(int index, NetworkEntityMetadataObject<?> object) {
		this.index = index;
		this.object = object;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
		if (entity.getDataCache().isFirstMeta()) {
			remapped.put(index, object);
		}
	}

}
