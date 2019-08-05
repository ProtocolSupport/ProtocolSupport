package protocolsupport.protocol.utils;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class SimpleTypeSerializer<T> {

	protected final Map<ProtocolVersion, BiConsumer<ByteBuf, T>> entries = new EnumMap<>(ProtocolVersion.class);

	public BiConsumer<ByteBuf, T> get(ProtocolVersion version) {
		BiConsumer<ByteBuf, T> serializer = entries.get(version);
		if (serializer == null) {
			throw new IllegalArgumentException(MessageFormat.format("Don''t know how to serialize type for protocol version {0}", version));
		}
		return entries.get(version);
	}

	protected void register(BiConsumer<ByteBuf, T> serializer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			entries.put(version, serializer);
		}
	}

}
