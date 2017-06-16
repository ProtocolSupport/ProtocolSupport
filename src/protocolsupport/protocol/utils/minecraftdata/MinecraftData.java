package protocolsupport.protocol.utils.minecraftdata;

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

	public static final int BLOCK_ID_MAX = 4096;
	public static final int BLOCK_DATA_MAX = 16;

	public static int getBlockStateFromIdAndData(int id, int data) {
		return (id << 4) | data;
	}

	public static int getBlockIdFromState(int blockstate) {
		return blockstate >> 4;
	}

	public static int getBlockDataFromState(int blockdata) {
		return blockdata & 0xF;
	}

}
