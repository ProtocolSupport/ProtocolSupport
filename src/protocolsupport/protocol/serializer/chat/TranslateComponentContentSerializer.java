package protocolsupport.protocol.serializer.chat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TranslateComponent;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public class TranslateComponentContentSerializer implements ComponentContentSerializer<TranslateComponent> {

	public static final TranslateComponentContentSerializer INSTANCE = new TranslateComponentContentSerializer();

	protected TranslateComponentContentSerializer() {
	}

	@Override
	public void serialize(SimpleJsonTreeSerializer<String> serializer, JsonObject json, TranslateComponent component, String locale) {
		json.addProperty("translate", component.getTranslationKey());
		if (!component.getTranslationArgs().isEmpty()) {
			JsonArray argsJson = new JsonArray();
			for (BaseComponent arg : component.getTranslationArgs()) {
				argsJson.add(serializer.serialize(arg, locale));
			}
			json.add("with", argsJson);
		}
	}

}
