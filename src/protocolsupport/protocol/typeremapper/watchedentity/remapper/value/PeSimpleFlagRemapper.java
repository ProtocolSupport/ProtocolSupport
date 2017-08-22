package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.DataWatcherDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class PeSimpleFlagRemapper extends DataWatcherDataRemapper {

	private final int fromBooleanId;
	private final int toFlagId;

	public PeSimpleFlagRemapper(int fromBooleanId, int toFlagId) {
		this.fromBooleanId = fromBooleanId;
		this.toFlagId = toFlagId;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
		getObject(original, fromBooleanId, DataWatcherObjectBoolean.class).ifPresent(booleanWatcher -> {
			if(toFlagId < 0) {
				entity.getDataCache().setPeBaseFlag((-1 * toFlagId), !booleanWatcher.getValue());
			} else {
				entity.getDataCache().setPeBaseFlag(toFlagId, booleanWatcher.getValue());
			}
		});
	}

}
