package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperDirectionToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class ShulkerEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public ShulkerEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, PeMetaBase.SHULKER_HEIGHT), ProtocolVersion.MINECRAFT_PE);
		addRemap(new IndexValueRemapperDirectionToByte(DataWatcherObjectIndex.Shulker.DIRECTION, PeMetaBase.SHULKER_DIRECTION), ProtocolVersion.MINECRAFT_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.DIRECTION, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.DIRECTION, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 13), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.COLOR, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_2);
	}

}
