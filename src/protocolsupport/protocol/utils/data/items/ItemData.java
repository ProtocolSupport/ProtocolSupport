package protocolsupport.protocol.utils.data.items;

import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import protocolsupport.protocol.utils.data.MinecraftData;
import protocolsupport.utils.JsonUtils;

public class ItemData {

	private static final HashMap<String, Integer> nameToId = new HashMap<>();

	public static void init() {
	}

	static {
		read("blocks.json");
		read("items.json");
	}

	private static void read(String filename) {
		JsonElement root = new JsonParser().parse(new InputStreamReader(MinecraftData.getDataResouce(filename)));
		for (JsonElement element : JsonUtils.getAsJsonArray(root, "root element")) {
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
