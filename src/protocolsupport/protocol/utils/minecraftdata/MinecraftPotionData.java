package protocolsupport.protocol.utils.minecraftdata;

import com.google.gson.JsonObject;

import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MinecraftPotionData {

	public static final int ID_NONE = 0;
	public static final int ID_MIN = 1;
	public static final int ID_MAX = 127;

	protected static final String[] idToName = new String[ID_MAX + 1];

	protected static void register(int id, String name) {
		idToName[id] = name;
	}

	static {
		JsonObject rootObject = ResourceUtils.getAsJsonObject(MinecraftDataResourceUtils.getResourcePath("potions.json"));
		for (String potionidString : rootObject.keySet()) {
			register(Integer.parseInt(potionidString), JsonUtils.getString(rootObject, potionidString));
		}
	}

	public static String getNameById(int id) {
		if ((id >= 0) && (id < idToName.length)) {
			return idToName[id];
		} else {
			return null;
		}
	}

}
