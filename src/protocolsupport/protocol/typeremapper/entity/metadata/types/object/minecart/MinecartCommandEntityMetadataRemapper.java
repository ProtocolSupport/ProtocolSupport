package protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperChatToString;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class MinecartCommandEntityMetadataRemapper extends MinecartEntityMetadataRemapper {

	public MinecartCommandEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.MinecartCommand.COMMAND, PeMetaBase.COMMAND_COMMAND), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.MinecartCommand.COMMAND, 13), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.MinecartCommand.COMMAND, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.MinecartCommand.COMMAND, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.MinecartCommand.COMMAND, 23), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8));

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.MinecartCommand.LAST_OUTPUT, 14), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.MinecartCommand.LAST_OUTPUT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.MinecartCommand.LAST_OUTPUT, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperChatToString(NetworkEntityMetadataObjectIndex.MinecartCommand.LAST_OUTPUT, 24, 64), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8));
	}

}
