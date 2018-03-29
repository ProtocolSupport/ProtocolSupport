package protocolsupport.protocol.utils.minecraftdata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.utils.JsonUtils;

public class PotionData {

	private static final Int2ObjectOpenHashMap<String> idToName = new Int2ObjectOpenHashMap<>();

	static {
		for (JsonElement element : MinecraftData.iterateJsonArrayResource("potions.json")) {
			JsonObject object = element.getAsJsonObject();
			idToName.put(JsonUtils.getInt(object, "id"), MinecraftData.addNamespacePrefix(JsonUtils.getString(object, "name")));
		}
	}

	public static String getNameById(int id) {
		return idToName.get(id);
	}

}
