package protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.block.BlockIdRemappingHelper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;

public class MinecartEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public static final MinecartEntityMetadataRemapper INSTANCE = new MinecartEntityMetadataRemapper();

	public MinecartEntityMetadataRemapper() {
		//TODO Block Remapping for PE/
		//addRemap(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Minecart.BLOCK, PeMetaBase.MINECART_BLOCK), ProtocolVersion.MINECRAFT_PE),
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 5), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 17), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 18), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.BEFORE_1_6);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.BLOCK, 9), ProtocolVersionsHelper.UP_1_13);
		addRemapPerVersion(
			version -> new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 9) {
				@Override
				public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
					return new DataWatcherObjectVarInt(BlockIdRemappingHelper.remapToCombinedIdM12(version, object.getValue()));
				}
			},
			ProtocolVersionsHelper.RANGE__1_10__1_12_2
		);
		addRemapPerVersion(
			version -> new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 8) {
				@Override
				public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
					return new DataWatcherObjectVarInt(BlockIdRemappingHelper.remapToCombinedIdM12(version, object.getValue()));
				}
			},
			ProtocolVersionsHelper.ALL_1_9
		);
		addRemap(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 20) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectInt(BlockIdRemappingHelper.remapToCombinedIdM12(ProtocolVersion.MINECRAFT_1_8, object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		addRemapPerVersion(
			version -> new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 20) {
				@Override
				public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
					return new DataWatcherObjectInt(BlockIdRemappingHelper.remapToCombinedIdM16(version, object.getValue()));
				}
			},
			ProtocolVersionsHelper.BEFORE_1_8
		);
		addRemap(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Minecart.BLOCK_Y, PeMetaBase.MINECART_OFFSET), ProtocolVersion.MINECRAFT_PE);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, PeMetaBase.MINECART_DISPLAY), ProtocolVersion.MINECRAFT_PE);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.BLOCK_Y, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.BLOCK_Y, 9), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.BLOCK_Y, 21), ProtocolVersionsHelper.BEFORE_1_9);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 10), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 22), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
