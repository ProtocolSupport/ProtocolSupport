package protocolsupport.protocol.typeremapper.entity.legacy.metadata;

import java.util.function.Consumer;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class BatInitDefaultMetadataTranfromer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

	public static final BatInitDefaultMetadataTranfromer INSTANCE = new BatInitDefaultMetadataTranfromer();

	@Override
	public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		NetworkEntityMetadataObjectIndex.Bat.HANGING.setObject(t, new NetworkEntityMetadataObjectByte((byte) 0));
	}

}