package protocolsupport.protocol.typeremapper.chunknew;

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

	private static final byte[] fake18ChunkDataSky = new byte[8192 + 2048 + 2048 + 256];
	private static final byte[] fake18ChunkDataNoSky = new byte[8192 + 2048 + 256];
	public static byte[] getEmptyChunkShort(boolean hasSkyLight) {
		return hasSkyLight ? fake18ChunkDataSky : fake18ChunkDataNoSky;
	}

	private static final byte[] fakePre18ChunkDataSky = Compressor.compressStatic(new byte[4096 + 2048 + 2048 + 2048 + 256]);
	private static final byte[] fakePre18ChunkDataNoSky	= Compressor.compressStatic(new byte[4096 + 2048 + 2048 + 256]);
	public static byte[] getPre18ChunkData(boolean hasSkyLight) {
		return hasSkyLight ? fakePre18ChunkDataSky : fakePre18ChunkDataNoSky;
	}

}
