package protocolsupport.protocol.utils.chat;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import protocolsupport.api.chat.modifiers.ClickAction;

public class ClickActionSerializer implements JsonDeserializer<ClickAction>, JsonSerializer<ClickAction> {

	@Override
	public ClickAction deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) {
		JsonObject jsonObject = element.getAsJsonObject();
		JsonPrimitive actionJson = jsonObject.getAsJsonPrimitive("action");
		if (actionJson == null) {
			return null;
		}
		JsonElement valueJson = jsonObject.get("value");
		if (valueJson == null) {
			return null;
		}
		return new ClickAction(ClickAction.Type.valueOf(actionJson.getAsString().toUpperCase()), valueJson.getAsString());
	}

	@Override
	public JsonElement serialize(ClickAction action, Type type, JsonSerializationContext ctx) {
		JsonObject object = new JsonObject();
		object.addProperty("action", action.getType().toString().toLowerCase());
		object.addProperty("value", action.getValue());
		return object;
	}

}
