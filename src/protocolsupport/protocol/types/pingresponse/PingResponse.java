package protocolsupport.protocol.types.pingresponse;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.serializer.chat.ChatSerializer.GsonBaseComponentSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.JsonUtils;
import protocolsupportbuildprocessor.Preload;

//TODO: this probably also needs to be redone to version based serialization for future proofness
@Preload
public class PingResponse {

	private static final Map<ProtocolVersion, Gson> serializers = new EnumMap<>(ProtocolVersion.class);

	private static final Gson deserializer;

	static {
		for (ProtocolVersion version : ProtocolVersionsHelper.ALL) {
			serializers.put(
				version,
				new GsonBuilder()
				.registerTypeAdapter(PingResponse.class, new PingResponse.Serializer())
				.registerTypeAdapter(PingResponseProtocolData.class, new PingResponseProtocolData.Serializer())
				.registerTypeAdapter(PingResponsePlayers.class, new PingResponsePlayers.Serializer())
				.registerTypeHierarchyAdapter(BaseComponent.class, new GsonBaseComponentSerializer(version))
				.create()
			);
		}
		deserializer = serializers.get(ProtocolVersionsHelper.LATEST_PC);
	}

	public static PingResponse fromJson(String json) {
		return deserializer.fromJson(json, PingResponse.class);
	}

	public static String toJson(ProtocolVersion version, PingResponse response) {
		return serializers.get(version).toJson(response);
	}

	private PingResponseProtocolData protocoldata;
	private BaseComponent motd;
	private PingResponsePlayers players;
	private String icon;

	public PingResponse() {
	}

	public PingResponse(PingResponseProtocolData protocoldata, BaseComponent motd, PingResponsePlayers players, String icon) {
		this.protocoldata = protocoldata;
		this.motd = motd;
		this.players = players;
		this.icon = icon;
	}

	public PingResponseProtocolData getProtocolData() {
		return protocoldata;
	}

	public void setProtocolData(PingResponseProtocolData protocoldata) {
		this.protocoldata = protocoldata;
	}

	public BaseComponent getMotd() {
		return motd;
	}

	public void setMotd(BaseComponent motd) {
		this.motd = motd;
	}

	public PingResponsePlayers getPlayers() {
		return players;
	}

	public void setPlayers(PingResponsePlayers players) {
		this.players = players;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public static class Serializer implements JsonDeserializer<PingResponse>, JsonSerializer<PingResponse> {

		@Override
		public PingResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) {
			JsonObject jsonObject = JsonUtils.getAsJsonObject(jsonElement, "status");
			PingResponse ping = new PingResponse();
			if (jsonObject.has("version")) {
				ping.setProtocolData(ctx.deserialize(jsonObject.get("version"), PingResponseProtocolData.class));
			}
			if (jsonObject.has("description")) {
				ping.setMotd(ctx.deserialize(jsonObject.get("description"), BaseComponent.class));
			}
			if (jsonObject.has("players")) {
				ping.setPlayers(ctx.deserialize(jsonObject.get("players"), PingResponsePlayers.class));
			}
			if (jsonObject.has("favicon")) {
				ping.setIcon(JsonUtils.getString(jsonObject, "favicon"));
			}
			return ping;
		}

		@Override
		public JsonElement serialize(PingResponse ping, Type type, JsonSerializationContext ctx) {
			final JsonObject jsonObject = new JsonObject();
			if (ping.getProtocolData() != null) {
				jsonObject.add("version", ctx.serialize(ping.getProtocolData()));
			}
			if (ping.getMotd() != null) {
				jsonObject.add("description", ctx.serialize(ping.getMotd()));
			}
			if (ping.getPlayers() != null) {
				jsonObject.add("players", ctx.serialize(ping.getPlayers()));
			}
			if (ping.getIcon() != null) {
				jsonObject.addProperty("favicon", ping.getIcon());
			}
			return jsonObject;
		}

	}

}
