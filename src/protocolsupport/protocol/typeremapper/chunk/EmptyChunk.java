package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
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

	private static byte[] fakePEChunkData;
	static {
		ByteBuf serializer = Unpooled.buffer();
		ArraySerializer.writeVarIntByteArray(serializer, chunkdata -> {
			chunkdata.writeByte(1); //1 section
		    chunkdata.writeByte(8); //New subchunk version!
		    chunkdata.writeByte(1); //Zero blockstorages :O
		    chunkdata.writeByte((1 << 1) | 1);  //Runtimeflag and palette id.
		    chunkdata.writeZero(512);
		    VarNumberSerializer.writeSVarInt(chunkdata, 1); //Palette size
		    VarNumberSerializer.writeSVarInt(chunkdata, 0); //Air
			chunkdata.writeZero(512); //heightmap.
			chunkdata.writeZero(256); //Biomedata.
			chunkdata.writeByte(0); //borders
		});
		fakePEChunkData = MiscSerializer.readAllBytes(serializer);
	}
	public static byte[] getPEChunkData() {
		return fakePEChunkData;
	}

}
