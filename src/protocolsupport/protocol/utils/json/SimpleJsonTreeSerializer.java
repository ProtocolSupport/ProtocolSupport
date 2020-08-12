package protocolsupport.protocol.utils.json;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;

import protocolsupport.utils.CachedInstanceOfChain;

public class SimpleJsonTreeSerializer<M> {

	public static class Builder<M> {

		private final List<Map.Entry<Class<?>, SimpleJsonObjectSerializer<?, M>>> serializers = new ArrayList<>();

		public <T> Builder<M> registerSerializer(Class<T> clazz, SimpleJsonObjectSerializer<T, M> serializer) {
			this.serializers.add(new AbstractMap.SimpleEntry<>(clazz, serializer));
			return this;
		}

		public SimpleJsonTreeSerializer<M> build() {
			return new SimpleJsonTreeSerializer<>(serializers);
		}

	}

	private final CachedInstanceOfChain<SimpleJsonObjectSerializer<Object, M>> serializers = new CachedInstanceOfChain<>();

	@SuppressWarnings("unchecked")
	public SimpleJsonTreeSerializer(List<Map.Entry<Class<?>, SimpleJsonObjectSerializer<?, M>>> serializers) {
		for (Map.Entry<Class<?>, SimpleJsonObjectSerializer<?, M>> entry : serializers) {
			this.serializers.setKnownPath(entry.getKey(), ((SimpleJsonObjectSerializer<Object, M>) entry.getValue()));
		}
	}

	public JsonElement serialize(Object object, M metadata) {
		return serializers.selectPath(object.getClass()).serialize(this, object, metadata);
	}

}
