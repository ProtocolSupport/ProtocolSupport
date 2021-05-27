package protocolsupport.protocol.typeremapper.entity.format.metadata.object.misc;

import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class NetworkEntityMetadataObjectAddOnFirstUpdateTransformer extends NetworkEntityMetadataFormatTransformer {

	private final int index;
	private final NetworkEntityMetadataObject<?> object;

	public NetworkEntityMetadataObjectAddOnFirstUpdateTransformer(int index, NetworkEntityMetadataObject<?> object) {
		this.index = index;
		this.object = object;
	}

	@Override
	public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
		if (entity.getDataCache().isFirstMeta()) {
			remapped.add(index, object);
		}
	}

}
