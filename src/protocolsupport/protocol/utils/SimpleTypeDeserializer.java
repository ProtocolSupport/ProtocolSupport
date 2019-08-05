package protocolsupport.protocol.utils;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class SimpleTypeDeserializer<T> {

	protected final Map<ProtocolVersion, Function<ByteBuf, T>> entries = new EnumMap<>(ProtocolVersion.class);

	public Function<ByteBuf, T> get(ProtocolVersion version) {
		Function<ByteBuf, T> serializer = entries.get(version);
		if (serializer == null) {
			throw new IllegalArgumentException(MessageFormat.format("Don''t know how to deserialize type for protocol version {0}", version));
		}
		return entries.get(version);
	}

	protected void register(Function<ByteBuf, T> serializer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			entries.put(version, serializer);
		}
	}

}
