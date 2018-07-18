package protocolsupport.protocol.utils.minecraftdata;

import java.io.BufferedReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import protocolsupport.utils.Utils;

public class MinecraftData {

	public static Iterable<JsonElement> iterateJsonArrayResource(String name) {
		return new JsonParser().parse(getResource(name)).getAsJsonArray();
	}

	public static BufferedReader getResource(String name) {
		return Utils.getResource("data/" + name);
	}

	public static final int ID_MAX = 65535;

}
