package protocolsupport.protocol.typeremapper.basic;

import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.EnumRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.EnumRemappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupportbuildprocessor.Preload;

@Preload
public class GenericIdRemapper {

	public static final EnumRemappingRegistry<WindowType, EnumRemappingTable<WindowType>> INVENTORY = new EnumRemappingRegistry<WindowType, EnumRemappingTable<WindowType>>() {
		{
			registerRemapEntry(WindowType.SHULKER, WindowType.CHEST, ProtocolVersionsHelper.concat(ProtocolVersionsHelper.BEFORE_1_11, ProtocolVersionsHelper.ALL_PE));
			registerRemapEntry(WindowType.DROPPER, WindowType.DISPENSER, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(WindowType.ENCHANT, WindowType.HOPPER, ProtocolVersionsHelper.ALL_PE);
		}
		@Override
		protected EnumRemappingTable<WindowType> createTable() {
			return new EnumRemappingTable<>(WindowType.class);
		}
	};

}
