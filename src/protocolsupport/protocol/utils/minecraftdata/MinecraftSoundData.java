package protocolsupport.protocol.utils.minecraftdata;

import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MinecraftSoundData {

	private static final Int2ObjectOpenHashMap<String> idToName = new Int2ObjectOpenHashMap<>();

	static {
		JsonObject rootObject = ResourceUtils.getAsJson(MinecraftData.getResourcePath("sounds.json"));
		for (String soundidString : rootObject.keySet()) {
			idToName.put(Integer.parseInt(soundidString), JsonUtils.getString(rootObject, soundidString));
		}
	}

	public static String getNameById(int id) {
		return idToName.get(id);
	}

}
