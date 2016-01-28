package protocolsupport.utils.chat;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.HoverAction;

public class HoverActionSerializer implements JsonDeserializer<HoverAction>, JsonSerializer<HoverAction> {

	@Override
	public HoverAction deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		JsonObject jsonobject = element.getAsJsonObject();
		if (!jsonobject.has("hoverEvent")) {
			return null;
		}
		JsonObject clickObject = jsonobject.getAsJsonObject("hoverEvent");
		HoverAction.Type atype = HoverAction.Type.valueOf(clickObject.getAsJsonPrimitive("action").getAsString().toUpperCase());
		BaseComponent component = ((BaseComponent) ctx.deserialize(clickObject.get("value"), BaseComponent.class));
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
