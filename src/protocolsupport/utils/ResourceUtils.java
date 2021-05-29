package protocolsupport.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import protocolsupport.ProtocolSupport;

public class ResourceUtils {

	private ResourceUtils() {
	}

	private static final String resourcesDirName = "resources";

	public static @Nullable InputStream getAsStream(@Nonnull String name) {
		return ProtocolSupport.class.getClassLoader().getResourceAsStream(resourcesDirName + "/" + name);
	}

	public static @Nullable BufferedReader getAsBufferedReader(@Nonnull String name) {
		InputStream resource = getAsStream(name);
		return resource != null ? new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)) : null;
	}

	public static @Nullable JsonObject getAsJsonObject(@Nonnull String name) {
		BufferedReader reader = getAsBufferedReader(name);
		return reader != null ? JsonUtils.readJsonObject(reader) : null;
	}

	public static @Nullable JsonArray getAsJsonArray(@Nonnull String name) {
		BufferedReader reader = getAsBufferedReader(name);
		return reader != null ? JsonUtils.readJsonArray(reader) : null;
	}

}
