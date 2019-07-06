package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class PeSimpleFlagAdder extends NetworkEntityMetadataObjectRemapper {

	protected final int[] flags;
	protected final boolean[] set;

	public PeSimpleFlagAdder(int[] flags, boolean[] set) {
		this.flags = flags;
		this.set = set;
	}

	@Override
	public void remap(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, ArrayMap<NetworkEntityMetadataObject<?>> remapped) {
		for (int i = 0; i < flags.length; i++) {
			entity.getDataCache().setPeBaseFlag(flags[i], set[i]);
		}
	}

}
