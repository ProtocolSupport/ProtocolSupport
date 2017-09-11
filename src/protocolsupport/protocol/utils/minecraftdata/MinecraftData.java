package protocolsupport.protocol.utils.minecraftdata;

import java.io.BufferedReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import protocolsupport.utils.Utils;

public class MinecraftData {

	public static String addNamespacePrefix(String val) {
		return "minecraft:" + val;
	}

	public static Iterable<JsonElement> iterateJsonArrayResource(String name) {
		return new JsonParser().parse(getResource(name)).getAsJsonArray();
	}

	public static BufferedReader getResource(String name) {
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

}
