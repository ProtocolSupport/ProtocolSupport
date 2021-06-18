package protocolsupport.protocol.codec.chat;

import com.google.gson.JsonObject;

import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public class TextComponentContentSerializer implements ComponentContentSerializer<TextComponent> {

	public static final TextComponentContentSerializer INSTANCE = new TextComponentContentSerializer();

	protected TextComponentContentSerializer() {
	}

	@Override
	public void serialize(SimpleJsonTreeSerializer<String> serializer, JsonObject json, TextComponent component, String locale) {
		json.addProperty("text", component.getValue());
	}

}
