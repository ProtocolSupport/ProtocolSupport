package protocolsupport.protocol.transformer.utils;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R3.ChatDeserializer;
import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.ChatTypeAdapterFactory;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ServerPing;
import net.minecraft.server.v1_8_R3.ServerPing.ServerData;
import net.minecraft.server.v1_8_R3.ServerPing.ServerPingPlayerSample;

public class ServerPingSerializers {

	public static final Gson PING_GSON = new GsonBuilder()
	.registerTypeAdapter(ServerData.class, new ServerDataSerializer())
	.registerTypeAdapter(ServerPingPlayerSample.class, new PlayerSampleSerializer())
	.registerTypeAdapter(ServerPing.class, new ServerPing.Serializer())
	.registerTypeHierarchyAdapter(IChatBaseComponent.class, new IChatBaseComponent.ChatSerializer())
	.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer())
	.registerTypeAdapterFactory(new ChatTypeAdapterFactory()).create();

	public static class ServerDataSerializer implements JsonDeserializer<ServerData>, JsonSerializer<ServerData> {
		@Override
		public ServerData deserialize(final JsonElement element, final Type type, final JsonDeserializationContext ctx) throws JsonParseException {
			final JsonObject l = ChatDeserializer.l(element, "version");
			return new ServerData(ChatDeserializer.h(l, "name"), ChatDeserializer.m(l, "protocol"));
		}

		@Override
		public JsonElement serialize(final ServerData data, final Type type, final JsonSerializationContext ctx) {
			final JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("name", data.a());
			jsonObject.addProperty("protocol", data.b());
			return jsonObject;
		}
	}


	public static class PlayerSampleSerializer implements JsonDeserializer<ServerPingPlayerSample>, JsonSerializer<ServerPingPlayerSample> {
		@Override
		public ServerPingPlayerSample deserialize(final JsonElement element, final Type type, final JsonDeserializationContext ctx) throws JsonParseException {
			final JsonObject players = ChatDeserializer.l(element, "players");
			final ServerPingPlayerSample serverPingPlayerSample = new ServerPingPlayerSample(ChatDeserializer.m(players, "max"), ChatDeserializer.m(players, "online"));
			if (ChatDeserializer.d(players, "sample")) {
				final JsonArray sample = ChatDeserializer.t(players, "sample");
				if (sample.size() > 0) {
					final GameProfile[] array = new GameProfile[sample.size()];
					for (int i = 0; i < array.length; ++i) {
						final JsonObject j = ChatDeserializer.l(sample.get(i), "player[" + i + "]");
						final String h = ChatDeserializer.h(j, "id");
						array[i] = new GameProfile(UUID.fromString(h), ChatDeserializer.h(j, "name"));
					}
					serverPingPlayerSample.a(array);
				}
			}
			return serverPingPlayerSample;
		}

		@Override
		public JsonElement serialize(final ServerPingPlayerSample data, final Type type, final JsonSerializationContext ctx) {
			final JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("max", data.a());
			jsonObject.addProperty("online", data.b());
			if ((data.c() != null) && (data.c().length > 0)) {
				final JsonArray jsonArray = new JsonArray();
				for (int i = 0; i < data.c().length; ++i) {
					final JsonObject jsonObject2 = new JsonObject();
					final UUID id = data.c()[i].getId();
					jsonObject2.addProperty("id", (id == null) ? "" : id.toString());
					jsonObject2.addProperty("name", data.c()[i].getName());
					jsonArray.add(jsonObject2);
				}
				jsonObject.add("sample", jsonArray);
			}
			return jsonObject;
		}
	}

}
