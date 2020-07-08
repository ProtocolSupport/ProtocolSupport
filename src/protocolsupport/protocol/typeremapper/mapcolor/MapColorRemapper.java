package protocolsupport.protocol.typeremapper.mapcolor;

import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MapColorRemapper {

	public static final IntMappingRegistry<ArrayBasedIntMappingTable> REMAPPER = new IntMappingRegistry<ArrayBasedIntMappingTable>() {
		{
			//see http://minecraft.gamepedia.com/Map_item_format
			for (ModernMapColor color : ModernMapColor.values()) {
				if (color.getId() > ModernMapColor.Color143.getId()) {
					register(color.getId(), MapColorHelper.getSimilarModernColor(color, ModernMapColor.Color143.getId()).getId(), ProtocolVersionsHelper.DOWN_1_11_1);
				}
				register(color.getId(), MapColorHelper.getSimilarLegacyColor(color).getId(), ProtocolVersionsHelper.DOWN_1_6_4);
			}
		}
		@Override
		protected ArrayBasedIntMappingTable createTable() {
			return new ArrayBasedIntMappingTable(256);
		}
	};

}
