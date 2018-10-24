package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class PeSimpleFlagAdder extends DataWatcherObjectRemapper {

	protected final int[] flags;
	protected final boolean[] set;

	public PeSimpleFlagAdder(int[] flags, boolean[] set) {
		this.flags = flags;
		this.set = set;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
		for (int i = 0; i < flags.length; i++) {
			entity.getDataCache().setPeBaseFlag(flags[i], set[i]);
		}
	}

}
