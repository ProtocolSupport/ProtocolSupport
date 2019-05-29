package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.utils.netty.Compressor;

public class ChunkUtils {

	public static void writeBBLight(ByteBuf to, byte[] light) {
		if (light != null) {
			to.writeBytes(light);
		} else {
			to.writeZero(2048);
		}
	}

	public static void writeBBEmptySection(ByteBuf to) {
		to.writeByte(4);
		ArraySerializer.writeVarIntVarIntArray(to, new int[] {0});
		ArraySerializer.writeVarIntLongArray(to, new long[256]);
	}

	public static void copyLight(byte[] to, int indexTo, byte[] light) {
		if (light != null) {
			System.arraycopy(light, 0, to, indexTo, light.length);
		}
	}

	protected static final byte[] emptySectionShortSky = new byte[8192 + 2048 + 2048 + 256];
	protected static final byte[] emptySectonShortNoSky = new byte[8192 + 2048 + 256];
	public static byte[] getEmptySectionShort(boolean hasSkyLight) {
		return hasSkyLight ? emptySectionShortSky : emptySectonShortNoSky;
	}

	protected static final byte[] emptySectionByteSky = Compressor.compressStatic(new byte[4096 + 2048 + 2048 + 2048 + 256]);
	protected static final byte[] emptySectonByteNoSky = Compressor.compressStatic(new byte[4096 + 2048 + 2048 + 256]);
	public static byte[] getEmptySectionByte(boolean hasSkyLight) {
		return hasSkyLight ? emptySectionByteSky : emptySectonByteNoSky;
	}

}
