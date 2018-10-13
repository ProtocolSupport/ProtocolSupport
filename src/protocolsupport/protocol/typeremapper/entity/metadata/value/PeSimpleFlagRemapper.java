package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class PeSimpleFlagRemapper extends DataWatcherObjectRemapper {

	protected final DataWatcherObjectIndex<DataWatcherObjectBoolean> fromBooleanId;
	protected final int toFlagId;

	public PeSimpleFlagRemapper(DataWatcherObjectIndex<DataWatcherObjectBoolean> fromBooleanId, int toFlagId) {
		this.fromBooleanId = fromBooleanId;
		this.toFlagId = toFlagId;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
		fromBooleanId.getValue(original)
		.ifPresent(booleanWatcher -> {
			if (toFlagId < 0) {
				entity.getDataCache().setPeBaseFlag((-1 * toFlagId), !booleanWatcher.getValue());
			} else {
				entity.getDataCache().setPeBaseFlag(toFlagId, booleanWatcher.getValue());
			}
		});
	}

}
