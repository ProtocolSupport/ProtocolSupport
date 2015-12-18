package protocolsupport.utils.chat;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import protocolsupport.api.chat.components.BaseComponent;
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
	public BaseComponent deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
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
					Object[] array = new Object[withJsonArray.size()];
					for (int i = 0; i < array.length; ++i) {
						array[i] = deserialize(withJsonArray.get(i), type, ctx);
						if (array[i] instanceof TextComponent) {
							final TextComponent text = (TextComponent) array[i];
							if (text.isSimple()) {
								array[i] = text.getValue();
							}
						}
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
			component.setModifier(ctx.<Modifier>deserialize(element, Modifier.class));
			component.setClickAction(ctx.<ClickAction>deserialize(element, ClickAction.class));
			component.setHoverAction(ctx.<HoverAction>deserialize(element, HoverAction.class));
			return component;
		}
        throw new JsonParseException("Don't know how to turn " + element.toString() + " into a Component");
	}

	@Override
	public JsonElement serialize(BaseComponent component, Type type, JsonSerializationContext ctx) {
		if (component instanceof TextComponent && component.isSimple()) {
			return new JsonPrimitive(component.getValue());
		}
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
			if (!translate.getArgs().isEmpty()) {
				JsonArray argsJson = new JsonArray();
				for (Object arg : translate.getArgs()) {
					if (arg instanceof BaseComponent) {
						BaseComponent argText = (BaseComponent) arg;
						argsJson.add(serialize(argText, argText.getClass(), ctx));
					} else {
						argsJson.add(new JsonPrimitive(String.valueOf(arg)));
					}
				}
				jsonObject.add("with", argsJson);
			}
		} else if (component instanceof ScoreComponent) {
			ScoreComponent score = (ScoreComponent) component;
			JsonObject scoreJSON = new JsonObject();
			scoreJSON.addProperty("name", score.getPlayerName());
			scoreJSON.addProperty("objective", score.getObjectiveName());
			scoreJSON.addProperty("value", score.getValue());
			jsonObject.add("score", scoreJSON);
		} else if (component instanceof SelectorComponent) {
			jsonObject.addProperty("selector", component.getValue());
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
