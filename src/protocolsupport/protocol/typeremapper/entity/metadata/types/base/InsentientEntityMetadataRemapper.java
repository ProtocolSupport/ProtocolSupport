package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class InsentientEntityMetadataRemapper extends LivingEntityMetadataRemapper {

	public static final InsentientEntityMetadataRemapper INSTANCE = new InsentientEntityMetadataRemapper();

	public InsentientEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Insentient.INS_FLAGS, 13), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Insentient.INS_FLAGS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Insentient.INS_FLAGS, 10), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Insentient.INS_FLAGS, 15), ProtocolVersion.MINECRAFT_1_8);
	}

}
