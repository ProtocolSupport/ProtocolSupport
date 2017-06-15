package protocolsupport.protocol.utils.minecraftdata;

import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.utils.JsonUtils;

public class ItemData {

	private static final HashMap<String, Integer> nameToId = new HashMap<>();

	static {
		read("blocks.json");
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
		return nameToId.get(name);
	}

}
