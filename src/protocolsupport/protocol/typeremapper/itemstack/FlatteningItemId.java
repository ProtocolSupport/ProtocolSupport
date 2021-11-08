package protocolsupport.protocol.typeremapper.itemstack;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftItemData;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class FlatteningItemId {

	private FlatteningItemId() {
	}

	public static final IntMappingRegistry<ArrayBasedIntMappingTable> REGISTRY_TO_CLIENT = new IntMappingRegistry<>() {
		@Override
		protected ArrayBasedIntMappingTable createTable() {
			return new ArrayBasedIntMappingTable(MinecraftItemData.ITEM_COUNT);
		}
	};

	public static final IntMappingRegistry<ArrayBasedIntMappingTable> REGISTRY_FROM_CLIENT = new IntMappingRegistry<>() {
		@Override
		protected ArrayBasedIntMappingTable createTable() {
			return new ArrayBasedIntMappingTable(MinecraftItemData.ITEM_COUNT);
		}
	};

	static {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("flatteningitem.json"));
		for (Entry<String, JsonElement> rootEntry : rootObject.entrySet()) {
			ArrayBasedIntMappingTable tableToClient = REGISTRY_TO_CLIENT.getTable(ProtocolVersion.valueOf(rootEntry.getKey()));
			ArrayBasedIntMappingTable tableFromClient = REGISTRY_FROM_CLIENT.getTable(ProtocolVersion.valueOf(rootEntry.getKey()));
			for (Entry<String, JsonElement> itemidEntry : rootEntry.getValue().getAsJsonObject().entrySet()) {
				int modernId = Integer.parseInt(itemidEntry.getKey());
				int legacyId = itemidEntry.getValue().getAsInt();
				tableToClient.set(modernId, legacyId);
				tableFromClient.set(legacyId, modernId);
			}
		}
	}

}
