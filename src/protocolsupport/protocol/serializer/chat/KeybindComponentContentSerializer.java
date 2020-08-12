package protocolsupport.protocol.serializer.chat;

import com.google.gson.JsonObject;

import protocolsupport.api.chat.components.KeybindComponent;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public class KeybindComponentContentSerializer implements ComponentContentSerializer<KeybindComponent> {

	public static final KeybindComponentContentSerializer INSTANCE = new KeybindComponentContentSerializer();

	protected KeybindComponentContentSerializer() {
	}

	@Override
	public void serialize(SimpleJsonTreeSerializer<String> serializer, JsonObject json, KeybindComponent component, String locale) {
		json.addProperty("keybind", component.getKeybind());
	}

}
