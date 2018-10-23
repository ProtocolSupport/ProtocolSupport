package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class InsentientEntityMetadataRemapper extends LivingEntityMetadataRemapper {

	public static final InsentientEntityMetadataRemapper INSTANCE = new InsentientEntityMetadataRemapper();

	public InsentientEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Insentient.FLAGS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Insentient.FLAGS, 10), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Insentient.FLAGS, 15), ProtocolVersion.MINECRAFT_1_8);
	}

}
