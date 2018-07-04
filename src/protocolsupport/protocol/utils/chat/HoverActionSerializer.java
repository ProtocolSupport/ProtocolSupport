package protocolsupport.protocol.utils.chat;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.HoverAction;

public class HoverActionSerializer implements JsonDeserializer<HoverAction>, JsonSerializer<HoverAction> {

	@Override
	public HoverAction deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) {
		JsonObject jsonObject = element.getAsJsonObject();
		JsonPrimitive actionJson = jsonObject.getAsJsonPrimitive("action");
		if (actionJson == null) {
			return null;
		}
		JsonElement valueJson = jsonObject.get("value");
		if (valueJson == null) {
			return null;
		}
		HoverAction.Type atype = HoverAction.Type.valueOf(actionJson.getAsString().toUpperCase());
		BaseComponent component = ctx.deserialize(valueJson, BaseComponent.class);
		return new HoverAction(atype, atype == HoverAction.Type.SHOW_TEXT ? ChatAPI.toJSON(component) : component.getValue());
	}

	@Override
	public JsonElement serialize(HoverAction action, Type type, JsonSerializationContext ctx) {
		JsonObject object = new JsonObject();
		object.addProperty("action", action.getType().toString().toLowerCase());
		object.add("value", action.getType() == HoverAction.Type.SHOW_TEXT ? ctx.serialize(ChatAPI.fromJSON(action.getValue())) : new JsonPrimitive(action.getValue()));
		return object;
	}

}
