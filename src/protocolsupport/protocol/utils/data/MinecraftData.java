package protocolsupport.protocol.utils.data;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import protocolsupport.utils.Utils;

public class MinecraftData {

	public static final String NAMESPACE_PREFIX = "minecraft:";

	public static String addNamespacePrefix(String val) {
		return NAMESPACE_PREFIX + val;
	}

	public static Iterable<JsonElement> iterateJsonArrayResource(String name) {
		return new JsonParser().parse(new InputStreamReader(getDataResource(name))).getAsJsonArray();
	}

	public static InputStream getDataResource(String name) {
		return Utils.getResource("data/" + name);
	}

}
