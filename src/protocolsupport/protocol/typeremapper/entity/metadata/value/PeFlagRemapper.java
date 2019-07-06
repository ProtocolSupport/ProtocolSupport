package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public final class PeFlagRemapper extends NetworkEntityMetadataObjectRemapper {

	protected final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> fromByteId;
	protected final int[] fromBitPosI;
	protected final int[] toPosI;

	public PeFlagRemapper(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectByte> fromByteId, int[] fromBitPosi, int[] toPosi) {
		this.fromByteId = fromByteId;
		this.fromBitPosI = fromBitPosi;
		this.toPosI = toPosi;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
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
