package protocolsupport.protocol.typeremapper.entity.metadata.object.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;

public class NetworkEntityMetadataIndexValueRemapperVillagerDataToVarInt extends NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObjectVillagerData> {

	public NetworkEntityMetadataIndexValueRemapperVillagerDataToVarInt(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVillagerData> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVillagerData object) {
		return new NetworkEntityMetadataObjectVarInt(object.getValue().getProfession());
	}

}
