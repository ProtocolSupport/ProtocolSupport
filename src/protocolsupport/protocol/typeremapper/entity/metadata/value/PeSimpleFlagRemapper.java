package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class PeSimpleFlagRemapper extends NetworkEntityMetadataObjectRemapper {

	protected final NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> fromBooleanId;
	protected final int toFlagId;

	public PeSimpleFlagRemapper(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectBoolean> fromBooleanId, int toFlagId) {
		this.fromBooleanId = fromBooleanId;
		this.toFlagId = toFlagId;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
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
