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
		return new JsonParser().parse(new InputStreamReader(getResource(name))).getAsJsonArray();
	}

	public static InputStream getResource(String name) {
		return Utils.getResource("data/" + name);
	}

	public static final int BLOCK_ID_MAX = 4096;
	public static final int BLOCK_DATA_MAX = 16;
	public static final int ITEM_ID_MAX = Short.MAX_VALUE;
	public static final int ITEM_DATA_MAX = (int) (Math.pow(2, 16) - 1);

	public static int getBlockStateFromIdAndData(int id, int data) {
		return (id << 4) | data;
	}

	public static int getBlockIdFromState(int blockstate) {
		return blockstate >> 4;
	}

	public static int getBlockDataFromState(int blockstate) {
		return blockstate & 0xF;
	}

	public static int getItemStateFromIdAndData(int id, int data) {
		return (id << 16) | data;
	}

	public static int getItemIdFromState(int itemstate) {
		return itemstate >>> 16;
	}

	public static int getItemDataFromState(int itemstate) {
		return itemstate & 0xFFFF;
	}

}
