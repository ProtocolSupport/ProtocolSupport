package protocolsupport.protocol.utils.minecraftdata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.utils.JsonUtils;

public class SoundData {

	private static final TIntObjectHashMap<String> idToName = new TIntObjectHashMap<>();

	static {
		for (JsonElement element : MinecraftData.iterateJsonArrayResource("sounds.json")) {
			JsonObject object = element.getAsJsonObject();
			idToName.put(JsonUtils.getInt(object, "id"), JsonUtils.getString(object, "name"));
		}
	}

	public static String getNameById(int id) {
		return idToName.get(id);
	}

}
