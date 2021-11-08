package protocolsupport.utils;

import java.io.Reader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {

	private JsonUtils() {
	}

	public static void setJsonValueIfNotNull(@Nonnull JsonObject jsonElement, @Nonnull String name, @Nullable Boolean value) {
		if (value != null) {
			jsonElement.addProperty(name, value);
		}
	}

	public static boolean hasJsonElement(@Nullable JsonObject jsonObject, @Nonnull String name) {
		return (jsonObject != null) && (jsonObject.get(name) != null);
	}

	public static boolean hasJsonArray(@Nullable JsonObject jsonObject, @Nonnull String name) {
		return hasJsonElement(jsonObject, name) && jsonObject.get(name).isJsonArray();
	}

	public static @Nonnull JsonArray getAsJsonArray(@Nonnull JsonElement jsonElement, @Nonnull String name) {
		if (jsonElement.isJsonArray()) {
			return jsonElement.getAsJsonArray();
		}
		throw new JsonSyntaxException("Expected " + name + " to be a JsonArray, was " + toString(jsonElement));
	}

	public static @Nonnull JsonArray getJsonArray(@Nonnull JsonObject jsonObject, @Nonnull String name) {
		if (jsonObject.has(name)) {
			return getAsJsonArray(jsonObject.get(name), name);
		}
		throw new JsonSyntaxException("Missing " + name + ", expected to find a JsonArray");
	}

	public static @Nonnull String getAsString(@Nonnull JsonElement jsonElement, @Nonnull String name) {
		if (jsonElement.isJsonPrimitive()) {
			return jsonElement.getAsString();
		}
		throw new JsonSyntaxException("Expected " + name + " to be a string, was " + toString(jsonElement));
	}

	public static @Nonnull String getString(@Nonnull JsonObject jsonObject, @Nonnull String name) {
		if (jsonObject.has(name)) {
			return getAsString(jsonObject.get(name), name);
		}
		throw new JsonSyntaxException("Missing " + name + ", expected to find a string");
	}

	public static @Nonnull float getAsFloat(@Nonnull JsonElement jsonElement, @Nonnull String name) {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
			return jsonElement.getAsFloat();
		}
		throw new JsonSyntaxException("Expected " + name + " to be a Float, was " + toString(jsonElement));
	}

	public static float getFloat(@Nonnull JsonObject jsonObject, @Nonnull String name) {
		if (jsonObject.has(name)) {
			return getAsFloat(jsonObject.get(name), name);
		}
		throw new JsonSyntaxException("Missing " + name + ", expected to find a Float");
	}

	public static int getAsInt(@Nonnull JsonElement jsonElement, @Nonnull String name) {
		if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
			return jsonElement.getAsInt();
		}
		throw new JsonSyntaxException("Expected " + name + " to be a Int, was " + toString(jsonElement));
	}

	public static int getInt(@Nonnull JsonObject jsonObject, @Nonnull String name) {
		if (jsonObject.has(name)) {
			return getAsInt(jsonObject.get(name), name);
		}
		throw new JsonSyntaxException("Missing " + name + ", expected to find a Int");
	}

	public static @Nonnull JsonObject getJsonObject(@Nonnull JsonObject jsonObject, @Nonnull String name) {
		if (jsonObject.has(name)) {
			return getAsJsonObject(jsonObject.get(name), name);
		}
		throw new JsonSyntaxException("Missing " + name + ", expected to find a JsonObject");
	}

	public static @Nonnull JsonObject getAsJsonObject(@Nonnull JsonElement jsonElement, @Nonnull String name) {
		if (jsonElement.isJsonObject()) {
			return jsonElement.getAsJsonObject();
		}
		throw new JsonSyntaxException("Expected " + name + " to be a JsonObject, was " + toString(jsonElement));
	}

	private static @Nonnull String toString(@Nullable JsonElement jsonElement) {
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

	public static final Gson GSON = new Gson();
	public static final JsonParser PARSER = new JsonParser();

	public static @Nonnull JsonObject readJsonObject(@Nonnull Reader reader) {
		return PARSER.parse(reader).getAsJsonObject();
	}

	public static @Nonnull JsonArray readJsonArray(@Nonnull Reader reader) {
		return PARSER.parse(reader).getAsJsonArray();
	}

}
