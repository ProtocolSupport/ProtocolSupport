package protocolsupport.protocol.utils.json;

import com.google.gson.JsonElement;

public interface SimpleJsonObjectSerializer<T, M> {

	public JsonElement serialize(SimpleJsonTreeSerializer<M> serializer, T object, M metadata);

}
