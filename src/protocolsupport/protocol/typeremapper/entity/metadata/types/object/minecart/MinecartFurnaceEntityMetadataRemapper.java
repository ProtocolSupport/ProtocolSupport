package protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart;

import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class MinecartFurnaceEntityMetadataRemapper extends MinecartEntityMetadataRemapper {

	public MinecartFurnaceEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartFurnace.POWERED, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartFurnace.POWERED, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.MinecartFurnace.POWERED, 16), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
