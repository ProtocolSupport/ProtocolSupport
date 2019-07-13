package protocolsupport.protocol.typeremapper.basic;

import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.EnumRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.EnumRemappingTable;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class GenericIdRemapper {

	public static final EnumRemappingRegistry<WindowType, EnumRemappingTable<WindowType>> INVENTORY = new EnumRemappingRegistry<WindowType, EnumRemappingTable<WindowType>>() {
		{
			registerRemapEntry(WindowType.ENCHANTMENT, WindowType.HOPPER, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.BLAST_FURNACE, WindowType.FURNACE, ProtocolVersionsHelper.BEFORE_1_14);
			registerRemapEntry(WindowType.SMOKER, WindowType.FURNACE, ProtocolVersionsHelper.BEFORE_1_14);
			registerRemapEntry(WindowType.SHULKER_BOX, WindowType.GENERIC_9X4, ProtocolVersionsHelper.concat(ProtocolVersionsHelper.BEFORE_1_11, ProtocolVersionsHelper.ALL_PE));
		}
		@Override
		protected EnumRemappingTable<WindowType> createTable() {
			return new EnumRemappingTable<>(WindowType.class);
		}
	};

}
