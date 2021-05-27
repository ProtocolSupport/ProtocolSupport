package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import java.util.function.Consumer;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class NoopMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

	public static final NoopMetadataTransformer INSTANCE = new NoopMetadataTransformer();

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
	}

}