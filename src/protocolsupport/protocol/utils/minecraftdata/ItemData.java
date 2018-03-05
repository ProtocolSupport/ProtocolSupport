package protocolsupport.protocol.utils.minecraftdata;

import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.protocol.utils.minecraftdata.BlockData.BlockDataEntry;
import protocolsupport.utils.JsonUtils;

public class ItemData {

	private static final HashMap<String, Integer> nameToId = new HashMap<>();

	static {
		read("items.json");
	}

	private static void read(String filename) {
		for (JsonElement element : MinecraftData.iterateJsonArrayResource(filename)) {
			JsonObject object = element.getAsJsonObject();
			Integer id = JsonUtils.getInt(object, "id");
			String name = JsonUtils.getString(object, "name");
			nameToId.put(name, id);
			nameToId.put(MinecraftData.addNamespacePrefix(name), id);
		}
	}

	public static Integer getIdByName(String name) {
		Integer runtimeId = nameToId.get(name);
		if (runtimeId == null) {
			BlockDataEntry entry = BlockData.getByName(name);
			if (entry != null) {
				runtimeId = entry.getRuntimeId();
			}
		}
		return runtimeId;
	}

}
