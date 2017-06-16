package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.utils.netty.Compressor;

public class EmptyChunk {

	private static final byte[] fake18ChunkDataSky = new byte[8192 + 2048 + 2048 + 256];
	private static final byte[] fake18ChunkDataNoSky = new byte[8192 + 2048 + 256];
	public static byte[] get18ChunkData(boolean hasSkyLight) {
		return hasSkyLight ? fake18ChunkDataSky : fake18ChunkDataNoSky;
	}

	private static final byte[] fakePre18ChunkDataSky = Compressor.compressStatic(new byte[4096 + 2048 + 2048 + 2048 + 256]);
	private static final byte[] fakePre18ChunkDataNoSky	= Compressor.compressStatic(new byte[4096 + 2048 + 2048 + 256]);
	public static byte[] getPre18ChunkData(boolean hasSkyLight) {
		return hasSkyLight ? fakePre18ChunkDataSky : fakePre18ChunkDataNoSky;
	}

}
