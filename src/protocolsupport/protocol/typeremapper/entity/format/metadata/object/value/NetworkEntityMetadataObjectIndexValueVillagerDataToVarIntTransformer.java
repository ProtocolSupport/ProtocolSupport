package protocolsupport.protocol.typeremapper.entity.format.metadata.object.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;

public class NetworkEntityMetadataObjectIndexValueVillagerDataToVarIntTransformer extends NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectVillagerData> {

	public NetworkEntityMetadataObjectIndexValueVillagerDataToVarIntTransformer(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVillagerData> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVillagerData object) {
		return new NetworkEntityMetadataObjectVarInt(object.getValue().getProfession());
	}

}
