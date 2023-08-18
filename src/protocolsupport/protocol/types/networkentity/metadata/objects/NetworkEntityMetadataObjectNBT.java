package protocolsupport.protocol.types.networkentity.metadata.objects;

import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;

public class NetworkEntityMetadataObjectNBT extends NetworkEntityMetadataObject<NBTCompound> {

	public NetworkEntityMetadataObjectNBT(NBTCompound nbt) {
		this.value = nbt;
	}

}
