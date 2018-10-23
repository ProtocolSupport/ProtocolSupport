package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.typeremapper.entity.metadata.DataWatcherObjectRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public final class PeFlagRemapper extends DataWatcherObjectRemapper {

	protected final DataWatcherObjectIndex<DataWatcherObjectByte> fromByteId;
	protected final int[] fromBitPosI;
	protected final int[] toPosI;

	public PeFlagRemapper(DataWatcherObjectIndex<DataWatcherObjectByte> fromByteId, int[] fromBitPosi, int[] toPosi) {
		this.fromByteId = fromByteId;
		this.fromBitPosI = fromBitPosi;
		this.toPosI = toPosi;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
		fromByteId.getValue(original)
		.ifPresent(fromByteWatcher -> {
			byte fromByte = fromByteWatcher.getValue();
			for (int i = 0; i < fromBitPosI.length; i++) {
				if (toPosI[i] < 0) {
					entity.getDataCache().setPeBaseFlag((-1 * toPosI[i]), (((fromByte >> (fromBitPosI[i] - 1)) & 1) ^ 1));
				} else {
					entity.getDataCache().setPeBaseFlag(toPosI[i], ((fromByte >> (fromBitPosI[i] - 1)) & 1));
				}
			}
		});
	}

}
