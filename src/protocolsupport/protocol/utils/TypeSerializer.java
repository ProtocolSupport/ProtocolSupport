package protocolsupport.protocol.utils;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.CachedInstanceOfChain;

public class TypeSerializer<T> {

	protected final Map<ProtocolVersion, Entry<T>> entries = new EnumMap<>(ProtocolVersion.class);

	public Entry<T> get(ProtocolVersion version) {
		return entries.get(version);
	}

	public static class Entry<T> {

		protected final ProtocolVersion version;
		protected final CachedInstanceOfChain<BiConsumer<ByteBuf, T>> serializers = new CachedInstanceOfChain<>();
		protected Entry(ProtocolVersion version) {
			this.version = version;
		}

		public void write(ByteBuf to, T t) {
			BiConsumer<ByteBuf, T> serializer = serializers.selectPath(t.getClass());
			if (serializer == null) {
				throw new IllegalArgumentException(MessageFormat.format("Don''t know how to serialize {0} for protocol version {1}", t.getClass(), version));
			}
			serializer.accept(to, t);
		}

	}

	@SuppressWarnings("unchecked")
	protected <L extends T> void register(Class<L> clazz, BiConsumer<ByteBuf, L> serializer, ProtocolVersion... versions) {
		for (ProtocolVersion version : ProtocolVersion.getAllSupported()) {
			entries.computeIfAbsent(version, Entry::new).serializers.setKnownPath(clazz, (BiConsumer<ByteBuf, T>) serializer);
		}
	}

}
