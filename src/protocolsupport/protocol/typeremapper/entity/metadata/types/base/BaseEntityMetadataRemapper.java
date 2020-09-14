package protocolsupport.protocol.typeremapper.entity.metadata.types.base;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNumberToShort;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperOptionalChatToString;
import protocolsupport.protocol.typeremapper.entity.metadata.types.EntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class BaseEntityMetadataRemapper extends EntityMetadataRemapper {

	public static final BaseEntityMetadataRemapper INSTANCE = new BaseEntityMetadataRemapper();

	protected BaseEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.BASE_FLAGS, 0), ProtocolVersionsHelper.ALL_PC);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.UP_1_9);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNumberToShort(NetworkEntityMetadataObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.DOWN_1_8);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 2), ProtocolVersionsHelper.UP_1_13);
		addRemap(new NetworkEntityMetadataIndexValueRemapperOptionalChatToString(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 2), ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperOptionalChatToString(NetworkEntityMetadataObjectIndex.Entity.NAMETAG, 2), ProtocolVersion.MINECRAFT_1_8);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersionsHelper.UP_1_9);
		addRemap(new NetworkEntityMetadataIndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersion.MINECRAFT_1_8);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.SILENT, 4), ProtocolVersionsHelper.UP_1_9);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.NO_GRAVITY, 5), ProtocolVersion.getAllAfterI(ProtocolVersion.MINECRAFT_1_10));

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Entity.POSE, 6), ProtocolVersionsHelper.UP_1_14);
	}

}
