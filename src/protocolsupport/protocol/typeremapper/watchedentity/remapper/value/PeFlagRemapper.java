package protocolsupport.protocol.typeremapper.watchedentity.remapper.value;

import protocolsupport.protocol.typeremapper.watchedentity.remapper.DataWatcherDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public final class PeFlagRemapper extends DataWatcherDataRemapper {

	private final int fromByteId;
	private final int[] fromBitPosi;
	private final int[] toPosi;

	public PeFlagRemapper(int fromByteId, int[] fromBitPosi, int[] toPosi) {
		this.fromByteId = fromByteId;
		this.fromBitPosi = fromBitPosi;
		this.toPosi = toPosi;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
		getObject(original, fromByteId, DataWatcherObjectByte.class).ifPresent(fromByteWatcher -> {
			byte fromByte = fromByteWatcher.getValue();
			for(int i = 0; i < fromBitPosi.length; i++) {
				if(toPosi[i] < 0) {
					entity.getDataCache().setPeBaseFlag((-1 * toPosi[i]), (((fromByte >> (fromBitPosi[i] - 1)) & 1) ^ 1));
				} else {
					entity.getDataCache().setPeBaseFlag(toPosi[i], ((fromByte >> (fromBitPosi[i] - 1)) & 1));
				}
			}
		});
	}

}
