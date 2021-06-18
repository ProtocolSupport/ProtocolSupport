package protocolsupport.protocol.typeremapper.chunk;

import java.util.BitSet;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.utils.netty.RecyclableWrapCompressor;

public class ChunkWriteUtils {

	private ChunkWriteUtils() {
	}

	public static int computeLimitedHeightMask(BitSet mask, int offest, int limit) {
		BitSet limitedMask = mask.get(offest, offest + limit);
		long[] longarray = limitedMask.toLongArray();
		return (int) (longarray.length > 0 ? longarray[0] : 0);
	}

	public static void writeBBLight(ByteBuf to, byte[] light) {
		if (light != null) {
			to.writeBytes(light);
		} else {
			to.writeZero(2048);
		}
	}

	public static void writeBBEmptySection(ByteBuf to) {
		to.writeByte(4);
		ArrayCodec.writeVarIntVarIntArray(to, new int[] {0});
		ArrayCodec.writeVarIntLongArray(to, new long[256]);
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

	protected static final byte[] emptySectionByteSky = RecyclableWrapCompressor.compressStatic(new byte[4096 + 2048 + 2048 + 2048 + 256]);
	protected static final byte[] emptySectonByteNoSky = RecyclableWrapCompressor.compressStatic(new byte[4096 + 2048 + 2048 + 256]);
	public static byte[] getEmptySectionByte(boolean hasSkyLight) {
		return hasSkyLight ? emptySectionByteSky : emptySectonByteNoSky;
	}

}
