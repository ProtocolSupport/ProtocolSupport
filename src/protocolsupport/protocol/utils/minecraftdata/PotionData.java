package protocolsupport.protocol.utils.minecraftdata;

import org.bukkit.NamespacedKey;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.Utils;

public class PotionData {

	private static final Int2ObjectOpenHashMap<String> idToName = new Int2ObjectOpenHashMap<>();

	static {
		for (JsonElement element : Utils.iterateJsonArrayResource(MinecraftData.getResourcePath("potions.json"))) {
			JsonObject object = element.getAsJsonObject();
			idToName.put(JsonUtils.getInt(object, "id"), NamespacedKey.minecraft(JsonUtils.getString(object, "name")).toString());
		}
	}

	public static String getNameById(int id) {
		return idToName.get(id);
	}

}
