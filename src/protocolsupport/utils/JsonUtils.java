package protocolsupport.utils;

import org.apache.commons.lang3.StringUtils;

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

	public static String asString(JsonElement jsonElement, String name) {
		if (jsonElement.isJsonPrimitive()) {
			return jsonElement.getAsString();
		}
		throw new JsonSyntaxException("Expected " + name + " to be a string, was " + toString(jsonElement));
	}

	public static String getString(JsonObject jsonObject, String name) {
		if (jsonObject.has(name)) {
			return asString(jsonObject.get(name), name);
		}
		throw new JsonSyntaxException("Missing " + name + ", expected to find a string");
	}

	private static String toString(final JsonElement jsonElement) {
		final String abbreviateMiddle = StringUtils.abbreviateMiddle(String.valueOf(jsonElement), "...", 10);
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
			final JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
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
