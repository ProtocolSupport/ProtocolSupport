package protocolsupport.protocol.utils.minecraftdata;

import java.util.List;

import org.bukkit.Sound;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gnu.trove.map.hash.TIntObjectHashMap;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;

public class SoundData {

	private static final TIntObjectHashMap<String> idToName = new TIntObjectHashMap<>();

	static {
		for (JsonElement element : MinecraftData.iterateJsonArrayResource("sounds.json")) {
			JsonObject object = element.getAsJsonObject();
			idToName.put(JsonUtils.getInt(object, "id"), JsonUtils.getString(object, "name"));
		}
	}
	
	public static List<String> getSounds() {
		return (List<String>) idToName.valueCollection();
	}

	public static String getNameById(int id) {
		return idToName.get(id);
	}
	
	public static int getIdByName(String name) {
		return Utils.TIntObjectHashMapGetKeyFromValue(idToName, name);
	}
	
	public static String getNameBySound(Sound sound) {
		return ServerPlatform.get().getMiscUtils().getSoundName(sound);
	}
	
	public static int getIdBySound(Sound sound) {
		return getIdByName(getNameBySound(sound));
	}

}
