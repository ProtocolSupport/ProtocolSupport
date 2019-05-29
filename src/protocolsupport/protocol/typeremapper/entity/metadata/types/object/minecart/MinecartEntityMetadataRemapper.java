package protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata.PeMetaBase;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.value.IndexValueRemapperNumberToSVarInt;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class MinecartEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public static final MinecartEntityMetadataRemapper INSTANCE = new MinecartEntityMetadataRemapper();

	public MinecartEntityMetadataRemapper() {
		//TODO Block Remapping for PE/
		//addRemap(new IndexValueRemapperNumberToSVarInt(DataWatcherObjectIndex.Minecart.BLOCK, PeMetaBase.MINECART_BLOCK), ProtocolVersionsHelper.ALL_PE),

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_POWER, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_POWER, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_POWER, 5), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_POWER, 17), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_DIRECTION, 8), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_DIRECTION, 18), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.DAMAGE_TAKEN, 9), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.BEFORE_1_6);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 10), ProtocolVersionsHelper.UP_1_14);
		addRemapPerVersion(version -> new IndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 9) {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
			final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectVarInt(BlockRemappingHelper.remapFBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_13_AND_PE);

		addRemapPerVersion(
			version -> new IndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 9) {
				final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				@Override
				public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
					return new NetworkEntityMetadataObjectVarInt(BlockRemappingHelper.remapBlockDataM12(blockDataRemappingTable, object.getValue()));
				}
			},
			ProtocolVersionsHelper.RANGE__1_10__1_12_2
		);
		addRemapPerVersion(
			version -> new IndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 8) {
				final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				@Override
				public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
					return new NetworkEntityMetadataObjectVarInt(BlockRemappingHelper.remapBlockDataM12(blockDataRemappingTable, object.getValue()));
				}
			},
			ProtocolVersionsHelper.ALL_1_9
		);
		addRemap(new IndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 20) {
			final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(ProtocolVersion.MINECRAFT_1_8);
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectInt(BlockRemappingHelper.remapBlockDataM12(blockDataRemappingTable, object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		addRemapPerVersion(
			version -> new IndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 20) {
				final ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				@Override
				public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
					return new NetworkEntityMetadataObjectInt(BlockRemappingHelper.remapBlockDataM16(blockDataRemappingTable, object.getValue()));
				}
			},
			ProtocolVersionsHelper.BEFORE_1_8
		);
		addRemap(new IndexValueRemapperNumberToSVarInt(NetworkEntityMetadataObjectIndex.Minecart.BLOCK_Y, PeMetaBase.MINECART_OFFSET), ProtocolVersionsHelper.ALL_PE);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.Minecart.SHOW_BLOCK, PeMetaBase.MINECART_DISPLAY), ProtocolVersionsHelper.ALL_PE);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.BLOCK_Y, 11), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.BLOCK_Y, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.BLOCK_Y, 9), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Minecart.BLOCK_Y, 21), ProtocolVersionsHelper.BEFORE_1_9);

		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHOW_BLOCK, 12), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHOW_BLOCK, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHOW_BLOCK, 10), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new IndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.Minecart.SHOW_BLOCK, 22), ProtocolVersionsHelper.BEFORE_1_9);
	}

}
