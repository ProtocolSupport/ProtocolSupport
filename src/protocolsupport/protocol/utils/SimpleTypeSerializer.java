package protocolsupport.protocol.utils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.function.BiConsumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class SimpleTypeSerializer<T> extends ProtocolData<BiConsumer<ByteBuf, T>> {

	@SafeVarargs
	public SimpleTypeSerializer(Map.Entry<BiConsumer<ByteBuf, T>, ProtocolVersion[]>... entries) {
		super(entries);
	}

	@Override
	protected String createMissingDataMessage(ProtocolVersion version) {
		return MessageFormat.format("Don''t know how to serialize type for protocol version {0}", version);
	}

}
