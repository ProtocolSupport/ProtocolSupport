package protocolsupport.protocol.types.pingresponse;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import protocolsupport.utils.JsonUtils;

public class PingResponseProtocolData {

	private int version;
	private String name;

	public PingResponseProtocolData() {
	}

	public PingResponseProtocolData(String name, int version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public static class Serializer implements JsonDeserializer<PingResponseProtocolData>, JsonSerializer<PingResponseProtocolData> {

		@Override
		public PingResponseProtocolData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
			JsonObject jsonObject = JsonUtils.getAsJsonObject(jsonElement, "version");
			return new PingResponseProtocolData(JsonUtils.getString(jsonObject, "name"), JsonUtils.getInt(jsonObject, "protocol"));
		}

		@Override
		public JsonElement serialize(PingResponseProtocolData serverData, Type type, JsonSerializationContext jsonSerializationContext) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("name", serverData.getName());
			jsonObject.addProperty("protocol", serverData.getVersion());
			return jsonObject;
		}

	}

}
