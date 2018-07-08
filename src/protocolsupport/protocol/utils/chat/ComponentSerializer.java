package protocolsupport.protocol.utils.chat;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.KeybindComponent;
import protocolsupport.api.chat.components.ScoreComponent;
import protocolsupport.api.chat.components.SelectorComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.components.TranslateComponent;
import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.utils.JsonUtils;

public class ComponentSerializer implements JsonDeserializer<BaseComponent>, JsonSerializer<BaseComponent> {

	@Override
	public BaseComponent deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) {
		if (element.isJsonPrimitive()) {
			return new TextComponent(element.getAsString());
		}
		if (element.isJsonArray()) {
			BaseComponent component = null;
			for (JsonElement e : element.getAsJsonArray()) {
				BaseComponent ecomponent = deserialize(e, e.getClass(), ctx);
				if (component == null) {
					component = ecomponent;
				} else {
					component.addSibling(ecomponent);
				}
			}
			return component;
		}
		if (element.isJsonObject()) {
			JsonObject jsonObject = element.getAsJsonObject();
			BaseComponent component;
			if (jsonObject.has("text")) {
				component = new TextComponent(jsonObject.get("text").getAsString());
			} else if (jsonObject.has("translate")) {
				String translate = jsonObject.get("translate").getAsString();
				if (jsonObject.has("with")) {
					JsonArray withJsonArray = jsonObject.getAsJsonArray("with");
					BaseComponent[] array = new BaseComponent[withJsonArray.size()];
					for (int i = 0; i < array.length; ++i) {
						array[i] = deserialize(withJsonArray.get(i), type, ctx);
					}
					component = new TranslateComponent(translate, array);
				} else {
					component = new TranslateComponent(translate);
				}
			} else if (jsonObject.has("score")) {
				JsonObject score = jsonObject.getAsJsonObject("score");
				if (!score.has("name") || !score.has("objective")) {
					throw new JsonParseException("A score component needs a least a name and an objective");
				}
				component = new ScoreComponent(JsonUtils.getString(score, "name"), JsonUtils.getString(score, "objective"));
				if (score.has("value")) {
					((ScoreComponent) component).setValue(JsonUtils.getString(score, "value"));
				}
			} else if (jsonObject.has("selector")) {
				component = new SelectorComponent(JsonUtils.getString(jsonObject, "selector"));
			} else if (jsonObject.has("keybind")) {
				component = new KeybindComponent(JsonUtils.getString(jsonObject, "keybind"));
			} else {
				throw new JsonParseException("Don't know how to turn " + element.toString() + " into a Component");
			}
			if (jsonObject.has("extra")) {
				JsonArray siblingsArray = jsonObject.getAsJsonArray("extra");
				if (siblingsArray.size() <= 0) {
					throw new JsonParseException("Unexpected empty array of components");
				}
				for (int i = 0; i < siblingsArray.size(); ++i) {
					component.addSibling(deserialize(siblingsArray.get(i), type, ctx));
				}
			}
			if (jsonObject.has("insertion")) {
				component.setClickInsertion(jsonObject.getAsJsonPrimitive("insertion").getAsString());
			}
			component.setModifier(ctx.deserialize(jsonObject, Modifier.class));
			if (jsonObject.has("clickEvent")) {
				component.setClickAction(ctx.deserialize(jsonObject.get("clickEvent"), ClickAction.class));
			}
			if (jsonObject.has("hoverEvent")) {
				component.setHoverAction(ctx.deserialize(jsonObject.get("hoverEvent"), HoverAction.class));
			}
			return component;
		}
        throw new JsonParseException("Don't know how to turn " + element.toString() + " into a Component");
	}

	@Override
	public JsonElement serialize(BaseComponent component, Type type, JsonSerializationContext ctx) {
		JsonObject jsonObject = new JsonObject();
		if (!component.getModifier().isEmpty()) {
			serializeAndAdd(component.getModifier(), jsonObject, ctx);
		}
		if (component.getClickAction() != null) {
			jsonObject.add("clickEvent", ctx.serialize(component.getClickAction()));
		}
		if (component.getHoverAction() != null) {
			jsonObject.add("hoverEvent", ctx.serialize(component.getHoverAction()));
		}
		if (component.getClickInsertion() != null) {
			jsonObject.addProperty("insertion", component.getClickInsertion());
		}
		if (!component.getSiblings().isEmpty()) {
			JsonArray jsonArray = new JsonArray();
			for (BaseComponent sibling : component.getSiblings()) {
				jsonArray.add(serialize(sibling, sibling.getClass(), ctx));
			}
			jsonObject.add("extra", jsonArray);
		}
		if (component instanceof TextComponent) {
			jsonObject.addProperty("text", component.getValue());
		} else if (component instanceof TranslateComponent) {
			TranslateComponent translate = (TranslateComponent) component;
			jsonObject.addProperty("translate", translate.getTranslationKey());
			if (!translate.getTranslationArgs().isEmpty()) {
				JsonArray argsJson = new JsonArray();
				for (BaseComponent arg : translate.getTranslationArgs()) {
					argsJson.add(serialize(arg, arg.getClass(), ctx));
				}
				jsonObject.add("with", argsJson);
			}
		} else if (component instanceof ScoreComponent) {
			ScoreComponent score = (ScoreComponent) component;
			JsonObject scoreJSON = new JsonObject();
			scoreJSON.addProperty("name", score.getPlayerName());
			scoreJSON.addProperty("objective", score.getObjectiveName());
			if (score.hasValue()) {
				scoreJSON.addProperty("value", score.getValue());
			}
			jsonObject.add("score", scoreJSON);
		} else if (component instanceof SelectorComponent) {
			jsonObject.addProperty("selector", component.getValue());
		} else if (component instanceof KeybindComponent) {
			jsonObject.addProperty("keybind", ((KeybindComponent) component).getKeybind());
		} else {
			throw new IllegalArgumentException("Don't know how to serialize " + component + " as a Component");
		}
		return jsonObject;
	}

	private void serializeAndAdd(Object object, JsonObject jsonObject, JsonSerializationContext ctx) {
		JsonElement serialize = ctx.serialize(object);
		if (serialize.isJsonObject()) {
			for (Entry<String, JsonElement> entry : ((JsonObject) serialize).entrySet()) {
				jsonObject.add(entry.getKey(), entry.getValue());
			}
		}
	}

}
