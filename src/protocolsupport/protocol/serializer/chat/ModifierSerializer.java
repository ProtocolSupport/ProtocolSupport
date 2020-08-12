package protocolsupport.protocol.serializer.chat;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.chat.ChatColor;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.protocol.utils.json.SimpleJsonObjectSerializer;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;
import protocolsupport.utils.JsonUtils;

public class ModifierSerializer implements JsonDeserializer<Modifier>, SimpleJsonObjectSerializer<Modifier, String> {

	public static final ModifierSerializer INSTANCE = new ModifierSerializer();

	protected ModifierSerializer() {
	}

	protected static final String key_bold = "bold";
	protected static final String key_italic = "italic";
	protected static final String key_underlined = "underlined";
	protected static final String key_strikethrough = "strikethrough";
	protected static final String key_obfuscated = "obfuscated";
	protected static final String key_color = "color";
	protected static final String key_font = "font";

	@Override
	public Modifier deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) {
		JsonObject jsonobject = element.getAsJsonObject();
		Modifier modifier = new Modifier();
		if (jsonobject.has(key_bold)) {
			modifier.setBold(jsonobject.get(key_bold).getAsBoolean());
		}
		if (jsonobject.has(key_italic)) {
			modifier.setItalic(jsonobject.get(key_italic).getAsBoolean());
		}
		if (jsonobject.has(key_underlined)) {
			modifier.setUnderlined(jsonobject.get(key_underlined).getAsBoolean());
		}
		if (jsonobject.has(key_strikethrough)) {
			modifier.setStrikethrough(jsonobject.get(key_strikethrough).getAsBoolean());
		}
		if (jsonobject.has(key_obfuscated)) {
			modifier.setRandom(jsonobject.get(key_obfuscated).getAsBoolean());
		}
		if (jsonobject.has(key_color)) {
			modifier.setRGBColor(ChatColor.of(jsonobject.get(key_color).getAsString()));
		}
		if (jsonobject.has(key_font)) {
			modifier.setFont(jsonobject.get(key_font).getAsString());
		}
		return modifier;
	}

	@Override
	public JsonElement serialize(SimpleJsonTreeSerializer<String> serializer, Modifier modifier, String metadata) {
		JsonObject jsonobject = new JsonObject();
		JsonUtils.setIfNotNull(jsonobject, key_bold, modifier.isBold());
		JsonUtils.setIfNotNull(jsonobject, key_italic, modifier.isItalic());
		JsonUtils.setIfNotNull(jsonobject, key_underlined, modifier.isUnderlined());
		JsonUtils.setIfNotNull(jsonobject, key_strikethrough, modifier.isStrikethrough());
		JsonUtils.setIfNotNull(jsonobject, key_obfuscated, modifier.isRandom());
		ChatColor color = modifier.getRGBColor();
		if (color != null) {
			jsonobject.addProperty(key_color, color.getIdentifier());
		}
		String font = modifier.getFont();
		if (font != null) {
			jsonobject.addProperty(key_font, font);
		}
		return jsonobject;
	}

}
