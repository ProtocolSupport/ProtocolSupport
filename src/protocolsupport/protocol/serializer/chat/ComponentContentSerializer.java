package protocolsupport.protocol.serializer.chat;

import com.google.gson.JsonObject;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public interface ComponentContentSerializer<T extends BaseComponent> {

	public void serialize(SimpleJsonTreeSerializer<String> serializer, JsonObject json, T component, String locale);

}
