package protocolsupport.protocol.utils.authlib;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import protocolsupport.api.utils.ProfileProperty;
import protocolsupport.utils.JsonUtils;

public class MinecraftSessionService {

	private static final String hasJoinedUrl = "https://sessionserver.mojang.com/session/minecraft/hasJoined";

	public static void checkHasJoinedServerAndUpdateProfile(GameProfile profile, String hash, String ip) throws AuthenticationUnavailableException, MalformedURLException {
		final URL url = new URL(hasJoinedUrl + "?username=" + profile.getOriginalName() + "&serverId=" + hash + (ip != null ? "&ip=" + ip : ""));
		try {
			JsonObject root = new JsonParser().parse(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)).getAsJsonObject();
			profile.setOriginalName(JsonUtils.getString(root, "name"));
			profile.setOriginalUUID(UUIDTypeAdapter.fromString(JsonUtils.getString(root, "id")));
			JsonArray properties = JsonUtils.getJsonArray(root, "properties");
			for (JsonElement property : properties) {
				JsonObject propertyobj = property.getAsJsonObject();
				profile.addProperty(new ProfileProperty(
					JsonUtils.getString(propertyobj, "name"),
					JsonUtils.getString(propertyobj, "value"),
					JsonUtils.getString(propertyobj, "signature")
				));
			}
		} catch (IOException | IllegalStateException | JsonParseException e) {
			throw new AuthenticationUnavailableException();
		}
	}

	public static class AuthenticationUnavailableException extends Exception {
		private static final long serialVersionUID = 1L;
	}

}
