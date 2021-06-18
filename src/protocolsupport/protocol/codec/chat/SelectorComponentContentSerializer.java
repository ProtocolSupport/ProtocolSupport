package protocolsupport.protocol.codec.chat;

import com.google.gson.JsonObject;

import protocolsupport.api.chat.components.SelectorComponent;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public class SelectorComponentContentSerializer implements ComponentContentSerializer<SelectorComponent> {

	public static final SelectorComponentContentSerializer INSTANCE = new SelectorComponentContentSerializer();

	protected SelectorComponentContentSerializer() {
	}

	@Override
	public void serialize(SimpleJsonTreeSerializer<String> serializer, JsonObject json, SelectorComponent component, String locale) {
		json.addProperty("selector", component.getValue());
	}

}
