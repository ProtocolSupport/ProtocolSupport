package protocolsupport.protocol.codec.chat;

import com.google.gson.JsonObject;

import protocolsupport.api.chat.components.KeybindComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public class AsTextKeybindComponentContentSerializer implements ComponentContentSerializer<KeybindComponent> {

	public static final AsTextKeybindComponentContentSerializer INSTANCE = new AsTextKeybindComponentContentSerializer();

	protected AsTextKeybindComponentContentSerializer() {
	}

	@Override
	public void serialize(SimpleJsonTreeSerializer<String> serializer, JsonObject json, KeybindComponent component, String locale) {
		TextComponentContentSerializer.INSTANCE.serialize(serializer, json, new TextComponent(component.getValue()), locale);
	}

}
