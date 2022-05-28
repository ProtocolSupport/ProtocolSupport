package protocolsupport.protocol.typeremapper.legacy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.legacy.LegacyCommandDataRegistry.LegacyCommandDataMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.command.CommandNodeProperties;
import protocolsupport.protocol.types.command.CommandNodeResourceOrTagProperties;
import protocolsupport.protocol.types.command.CommandNodeResourceProperties;
import protocolsupport.protocol.types.command.CommandNodeStringProperties;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyCommandDataRegistry extends MappingRegistry<LegacyCommandDataMappingTable> {

	public static final LegacyCommandDataRegistry INSTANCE = new LegacyCommandDataRegistry();

	public LegacyCommandDataRegistry() {
		register(CommandNodeResourceOrTagProperties.class, properties -> new CommandNodeStringProperties(CommandNodeStringProperties.Type.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18);
		register(CommandNodeResourceProperties.class, properties -> new CommandNodeStringProperties(CommandNodeStringProperties.Type.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18);
	}

	protected <T extends CommandNodeProperties> void register(Class<T> clazz, Function<T, CommandNodeProperties> func, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			getTable(version).set(clazz, func);
		}
	}

	@Override
	protected LegacyCommandDataMappingTable createTable() {
		return new LegacyCommandDataMappingTable();
	}

	public static class LegacyCommandDataMappingTable extends MappingTable {

		protected final Map<Class<? extends CommandNodeProperties>, Function<? extends CommandNodeProperties, CommandNodeProperties>> table = new HashMap<>();

		public <T extends CommandNodeProperties> void set(Class<T> clazz, Function<T, CommandNodeProperties> func) {
			table.put(clazz, func);
		}

		@SuppressWarnings("unchecked")
		public <T extends CommandNodeProperties> Function<T, CommandNodeProperties> get(Class<? extends T> clazz) {
			return (Function<T, CommandNodeProperties>) table.getOrDefault(clazz, UnaryOperator.identity());
		}

	}

}
