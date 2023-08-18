package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.networkentity.data.VillagerData;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectVillagerData extends NetworkEntityMetadataObject<VillagerData> {

	public NetworkEntityMetadataObjectVillagerData(VillagerData vdata) {
		this.value = vdata;
	}

}
