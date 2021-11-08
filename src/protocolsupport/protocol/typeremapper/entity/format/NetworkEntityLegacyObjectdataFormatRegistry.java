package protocolsupport.protocol.typeremapper.entity.format;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

import javax.annotation.Nonnull;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyObjectdataFormatRegistry.NetworkEntityLegacyObjectdataFormatTable;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityLegacyObjectdataFormatRegistry extends MappingRegistry<NetworkEntityLegacyObjectdataFormatTable> {

	public static final NetworkEntityLegacyObjectdataFormatRegistry INSTANCE = new NetworkEntityLegacyObjectdataFormatRegistry();

	protected NetworkEntityLegacyObjectdataFormatRegistry() {
		register(
			NetworkEntityType.FALLING_OBJECT,
			(Function<ProtocolVersion, IntUnaryOperator>) version -> {
				FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
				return flatteningBlockDataTable::getId;
			},
			ProtocolVersionsHelper.ALL
		);
		register(NetworkEntityType.FALLING_OBJECT, (IntUnaryOperator) PreFlatteningBlockIdData::getCombinedIdM12, ProtocolVersionsHelper.RANGE__1_8__1_12_2);
		register(NetworkEntityType.FALLING_OBJECT, (IntUnaryOperator) PreFlatteningBlockIdData::getCombinedIdM16, ProtocolVersionsHelper.DOWN_1_7_10);

		IntUnaryOperator entityIdOffset = entityId -> entityId + 1;
		register(NetworkEntityType.ARROW, entityIdOffset, ProtocolVersionsHelper.RANGE__1_9__1_13_2);
		register(NetworkEntityType.TIPPED_ARROW, entityIdOffset, ProtocolVersionsHelper.RANGE__1_9__1_13_2);
		register(NetworkEntityType.SPECTRAL_ARROW, entityIdOffset, ProtocolVersionsHelper.RANGE__1_9__1_13_2);

		register(NetworkEntityType.MINECART, (IntUnaryOperator) i -> 0, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityType.MINECART_CHEST, (IntUnaryOperator) i -> 1, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityType.MINECART_FURNACE, (IntUnaryOperator) i -> 2, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityType.MINECART_TNT, (IntUnaryOperator) i -> 3, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityType.MINECART_MOB_SPAWNER, (IntUnaryOperator) i -> 4, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityType.MINECART_HOPPER, (IntUnaryOperator) i -> 5, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityType.MINECART_COMMAND, (IntUnaryOperator) i -> 6, ProtocolVersionsHelper.DOWN_1_8);
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
	protected NetworkEntityLegacyObjectdataFormatTable createTable() {
		return new NetworkEntityLegacyObjectdataFormatTable();
	}

	public static class NetworkEntityLegacyObjectdataFormatTable extends MappingTable {

		protected final Map<NetworkEntityType, IntUnaryOperator> table = new EnumMap<>(NetworkEntityType.class);

		public void set(@Nonnull NetworkEntityType type, @Nonnull IntUnaryOperator operator) {
			table.put(type, operator);
		}

		public @Nonnull IntUnaryOperator get(@Nonnull NetworkEntityType entityType) {
			return table.getOrDefault(entityType, i -> i);
		}

	}

}
