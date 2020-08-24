package protocolsupport.protocol.typeremapper.basic;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.IntUnaryOperator;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.BlockDirection;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

//TODO: combine with entity remapping
@Preload
public class ObjectDataRemappersRegistry {

	public static final MappingRegistry<ObjectDataRemappingTable> REGISTRY = new MappingRegistry<ObjectDataRemappingTable>() {

		{
			Arrays.stream(ProtocolVersionsHelper.DOWN_1_12_2)
			.forEach(version -> registerRemapEntry(
				NetworkEntityType.ITEM_FRAME,
				blockdata -> BlockDirection.CONSTANT_LOOKUP.getByOrdinal(blockdata).get2DId(),
				version
			));

			Arrays.stream(ProtocolVersionsHelper.UP_1_13)
			.forEach(version -> {
				ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);
				registerRemapEntry(
					NetworkEntityType.FALLING_OBJECT,
					blockdata -> BlockRemappingHelper.remapFlatteningBlockDataId(blockDataRemappingTable, flatteningBlockDataTable, blockdata),
					version
				);
			});
			Arrays.stream(ProtocolVersionsHelper.RANGE__1_8__1_12_2)
			.forEach(version -> {
				ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				registerRemapEntry(
					NetworkEntityType.FALLING_OBJECT,
					blockdata -> BlockRemappingHelper.remapPreFlatteningBlockDataM12(blockDataRemappingTable, blockdata),
					version
				);
			});
			Arrays.stream(ProtocolVersionsHelper.DOWN_1_7_10)
			.forEach(version -> {
				ArrayBasedIntMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				registerRemapEntry(
					NetworkEntityType.FALLING_OBJECT,
					blockdata -> BlockRemappingHelper.remapPreFlatteningBlockDataM16(blockDataRemappingTable, blockdata),
					version
				);
			});

			registerRemapEntry(NetworkEntityType.ARROW, entityId -> entityId - 1, ProtocolVersionsHelper.DOWN_1_8);

			registerRemapEntry(NetworkEntityType.MINECART, i -> 0, ProtocolVersionsHelper.DOWN_1_8);
			registerRemapEntry(NetworkEntityType.MINECART_CHEST, i -> 1, ProtocolVersionsHelper.DOWN_1_8);
			registerRemapEntry(NetworkEntityType.MINECART_FURNACE, i -> 2, ProtocolVersionsHelper.DOWN_1_8);
			registerRemapEntry(NetworkEntityType.MINECART_TNT, i -> 3, ProtocolVersionsHelper.DOWN_1_8);
			registerRemapEntry(NetworkEntityType.MINECART_MOB_SPAWNER, i -> 4, ProtocolVersionsHelper.DOWN_1_8);
			registerRemapEntry(NetworkEntityType.MINECART_HOPPER, i -> 5, ProtocolVersionsHelper.DOWN_1_8);
			registerRemapEntry(NetworkEntityType.MINECART_COMMAND, i -> 6, ProtocolVersionsHelper.DOWN_1_8);
		}

		protected void registerRemapEntry(NetworkEntityType type, IntUnaryOperator operator, ProtocolVersion... versions) {
			Arrays.stream(versions).forEach(version -> getTable(version).setRemap(type, operator));
		}

		@Override
		protected ObjectDataRemappingTable createTable() {
			return new ObjectDataRemappingTable();
		}

	};

	public static class ObjectDataRemappingTable extends MappingTable {

		protected final Map<NetworkEntityType, IntUnaryOperator> table = new EnumMap<>(NetworkEntityType.class);

		public void setRemap(NetworkEntityType type, IntUnaryOperator operator) {
			table.put(type, operator);
		}

		public IntUnaryOperator getRemap(NetworkEntityType entityType) {
			return table.getOrDefault(entityType, i -> i);
		}

	}

}
