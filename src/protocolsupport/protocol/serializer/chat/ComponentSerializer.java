package protocolsupport.protocol.serializer.chat;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.KeybindComponent;
import protocolsupport.api.chat.components.ScoreComponent;
import protocolsupport.api.chat.components.SelectorComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.components.TranslateComponent;
import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.protocol.utils.json.SimpleJsonObjectSerializer;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;
import protocolsupport.utils.CachedInstanceOfChain;
import protocolsupport.utils.JsonUtils;

public class ComponentSerializer implements JsonDeserializer<BaseComponent>, SimpleJsonObjectSerializer<BaseComponent, String> {

	public static final ComponentSerializer DEFAULT_INSTANCE = new ComponentSerializer.Builder().build();

	public static class Builder {

		private ComponentContentSerializer<TextComponent> textSerializer = TextComponentContentSerializer.INSTANCE;
		private ComponentContentSerializer<TranslateComponent> translateSerializer = TranslateComponentContentSerializer.INSTANCE;
		private ComponentContentSerializer<ScoreComponent> scoreSerializer = ScoreComponentContentSerializer.INSTANCE;
		private ComponentContentSerializer<SelectorComponent> selectorSerializer = SelectorComponentContentSerializer.INSTANCE;
		private ComponentContentSerializer<KeybindComponent> keybindSerializer = new KeybindComponentContentSerializer();

		public Builder setTextSerializer(ComponentContentSerializer<TextComponent> textSerializer) {
			this.textSerializer = textSerializer;
			return this;
		}

		public Builder setTranslateSerializer(ComponentContentSerializer<TranslateComponent> translateSerializer) {
			this.translateSerializer = translateSerializer;
			return this;
		}

		public Builder setScoreSerializer(ComponentContentSerializer<ScoreComponent> scoreSerializer) {
			this.scoreSerializer = scoreSerializer;
			return this;
		}

		public Builder setSelectorSerializer(ComponentContentSerializer<SelectorComponent> selectorSerializer) {
			this.selectorSerializer = selectorSerializer;
			return this;
		}

		public Builder setKeybindSerializer(ComponentContentSerializer<KeybindComponent> keybindSerializer) {
			this.keybindSerializer = keybindSerializer;
			return this;
		}

		public ComponentSerializer build() {
			return new ComponentSerializer(textSerializer, translateSerializer, scoreSerializer, selectorSerializer, keybindSerializer);
		}

	}

	private final CachedInstanceOfChain<ComponentContentSerializer<BaseComponent>> componentContentSerializers = new CachedInstanceOfChain<>();

	public ComponentSerializer(
		ComponentContentSerializer<TextComponent> textSerializer,
		ComponentContentSerializer<TranslateComponent> translateSerializer,
		ComponentContentSerializer<ScoreComponent> scoreSerializer,
		ComponentContentSerializer<SelectorComponent> selectorSerializer,
		ComponentContentSerializer<KeybindComponent> keybindSerializer
	) {
		setComponentContentSerializer(TextComponent.class, textSerializer);
		setComponentContentSerializer(TranslateComponent.class, translateSerializer);
		setComponentContentSerializer(ScoreComponent.class, scoreSerializer);
		setComponentContentSerializer(SelectorComponent.class, selectorSerializer);
		setComponentContentSerializer(KeybindComponent.class, keybindSerializer);
	}

	@SuppressWarnings("unchecked")
	protected <T extends BaseComponent> void setComponentContentSerializer(Class<T> clazz, ComponentContentSerializer<T> serializer) {
		componentContentSerializers.setKnownPath(clazz, (ComponentContentSerializer<BaseComponent>) serializer);
	}

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
	public JsonElement serialize(SimpleJsonTreeSerializer<String> serializer, BaseComponent component, String locale) {
		JsonObject json = new JsonObject();
		if (!component.getModifier().isEmpty()) {
			serializeAndAdd(serializer, json, component.getModifier(), locale);
		}
		if (component.getClickAction() != null) {
			json.add("clickEvent", serializer.serialize(component.getClickAction(), locale));
		}
		if (component.getHoverAction() != null) {
			json.add("hoverEvent", serializer.serialize(component.getHoverAction(), locale));
		}
		if (component.getClickInsertion() != null) {
			json.addProperty("insertion", component.getClickInsertion());
		}
		if (!component.getSiblings().isEmpty()) {
			JsonArray jsonArray = new JsonArray();
			for (BaseComponent sibling : component.getSiblings()) {
				jsonArray.add(serialize(serializer, sibling, locale));
			}
			json.add("extra", jsonArray);
		}
		componentContentSerializers.selectPath(component.getClass()).serialize(serializer, json, component, locale);
		return json;
	}

	protected void serializeAndAdd(SimpleJsonTreeSerializer<String> serializer, JsonObject json, Object object, String locale) {
		JsonElement serialize = serializer.serialize(object, locale);
		if (serialize.isJsonObject()) {
			for (Entry<String, JsonElement> entry : ((JsonObject) serialize).entrySet()) {
				json.add(entry.getKey(), entry.getValue());
			}
		}
	}

}
