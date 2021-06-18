package protocolsupport.protocol.codec.chat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public class UrlFixClickActionSerializer extends ClickActionSerializer {

	public static final UrlFixClickActionSerializer INSTANCE = new UrlFixClickActionSerializer();

	protected UrlFixClickActionSerializer() {
	}

	@Override
	public JsonElement serialize(SimpleJsonTreeSerializer<String> serializer, ClickAction action, String locale) {
		JsonObject json = (JsonObject) super.serialize(serializer, action, locale);
		if (action.getType() == ClickAction.Type.OPEN_URL) {
			String url = action.getValue();
			if (!url.startsWith("http://") && !url.startsWith("https://")) {
				json.addProperty(key_value, "http://" + url);
			}
		}
		return json;
	}

}
