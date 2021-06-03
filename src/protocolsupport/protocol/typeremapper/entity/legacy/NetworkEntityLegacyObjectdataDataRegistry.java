package protocolsupport.protocol.typeremapper.entity.legacy;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

import javax.annotation.Nonnull;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyObjectdataDataRegistry.NetworkEntityLegacyObjectdataDataTable;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.types.BlockDirection;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityLegacyObjectdataDataRegistry extends MappingRegistry<NetworkEntityLegacyObjectdataDataTable> {

	public static final NetworkEntityLegacyObjectdataDataRegistry INSTANCE = new NetworkEntityLegacyObjectdataDataRegistry();

	protected NetworkEntityLegacyObjectdataDataRegistry() {
		register(
			NetworkEntityType.FALLING_OBJECT,
			(Function<ProtocolVersion, IntUnaryOperator>) version -> {
				ArrayBasedIntMappingTable blockLegacyDataTable = BlockDataLegacyDataRegistry.INSTANCE.getTable(version);
				return blockLegacyDataTable::get;
			},
			ProtocolVersionsHelper.ALL
		);

		register(
			NetworkEntityType.ITEM_FRAME,
			(Function<ProtocolVersion, IntUnaryOperator>) version -> (blockdata -> BlockDirection.CONSTANT_LOOKUP.getByOrdinal(blockdata).get2DId()),
			ProtocolVersionsHelper.DOWN_1_12_2
		);
	}

	protected void register(@Nonnull NetworkEntityType type, @Nonnull IntUnaryOperator operator, @Nonnull ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			getTable(version).set(type, operator);
		}
	}

	protected void register(@Nonnull NetworkEntityType type, @Nonnull Function<ProtocolVersion, IntUnaryOperator> operatorSupplier, @Nonnull ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			getTable(version).set(type, operatorSupplier.apply(version));
		}
	}

	@Override
	protected NetworkEntityLegacyObjectdataDataTable createTable() {
		return new NetworkEntityLegacyObjectdataDataTable();
	}

	public static class NetworkEntityLegacyObjectdataDataTable extends MappingTable {

		protected final Map<NetworkEntityType, IntUnaryOperator> table = new EnumMap<>(NetworkEntityType.class);

		public void set(@Nonnull NetworkEntityType type, @Nonnull IntUnaryOperator operator) {
			table.put(type, operator);
		}

		public @Nonnull IntUnaryOperator get(@Nonnull NetworkEntityType entityType) {
			return table.getOrDefault(entityType, i -> i);
		}

	}

}
