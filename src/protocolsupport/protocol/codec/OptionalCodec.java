package protocolsupport.protocol.codec;

import java.util.function.BiConsumer;
import java.util.function.Function;

import io.netty.buffer.ByteBuf;

public class OptionalCodec {

	public static <T> T readOptional(ByteBuf from, Function<ByteBuf, T> typeReader) {
		if (from.readBoolean()) {
			return typeReader.apply(from);
		}
		return null;
	}

	public static String readOptionalVarIntUTF8String(ByteBuf from) {
		return readOptional(from, StringCodec::readVarIntUTF8String);
	}

	public static <T> void writeOptional(ByteBuf to, T type, BiConsumer<ByteBuf, T> typeWriter) {
		if (type != null) {
			to.writeBoolean(true);
			typeWriter.accept(to, type);
		} else {
			to.writeBoolean(false);
		}
	}

	public static void writeOptionalVarIntUTF8String(ByteBuf to, String string) {
		writeOptional(to, string, StringCodec::writeVarIntUTF8String);
	}

}
