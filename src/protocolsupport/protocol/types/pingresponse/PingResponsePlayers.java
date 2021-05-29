package protocolsupport.protocol.types.pingresponse;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import protocolsupport.protocol.utils.authlib.LoginProfile;
import protocolsupport.utils.JsonUtils;

public class PingResponsePlayers {

	private int max;
	private int online;
	private LoginProfile[] players;

	public PingResponsePlayers() {
	}

	public PingResponsePlayers(int max, int online) {
		this.max = max;
		this.online = online;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public boolean hasPlayers() {
		return (players != null) && (players.length > 0);
	}

	public LoginProfile[] getPlayers() {
		return players;
	}

	public void setPlayers(LoginProfile[] players) {
		this.players = players;
	}

	public static class Serializer implements JsonDeserializer<PingResponsePlayers>, JsonSerializer<PingResponsePlayers> {
		@Override
		public PingResponsePlayers deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
			JsonObject jsonObject = JsonUtils.getAsJsonObject(jsonElement, "players");
			PingResponsePlayers players = new PingResponsePlayers(JsonUtils.getInt(jsonObject, "max"), JsonUtils.getInt(jsonObject, "online"));
			if (JsonUtils.hasJsonArray(jsonObject, "sample")) {
				JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, "sample");
				if (jsonArray.size() > 0) {
					LoginProfile[] array = new LoginProfile[jsonArray.size()];
					for (int i = 0; i < array.length; ++i) {
						JsonObject playerJsonObject = JsonUtils.getAsJsonObject(jsonArray.get(i), "player[" + i + "]");
						array[i] = new LoginProfile(UUID.fromString(JsonUtils.getString(playerJsonObject, "id")), JsonUtils.getString(playerJsonObject, "name"));
					}
					players.setPlayers(array);
				}
			}
			return players;
		}

		@Override
		public JsonElement serialize(PingResponsePlayers players, Type type, JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("max", players.getMax());
			jsonObject.addProperty("online", players.getOnline());
			if (players.hasPlayers()) {
				JsonArray jsonArray = new JsonArray();
				for (int i = 0; i < players.getPlayers().length; ++i) {
					LoginProfile player = players.getPlayers()[i];
					JsonObject playerJsonObject = new JsonObject();
					playerJsonObject.addProperty("id", (player.getUUID() == null) ? "" : player.getUUID().toString());
					playerJsonObject.addProperty("name", player.getName());
					jsonArray.add(playerJsonObject);
				}
				jsonObject.add("sample", jsonArray);
			}
			return jsonObject;
		}
	}

}
