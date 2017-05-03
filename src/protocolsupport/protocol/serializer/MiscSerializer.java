package protocolsupport.protocol.serializer;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.DecoderException;

public class MiscSerializer {

	public static float readLFloat(ByteBuf from) {
		return Float.intBitsToFloat(ByteBufUtil.swapInt(from.readInt()));
	}

	public static void writeLFloat(ByteBuf to, float f) {
		to.writeInt(ByteBufUtil.swapInt(Float.floatToIntBits(f)));
	}
	
	public static short readLShort(ByteBuf from){
		return ByteBufUtil.swapShort(from.readShort());
	}
	
	public static void writeLShort(ByteBuf to, short s){
		to.writeShort(ByteBufUtil.swapShort(s));
	}

	public static <T extends Enum<T>> T readEnum(ByteBuf from, Class<T> clazz) {
		return clazz.getEnumConstants()[VarNumberSerializer.readVarInt(from)];
	}

	public static void writeEnum(ByteBuf to, Enum<?> e) {
		VarNumberSerializer.writeVarInt(to, e.ordinal());
	}

	public static UUID readUUID(ByteBuf from) {
		return new UUID(from.readLong(), from.readLong());
	}

	public static void writeUUID(ByteBuf to, UUID uuid) {
		to.writeLong(uuid.getMostSignificantBits());
		to.writeLong(uuid.getLeastSignificantBits());
	}

	public static byte[] readAllBytes(ByteBuf buf) {
		return MiscSerializer.readBytes(buf, buf.readableBytes());
	}

	public static byte[] readBytes(ByteBuf buf, int length) {
		byte[] result = new byte[length];
		buf.readBytes(result);
		return result;
	}

	protected static void checkLimit(int size, int limit) {
		if (size > limit) {
			throw new DecoderException("Size " + size + " is bigger than allowed " + limit);
		}
	}

}
