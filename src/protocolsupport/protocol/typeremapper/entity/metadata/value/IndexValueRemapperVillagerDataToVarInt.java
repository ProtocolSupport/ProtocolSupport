package protocolsupport.protocol.typeremapper.entity.metadata.value;

import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;

public class IndexValueRemapperVillagerDataToVarInt extends IndexValueRemapper<NetworkEntityMetadataObjectVillagerData> {

	public IndexValueRemapperVillagerDataToVarInt(NetworkEntityMetadataObjectIndex<NetworkEntityMetadataObjectVillagerData> fromIndex, int toIndex) {
		super(fromIndex, toIndex);
	}

	@Override
	public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVillagerData object) {
		return new NetworkEntityMetadataObjectVarInt(object.getValue().getProfession());
	}

}
