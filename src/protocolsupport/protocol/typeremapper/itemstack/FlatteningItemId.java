package protocolsupport.protocol.typeremapper.itemstack;

import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;

public class FlatteningItemId {

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> REGISTRY_TO_CLIENT = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(MinecraftData.ITEM_COUNT);
		}
	};

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> REGISTRY_FROM_CLIENT = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(MinecraftData.ITEM_COUNT);
		}
	};

}
