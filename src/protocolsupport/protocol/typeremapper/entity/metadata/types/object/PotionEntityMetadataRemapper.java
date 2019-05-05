package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class PotionEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public PotionEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Potion.ITEM, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Potion.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Potion.ITEM, 7), ProtocolVersion.MINECRAFT_1_10);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Potion.ITEM, 5), ProtocolVersionsHelper.ALL_1_9);
	}

}
