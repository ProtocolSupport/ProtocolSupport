package protocolsupport.protocol.v_1_6.utils;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;

public class ChatEncoder {

	private static final Gson gson = new GsonBuilder().create();

	public static String encode(String message) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("text", message);
		return gson.toJson(jsonObject);
	}

}
