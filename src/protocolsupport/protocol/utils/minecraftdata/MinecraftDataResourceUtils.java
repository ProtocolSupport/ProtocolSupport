package protocolsupport.protocol.utils.minecraftdata;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import protocolsupport.utils.ResourceUtils;

public class MinecraftDataResourceUtils {

	private MinecraftDataResourceUtils() {
	}

	public static @Nonnull String getResourcePath(@Nonnull String name) {
		return ("data/" + name);
	}

	public static @Nullable JsonObject getResourceAsJsonObject(@Nonnull String name) {
		return ResourceUtils.getAsJsonObject(getResourcePath(name));
	}

}
