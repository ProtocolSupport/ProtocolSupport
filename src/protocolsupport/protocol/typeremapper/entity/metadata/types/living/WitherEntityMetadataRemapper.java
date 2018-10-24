package protocolsupport.protocol.typeremapper.entity.metadata.types.living;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarLong;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;

public class WitherEntityMetadataRemapper extends InsentientEntityMetadataRemapper {

	public WitherEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNumberToSVarLong(DataWatcherObjectIndex.Wither.TARGET1, PeMetaBase.WITHER_TARGET1), ProtocolVersion.MINECRAFT_PE);
		addRemap(new IndexValueRemapperNumberToSVarLong(DataWatcherObjectIndex.Wither.TARGET2, PeMetaBase.WITHER_TARGET2), ProtocolVersion.MINECRAFT_PE);
		addRemap(new IndexValueRemapperNumberToSVarLong(DataWatcherObjectIndex.Wither.TARGET3, PeMetaBase.WITHER_TARGET3), ProtocolVersion.MINECRAFT_PE);
		addRemap(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, PeMetaBase.INVULNERABLE_TIME), ProtocolVersion.MINECRAFT_PE);

		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET1, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET1, 11), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET1, 17), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET2, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET2, 12), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET2, 18), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET3, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET3, 13), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET3, 19), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 14), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 20), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
