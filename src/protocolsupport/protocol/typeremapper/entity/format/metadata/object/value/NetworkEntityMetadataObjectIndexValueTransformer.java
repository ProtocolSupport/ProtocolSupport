package protocolsupport.protocol.typeremapper.entity.format.metadata.object.value;

import protocolsupport.protocol.serializer.NetworkEntityMetadataSerializer.NetworkEntityMetadataList;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public abstract class NetworkEntityMetadataObjectIndexValueTransformer<T extends NetworkEntityMetadataObject<?>> extends NetworkEntityMetadataFormatTransformer {

	protected final NetworkEntityMetadataObjectIndex<T> fromIndex;
	protected final int toIndex;

	protected NetworkEntityMetadataObjectIndexValueTransformer(NetworkEntityMetadataObjectIndex<T> fromIndex, int toIndex) {
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	@Override
	public void transform(NetworkEntity entity, ArrayMap<NetworkEntityMetadataObject<?>> original, NetworkEntityMetadataList remapped) {
		T object = fromIndex.getObject(original);
		if (object != null) {
			remapped.add(toIndex, transformValue(object));
		}
	}

	public abstract NetworkEntityMetadataObject<?> transformValue(T object);

}
