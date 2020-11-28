package protocolsupport.protocol.typeremapper.itemstack;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftItemData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyItemType {

	public static final ItemIdRemappingRegistry REGISTRY = new ItemIdRemappingRegistry();

	public static class ItemIdRemappingRegistry extends IntMappingRegistry<ArrayBasedIntMappingTable> {
		{
			applyDefaultRemaps();
		}
		public void applyDefaultRemaps() {
			JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("legacyitemtype.json"));
			for (String versionString : rootObject.keySet()) {
				JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
				ArrayBasedIntMappingTable table = getTable(ProtocolVersion.valueOf(versionString));
				for (String itemidString : entriesObject.keySet()) {
					table.set(Integer.parseInt(itemidString), JsonUtils.getInt(entriesObject, itemidString));
				}
			}
		}

		@Override
		protected ArrayBasedIntMappingTable createTable() {
			return new ArrayBasedIntMappingTable(MinecraftItemData.ITEM_COUNT);
		}

	}

}
