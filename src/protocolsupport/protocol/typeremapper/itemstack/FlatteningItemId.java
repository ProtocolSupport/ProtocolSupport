package protocolsupport.protocol.typeremapper.itemstack;

import java.util.Arrays;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.ItemMaterialLookup;
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

	protected static void load(ProtocolVersion version) {
		JsonObject itemsData = ResourceUtils.getAsJson(MappingsData.getFlatteningResoucePath(version, "items.json"));
		if (itemsData != null) {
			ArrayBasedIdRemappingTable tableToClient = REGISTRY_TO_CLIENT.getTable(version);
			ArrayBasedIdRemappingTable tableFromClient = REGISTRY_FROM_CLIENT.getTable(version);
			for (Entry<String, JsonElement> entry : itemsData.entrySet()) {
				int currentId = MaterialAPI.getItemNetworkId(ItemMaterialLookup.getByKey(entry.getKey()));
				int legacyId = JsonUtils.getInt(entry.getValue().getAsJsonObject(), "protocol_id");
				tableToClient.setRemap(currentId, legacyId);
				tableFromClient.setRemap(legacyId, currentId);
			}
		}
	}

	static {
		Arrays.stream(ProtocolVersion.getAllSupported()).forEach(FlatteningItemId::load);
	}

}
