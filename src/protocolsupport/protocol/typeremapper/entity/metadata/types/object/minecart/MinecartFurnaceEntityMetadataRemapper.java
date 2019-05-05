package protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class MinecartFurnaceEntityMetadataRemapper extends MinecartEntityMetadataRemapper {

	public MinecartFurnaceEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.MinecartFurnace.POWERED, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.MinecartFurnace.POWERED, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.MinecartFurnace.POWERED, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
