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

	static {
		JsonObject rootObject = ResourceUtils.getAsJson(MappingsData.getResourcePath("flatteningitem.json"));
		for (String versionString : rootObject.keySet()) {
			JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
			ArrayBasedIdRemappingTable tableToClient = REGISTRY_TO_CLIENT.getTable(ProtocolVersion.valueOf(versionString));
			ArrayBasedIdRemappingTable tableFromClient = REGISTRY_FROM_CLIENT.getTable(ProtocolVersion.valueOf(versionString));
			for (String itemidString : entriesObject.keySet()) {
				int modernId = Integer.parseInt(itemidString);
				int legacyId = JsonUtils.getInt(entriesObject, itemidString);
				tableToClient.setRemap(modernId, legacyId);
				tableFromClient.setRemap(legacyId, modernId);
			}
		}
	}

}
