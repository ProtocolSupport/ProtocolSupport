package protocolsupport.utils.chat;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

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
		return new HoverAction(atype, extractValue((BaseComponent) ctx.deserialize(clickObject.get("value"), BaseComponent.class)));
	}

	private static String extractValue(BaseComponent component) {
		//Currently only root value from component is used, but that may change in the future
		return component.getValue();
	}

	@Override
	public JsonElement serialize(HoverAction action, Type type, JsonSerializationContext ctx) {
		JsonObject object = new JsonObject();
		object.addProperty("action", action.getType().toString().toLowerCase());
		object.addProperty("value", action.getValue());
		return object;
	}

}
