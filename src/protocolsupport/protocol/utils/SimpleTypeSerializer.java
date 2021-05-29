package protocolsupport.protocol.utils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;

public class SimpleTypeSerializer<T> extends ProtocolData<BiConsumer<ByteBuf, T>> {

	@SafeVarargs
	public SimpleTypeSerializer(@Nonnull Map.Entry<BiConsumer<ByteBuf, T>, ProtocolVersion[]>... entries) {
		super(entries);
	}

	@Override
	protected @Nonnull String createMissingDataMessage(@Nonnull ProtocolVersion version) {
		return MessageFormat.format("Don''t know how to serialize type for protocol version {0}", version);
	}

}
