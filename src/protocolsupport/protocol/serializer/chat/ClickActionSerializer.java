package protocolsupport.protocol.serializer.chat;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.protocol.utils.json.SimpleJsonObjectSerializer;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public class ClickActionSerializer implements JsonDeserializer<ClickAction>, SimpleJsonObjectSerializer<ClickAction, String> {

	public static final ClickActionSerializer INSTANCE = new ClickActionSerializer();

	protected ClickActionSerializer() {
	}

	protected static final String key_action = "action";
	protected static final String key_value = "value";

	@Override
	public ClickAction deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) {
		JsonObject jsonObject = element.getAsJsonObject();
		JsonPrimitive actionJson = jsonObject.getAsJsonPrimitive(key_action);
		if (actionJson == null) {
			return null;
		}
		JsonElement valueJson = jsonObject.get(key_value);
		if (valueJson == null) {
			return null;
		}
		return new ClickAction(ClickAction.Type.valueOf(actionJson.getAsString().toUpperCase()), valueJson.getAsString());
	}

	@Override
	public JsonElement serialize(SimpleJsonTreeSerializer<String> serializer, ClickAction action, String locale) {
		JsonObject json = new JsonObject();
		json.addProperty(key_action, action.getType().toString().toLowerCase());
		json.addProperty(key_value, action.getValue());
		return json;
	}

}
