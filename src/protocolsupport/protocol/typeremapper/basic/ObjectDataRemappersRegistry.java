package protocolsupport.protocol.typeremapper.basic;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.IntUnaryOperator;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.types.BlockDirection;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

//TODO: move the position remappers here, so it will be the shared registry for remapping all entity object data
public class ObjectDataRemappersRegistry {

	public static final RemappingRegistry<ObjectDataRemappingTable> REGISTRY = new RemappingRegistry<ObjectDataRemappingTable>() {
		{
			Arrays.stream(ProtocolVersionsHelper.BEFORE_1_13)
			.forEach(version -> registerRemapEntry(
				NetworkEntityType.ITEM_FRAME,
				blockdata -> BlockDirection.CONSTANT_LOOKUP.getByOrdinal(blockdata).get2DId(),
				version
			));
			Arrays.stream(ProtocolVersionsHelper.RANGE__1_8__1_12_2)
			.forEach(version -> {
				ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				registerRemapEntry(
					NetworkEntityType.FALLING_OBJECT,
					blockdata -> BlockRemappingHelper.remapBlockDataM12(blockDataRemappingTable, blockdata),
					version
				);
			});
			registerRemapEntry(NetworkEntityType.ARROW, entityId -> entityId - 1, ProtocolVersionsHelper.BEFORE_1_9);
			Arrays.stream(ProtocolVersionsHelper.BEFORE_1_8)
			.forEach(version -> {
				ArrayBasedIdRemappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
				registerRemapEntry(
					NetworkEntityType.FALLING_OBJECT,
					blockdata -> BlockRemappingHelper.remapBlockDataM16(blockDataRemappingTable, blockdata),
					version
				);
			});
		}
		protected void registerRemapEntry(NetworkEntityType type, IntUnaryOperator operator, ProtocolVersion... versions) {
			Arrays.stream(versions).forEach(version -> getTable(version).setRemap(type, operator));
		}
		@Override
		protected ObjectDataRemappingTable createTable() {
			return new ObjectDataRemappingTable();
		}
	};

	public static class ObjectDataRemappingTable extends RemappingTable {

		protected final Map<NetworkEntityType, IntUnaryOperator> table = new EnumMap<>(NetworkEntityType.class);

		public void setRemap(NetworkEntityType type, IntUnaryOperator operator) {
			table.put(type, operator);
		}

		public IntUnaryOperator getRemap(NetworkEntityType entityType) {
			return table.getOrDefault(entityType, i -> i);
		}

	}

}
