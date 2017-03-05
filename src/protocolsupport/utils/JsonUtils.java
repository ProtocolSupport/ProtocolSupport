package protocolsupport.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {

	public static void setIfNotNull(JsonObject jsonElement, String name, Boolean value) {
		if (value != null) {
			jsonElement.addProperty(name, value);
		}
	}

	public static boolean hasObject(JsonObject jsonObject, String name) {
		return (jsonObject != null) && (jsonObject.get(name) != null);
	}

	public static boolean isJsonArray(JsonObject jsonObject, String name) {
		return hasObject(jsonObject, name) && jsonObject.get(name).isJsonArray();
	}

	public static JsonArray getAsJsonArray(JsonElement jsonElement, String name) {
		if (jsonElement.isJsonArray()) {
			return jsonElement.getAsJsonArray();
		}
		throw new JsonSyntaxException("Expected " + name + " to be a JsonArray, was " + toString(jsonElement));
	}

	public static JsonArray getJsonArray(JsonObject jsonObject, String name) {
		if (jsonObject.has(name)) {
			return getAsJsonArray(jsonObject.get(name), name);
		}
		throw new JsonSyntaxException("Missing " + name + ", expected to find a JsonArray");
	}

	public static String getAsString(JsonElement jsonElement, String name) {
		if (jsonElement.isJsonPrimitive()) {
			return jsonElement.getAsString();
		}
		throw new JsonSyntaxException("Expected " + name + " to be a string, was " + toString(jsonElement));
	}

	public static String getString(JsonObject jsonObject, String name) {
		if (jsonObject.has(name)) {
			return getAsString(jsonObject.get(name), name);
		}
		throw new JsonSyntaxException("Missing " + name + ", expected to find a string");
	}

	public static int getAsInt(JsonElement jsonElement, String s) {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
			return jsonElement.getAsInt();
		}
		throw new JsonSyntaxException("Expected " + s + " to be a Int, was " + toString(jsonElement));
	}

	public static int getInt(JsonObject jsonObject, String s) {
		if (jsonObject.has(s)) {
			return getAsInt(jsonObject.get(s), s);
		}
		throw new JsonSyntaxException("Missing " + s + ", expected to find a Int");
	}

	public static JsonObject getAsJsonObject(JsonElement jsonElement, String name) {
		if (jsonElement.isJsonObject()) {
			return jsonElement.getAsJsonObject();
		}
		throw new JsonSyntaxException("Expected " + name + " to be a JsonObject, was " + toString(jsonElement));
	}

	public static JsonObject getJsonObject(JsonObject jsonObject, String name) {
		if (jsonObject.has(name)) {
			return getAsJsonObject(jsonObject.get(name), name);
		}
		throw new JsonSyntaxException("Missing " + name + ", expected to find an Object");
	}

	private static String toString(JsonElement jsonElement) {
		String abbreviateMiddle = String.valueOf(jsonElement);
		if (jsonElement == null) {
			return "null (missing)";
		}
		if (jsonElement.isJsonNull()) {
			return "null (json)";
		}
		if (jsonElement.isJsonArray()) {
			return "an array (" + abbreviateMiddle + ")";
		}
		if (jsonElement.isJsonObject()) {
			return "an object (" + abbreviateMiddle + ")";
		}
		if (jsonElement.isJsonPrimitive()) {
			JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
			if (asJsonPrimitive.isNumber()) {
				return "a number (" + abbreviateMiddle + ")";
			}
			if (asJsonPrimitive.isBoolean()) {
				return "a boolean (" + abbreviateMiddle + ")";
			}
		}
		return abbreviateMiddle;
	}

}
