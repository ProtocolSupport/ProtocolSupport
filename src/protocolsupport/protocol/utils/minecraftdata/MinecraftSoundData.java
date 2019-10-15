package protocolsupport.protocol.utils.minecraftdata;

import com.google.gson.JsonObject;

import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MinecraftSoundData {

	protected static final String[] idToName = new String[1024];

	protected static void register(int id, String name) {
		idToName[id] = name;
	}

	static {
		JsonObject rootObject = ResourceUtils.getAsJson(MinecraftData.getResourcePath("sounds.json"));
		for (String soundidString : rootObject.keySet()) {
			register(Integer.parseInt(soundidString), JsonUtils.getString(rootObject, soundidString));
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
