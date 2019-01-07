package protocolsupport.protocol.typeremapper.mapcolor;

import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MapColorRemapper {

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> REMAPPER = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		{
			//see http://minecraft.gamepedia.com/Map_item_format
			for (ModernMapColor color : ModernMapColor.values()) {
				if (color.getId() > ModernMapColor.Color143.getId()) {
					registerRemapEntry(color.getId(), MapColorHelper.getSimilarModernColor(color, ModernMapColor.Color143.getId()).getId(), ProtocolVersionsHelper.BEFORE_1_12);
				}
				registerRemapEntry(color.getId(), MapColorHelper.getSimilarLegacyColor(color).getId(), ProtocolVersionsHelper.BEFORE_1_7);
			}
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(256);
		}
	};

}
