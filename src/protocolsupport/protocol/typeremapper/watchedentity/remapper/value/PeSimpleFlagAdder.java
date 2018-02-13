package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.DataWatcherDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class PeSimpleFlagAdder extends DataWatcherDataRemapper {

	private final int[] flags;
	private final boolean[] set;

	public PeSimpleFlagAdder(int[] flags, boolean[] set) {
		this.flags = flags;
		this.set = set;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
		for(int i = 0; i < flags.length; i++) {
			entity.getDataCache().setPeBaseFlag(flags[i], set[i]);
		}
	}

}
