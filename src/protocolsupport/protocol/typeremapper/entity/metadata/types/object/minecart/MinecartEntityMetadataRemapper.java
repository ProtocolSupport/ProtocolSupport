package protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataIndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class MinecartEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public static final MinecartEntityMetadataRemapper INSTANCE = new MinecartEntityMetadataRemapper();

	protected MinecartEntityMetadataRemapper() {
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_POWER, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_POWER, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_POWER, 5), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_POWER, 17), ProtocolVersionsHelper.DOWN_1_8);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_DIRECTION, 8), ProtocolVersionsHelper.UP_1_14);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Minecart.SHAKING_DIRECTION, 18), ProtocolVersionsHelper.DOWN_1_8);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.DAMAGE_TAKEN, 9), ProtocolVersionsHelper.UP_1_14);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1));
		addRemap(new NetworkEntityMetadataIndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.DOWN_1_5_2);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 10), ProtocolVersionsHelper.UP_1_14);
		addRemapPerVersion(version -> new NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 9) {
			final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
			final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectVarInt(BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, object.getValue()));
			}
		}, ProtocolVersionsHelper.ALL_1_13);
		addRemapPerVersion(
			version -> new NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 9) {
				final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				@Override
				public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
					return new NetworkEntityMetadataObjectVarInt(BlockRemappingHelper.remapPreFlatteningBlockDataM12(blockDataRemappingTable, object.getValue()));
				}
			},
			ProtocolVersionsHelper.RANGE__1_10__1_12_2
		);
		addRemapPerVersion(
			version -> new NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 8) {
				final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				@Override
				public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
					return new NetworkEntityMetadataObjectVarInt(BlockRemappingHelper.remapPreFlatteningBlockDataM12(blockDataRemappingTable, object.getValue()));
				}
			},
			ProtocolVersionsHelper.ALL_1_9
		);
		addRemap(new NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 20) {
			final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(ProtocolVersion.MINECRAFT_1_8);
			@Override
			public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
				return new NetworkEntityMetadataObjectInt(BlockRemappingHelper.remapPreFlatteningBlockDataM12(blockDataRemappingTable, object.getValue()));
			}
		}, ProtocolVersion.MINECRAFT_1_8);
		addRemapPerVersion(
			version -> new NetworkEntityMetadataIndexValueRemapper<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.Minecart.BLOCK, 20) {
				final ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				@Override
				public NetworkEntityMetadataObject<?> remapValue(NetworkEntityMetadataObjectVarInt object) {
					return new NetworkEntityMetadataObjectInt(BlockRemappingHelper.remapPreFlatteningBlockDataM16(blockDataRemappingTable, object.getValue()));
				}
			},
			ProtocolVersionsHelper.DOWN_1_7_10
		);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.BLOCK_Y, 11), ProtocolVersionsHelper.UP_1_14);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.BLOCK_Y, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.BLOCK_Y, 9), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNumberToInt(NetworkEntityMetadataObjectIndex.Minecart.BLOCK_Y, 21), ProtocolVersionsHelper.DOWN_1_8);

		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHOW_BLOCK, 12), ProtocolVersionsHelper.UP_1_14);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHOW_BLOCK, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new NetworkEntityMetadataIndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.Minecart.SHOW_BLOCK, 10), ProtocolVersionsHelper.ALL_1_9);
		addRemap(new NetworkEntityMetadataIndexValueRemapperBooleanToByte(NetworkEntityMetadataObjectIndex.Minecart.SHOW_BLOCK, 22), ProtocolVersionsHelper.DOWN_1_8);
	}

}
