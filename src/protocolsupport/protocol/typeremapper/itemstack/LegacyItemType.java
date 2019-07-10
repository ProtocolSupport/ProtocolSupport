package protocolsupport.protocol.typeremapper.itemstack;

import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyItemType {

	public static final ItemIdRemappingRegistry REGISTRY = new ItemIdRemappingRegistry();

	public static class ItemIdRemappingRegistry extends IdRemappingRegistry<ArrayBasedIdRemappingTable> {
		{
			applyDefaultRemaps();
		}
		public void applyDefaultRemaps() {
			JsonObject rootObject = ResourceUtils.getAsJson(MappingsData.getResourcePath("legacyitemtype.json"));
			for (String versionString : rootObject.keySet()) {
				JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
				ArrayBasedIdRemappingTable table = getTable(ProtocolVersion.valueOf(versionString));
				for (String itemidString : entriesObject.keySet()) {
					table.setRemap(Integer.parseInt(itemidString), JsonUtils.getInt(entriesObject, itemidString));
				}
			}
		}

		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(MinecraftData.ITEM_COUNT);
		}

	}

}
