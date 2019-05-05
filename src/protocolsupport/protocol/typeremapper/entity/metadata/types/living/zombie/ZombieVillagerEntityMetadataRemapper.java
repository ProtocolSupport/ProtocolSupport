package protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperVillagerDataToVarInt;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ZombieVillagerEntityMetadataRemapper extends ZombieEntityMetadataRemapper {

	public ZombieVillagerEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ZombieVillager.CONVERTING, 17), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ZombieVillager.CONVERTING, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ZombieVillager.CONVERTING, 14), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ZombieVillager.CONVERTING, 13), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.ZombieVillager.CONVERTING, 14), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.ZombieVillager.VDATA, 18), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperVillagerDataToVarInt(NetworkEntityMetadataObjectIndex.ZombieVillager.VDATA, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
