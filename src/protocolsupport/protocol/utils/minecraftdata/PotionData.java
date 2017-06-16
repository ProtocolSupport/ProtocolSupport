package protocolsupport.protocol.utils.minecraftdata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.utils.JsonUtils;

public class PotionData {

	private static final TIntObjectHashMap<String> idToName = new TIntObjectHashMap<>();

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
