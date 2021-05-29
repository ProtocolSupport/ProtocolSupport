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
public class FlatteningItemId {

	private FlatteningItemId() {
	}

	public static final IntMappingRegistry<ArrayBasedIntMappingTable> REGISTRY_TO_CLIENT = new IntMappingRegistry<ArrayBasedIntMappingTable>() {
		@Override
		protected ArrayBasedIntMappingTable createTable() {
			return new ArrayBasedIntMappingTable(MinecraftItemData.ITEM_COUNT);
		}
	};

	public static final IntMappingRegistry<ArrayBasedIntMappingTable> REGISTRY_FROM_CLIENT = new IntMappingRegistry<ArrayBasedIntMappingTable>() {
		@Override
		protected ArrayBasedIntMappingTable createTable() {
			return new ArrayBasedIntMappingTable(MinecraftItemData.ITEM_COUNT);
		}
	};

	static {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("flatteningitem.json"));
		for (String versionString : rootObject.keySet()) {
			JsonObject entriesObject = rootObject.get(versionString).getAsJsonObject();
			ArrayBasedIntMappingTable tableToClient = REGISTRY_TO_CLIENT.getTable(ProtocolVersion.valueOf(versionString));
			ArrayBasedIntMappingTable tableFromClient = REGISTRY_FROM_CLIENT.getTable(ProtocolVersion.valueOf(versionString));
			for (String itemidString : entriesObject.keySet()) {
				int modernId = Integer.parseInt(itemidString);
				int legacyId = JsonUtils.getInt(entriesObject, itemidString);
				tableToClient.set(modernId, legacyId);
				tableFromClient.set(legacyId, modernId);
			}
		}
	}

}
