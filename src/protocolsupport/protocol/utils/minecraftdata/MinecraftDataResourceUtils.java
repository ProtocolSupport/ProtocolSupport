package protocolsupport.protocol.utils.minecraftdata;

import com.google.gson.JsonObject;

import protocolsupport.utils.ResourceUtils;

public class MinecraftDataResourceUtils {

	public static String getResourcePath(String name) {
		return ("data/" + name);
	}

	public static JsonObject getResourceAsJsonObject(String name) {
		return ResourceUtils.getAsJson(getResourcePath(name));
	}

}
