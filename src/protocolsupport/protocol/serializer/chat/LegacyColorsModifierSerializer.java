package protocolsupport.protocol.serializer.chat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.chat.ChatColor;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public class LegacyColorsModifierSerializer extends ModifierSerializer {

	public static final LegacyColorsModifierSerializer INSTANCE = new LegacyColorsModifierSerializer();

	protected LegacyColorsModifierSerializer() {
	}

	@Override
	public JsonElement serialize(SimpleJsonTreeSerializer<String> serializer, Modifier modifier, String metadata) {
		JsonObject json = (JsonObject) super.serialize(serializer, modifier, metadata);
		ChatColor color = modifier.getRGBColor();
		if ((color != null) && !color.isBasic()) {
			json.addProperty(key_color, color.asBasic().getIdentifier());
		}
		return json;
	}

}
