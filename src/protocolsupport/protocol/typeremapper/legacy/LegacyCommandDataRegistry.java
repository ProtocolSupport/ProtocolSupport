package protocolsupport.protocol.typeremapper.legacy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.legacy.LegacyCommandDataRegistry.LegacyCommandDataMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.command.CommandNodeArgumentProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentStringProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyCommandDataRegistry extends MappingRegistry<LegacyCommandDataMappingTable> {

	public static final LegacyCommandDataRegistry INSTANCE = new LegacyCommandDataRegistry();

	public LegacyCommandDataRegistry() {
		register(CommandNodeArgumentType.GAMEMODE, properties -> new CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18_2);
		register(CommandNodeArgumentType.HEIGHTMAP, properties -> new CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18_2);
		register(CommandNodeArgumentType.TEMPLATE_ROTATION, properties -> new CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18_2);
		register(CommandNodeArgumentType.TEMPLATE_MIRROR, properties -> new CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18_2);
		register(CommandNodeArgumentType.GAMEPROFILE, properties -> new CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18_2);
		register(CommandNodeArgumentType.UUID, properties -> new CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18_2);
		register(CommandNodeArgumentType.RESOURCE_OR_TAG, properties -> new CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18);
		register(CommandNodeArgumentType.RESOURCE_OR_TAG_KEY, properties -> new CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18_2);
		register(CommandNodeArgumentType.RESOURCE, properties -> new CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18);
		register(CommandNodeArgumentType.RESOURCE_KEY, properties -> new CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType.SINGLE_WORD), ProtocolVersionsHelper.DOWN_1_18_2);
	}

	protected <T extends CommandNodeArgumentProperties> void register(CommandNodeArgumentType type, Function<T, CommandNodeArgumentProperties> func, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			getTable(version).set(type, func);
		}
	}

	@Override
	protected LegacyCommandDataMappingTable createTable() {
		return new LegacyCommandDataMappingTable();
	}

	public static class LegacyCommandDataMappingTable extends MappingTable {

		protected final Map<CommandNodeArgumentType, Function<? extends CommandNodeArgumentProperties, CommandNodeArgumentProperties>> table = new HashMap<>();

		public <T extends CommandNodeArgumentProperties> void set(CommandNodeArgumentType type, Function<T, CommandNodeArgumentProperties> func) {
			table.put(type, func);
		}

		@SuppressWarnings("unchecked")
		public <T extends CommandNodeArgumentProperties> Function<T, CommandNodeArgumentProperties> get(CommandNodeArgumentType type) {
			return (Function<T, CommandNodeArgumentProperties>) table.getOrDefault(type, UnaryOperator.identity());
		}

	}

}
