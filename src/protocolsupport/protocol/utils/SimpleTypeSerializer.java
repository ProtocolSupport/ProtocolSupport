package protocolsupport.protocol.utils;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class SimpleTypeSerializer<T> {

	protected final Map<ProtocolVersion, BiConsumer<ByteBuf, T>> entries = new EnumMap<>(ProtocolVersion.class);

	public SimpleTypeSerializer() {
	}

	@SafeVarargs
	public SimpleTypeSerializer(Map.Entry<BiConsumer<ByteBuf, T>, ProtocolVersion[]>... entries) {
		for (Map.Entry<BiConsumer<ByteBuf, T>, ProtocolVersion[]> entry : entries) {
			BiConsumer<ByteBuf, T> serializer = entry.getKey();
			for (ProtocolVersion version : entry.getValue()) {
				this.entries.put(version, serializer);
			}
		}
	}

	public BiConsumer<ByteBuf, T> get(ProtocolVersion version) {
		BiConsumer<ByteBuf, T> serializer = entries.get(version);
		if (serializer == null) {
			throw new IllegalArgumentException(MessageFormat.format("Don''t know how to serialize type for protocol version {0}", version));
		}
		return entries.get(version);
	}

}
