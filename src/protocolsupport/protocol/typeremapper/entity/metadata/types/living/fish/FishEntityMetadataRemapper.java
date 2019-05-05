package protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class FishEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final FishEntityMetadataRemapper INSTANCE = new FishEntityMetadataRemapper();

	public FishEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseFish.FROM_BUCKET, 14), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.BaseFish.FROM_BUCKET, 12), ProtocolVersionsHelper.ALL_1_13);
	}

}
