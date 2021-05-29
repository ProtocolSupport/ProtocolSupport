package protocolsupport.protocol.utils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class SimpleTypeDeserializer<T> extends ProtocolData<Function<ByteBuf, T>> {

	@SafeVarargs
	public SimpleTypeDeserializer(@Nonnull Map.Entry<Function<ByteBuf, T>, ProtocolVersion[]>... entries) {
		super(entries);
	}

	@Override
	protected @Nonnull String createMissingDataMessage(@Nonnull ProtocolVersion version) {
		return MessageFormat.format("Don''t know how to deserialize type for protocol version {0}", version);
	}

}
