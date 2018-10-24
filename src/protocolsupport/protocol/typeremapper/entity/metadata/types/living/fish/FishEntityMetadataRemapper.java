package protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish;

import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class FishEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public static final FishEntityMetadataRemapper INSTANCE = new FishEntityMetadataRemapper();

	public FishEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BaseFish.FROM_BUCKET, 12), ProtocolVersionsHelper.UP_1_13);
	}

}
