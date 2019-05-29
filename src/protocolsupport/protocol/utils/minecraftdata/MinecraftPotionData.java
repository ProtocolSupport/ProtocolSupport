package protocolsupport.protocol.utils.minecraftdata;

import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MinecraftPotionData {

	private static final Int2ObjectOpenHashMap<String> idToName = new Int2ObjectOpenHashMap<>();

	static {
		JsonObject rootObject = ResourceUtils.getAsJson(MinecraftData.getResourcePath("potions.json"));
		for (String potionidString : rootObject.keySet()) {
			idToName.put(Integer.parseInt(potionidString), JsonUtils.getString(rootObject, potionidString));
		}
	}

	public static String getNameById(int id) {
		return idToName.get(id);
	}

}
