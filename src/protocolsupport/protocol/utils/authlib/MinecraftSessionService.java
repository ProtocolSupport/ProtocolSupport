package protocolsupport.protocol.utils.authlib;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.utils.JsonUtils;

public class MinecraftSessionService {

	private static final String hasJoinedUrl = "https://sessionserver.mojang.com/session/minecraft/hasJoined";

	public static GameProfile hasJoinedServer(String name, String hash) throws AuthenticationUnavailableException, MalformedURLException {
		final URL url = new URL(hasJoinedUrl + "?username=" + name + "&serverId=" + hash);
		try {
			JsonObject root = new JsonParser().parse(new InputStreamReader(url.openStream())).getAsJsonObject();
			String rname = JsonUtils.getString(root, "name");
			UUID ruuid = UUIDTypeAdapter.fromString(JsonUtils.getString(root, "id"));
			GameProfile profile = new GameProfile(ruuid, rname);
			JsonArray properties = JsonUtils.getJsonArray(root, "properties");
			for (JsonElement property : properties) {
				JsonObject propertyobj = property.getAsJsonObject();
				profile.addProperty(new ProfileProperty(
					JsonUtils.getString(propertyobj, "name"),
					JsonUtils.getString(propertyobj, "value"),
					JsonUtils.getString(propertyobj, "signature")
				));
			}
			return profile;
		} catch (IOException | IllegalStateException | JsonParseException e) {
			throw new AuthenticationUnavailableException();
		}
	}

	public static class AuthenticationUnavailableException extends Exception {
		private static final long serialVersionUID = 1L;
	}

}
