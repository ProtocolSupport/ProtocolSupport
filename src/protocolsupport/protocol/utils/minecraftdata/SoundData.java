package protocolsupport.protocol.utils.minecraftdata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class SoundData {

	private static final Int2ObjectOpenHashMap<String> idToName = new Int2ObjectOpenHashMap<>();

	static {
		for (JsonElement element : ResourceUtils.getAsIterableJson(MinecraftData.getResourcePath("sounds.json"))) {
			JsonObject object = element.getAsJsonObject();
			idToName.put(JsonUtils.getInt(object, "id"), JsonUtils.getString(object, "name"));
		}
	}

	public static String getNameById(int id) {
		return idToName.get(id);
	}

}
