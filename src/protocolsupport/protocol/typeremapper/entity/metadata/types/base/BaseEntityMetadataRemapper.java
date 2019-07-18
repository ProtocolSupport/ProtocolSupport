package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNumberToShort;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperOptionalChatToString;
import protocolsupport.protocol.typeremapper.entity.metadata.types.EntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BaseEntityMetadataRemapper extends EntityMetadataRemapper {

	public static final BaseEntityMetadataRemapper INSTANCE = new BaseEntityMetadataRemapper();

	public BaseEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.FLAGS, 0), ProtocolVersionsHelper.ALL_PC);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.UP_1_9);
		addRemap(new IndexValueRemapperNumberToShort(NetworkEntityMetadataObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 2), ProtocolVersionsHelper.UP_1_13);
		addRemap(new IndexValueRemapperOptionalChatToString(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 2, 64), ProtocolVersionsHelper.RANGE__1_9__1_12_2);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersionsHelper.UP_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.SILENT, 4), ProtocolVersionsHelper.UP_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.NO_GRAVITY, 5), ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_10));

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.POSE, 6), ProtocolVersionsHelper.UP_1_14);
	}

}
