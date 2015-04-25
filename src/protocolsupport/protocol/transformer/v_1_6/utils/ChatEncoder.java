package protocolsupport.protocol.transformer.v_1_6.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ChatEncoder {

	private static final Gson gson = new GsonBuilder().create();

	public static String encode(String message) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("text", message);
		return gson.toJson(jsonObject);
	}

}
