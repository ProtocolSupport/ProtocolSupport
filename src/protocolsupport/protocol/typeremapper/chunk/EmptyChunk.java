package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Chunk;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.netty.Compressor;
import protocolsupportbuildprocessor.Preload;

@Preload
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

	private static final byte[] fakePEChunkData;
	public static byte[] getPEChunkData() {
		return fakePEChunkData;
	}

	private static final byte[] fakePEChunkData112;
	public static byte[] getPEChunkData112() {
		return fakePEChunkData112;
	}

	static {
		ByteBuf serializer = Unpooled.buffer();
		ArraySerializer.writeVarIntByteArray(serializer, chunkdata -> {
			chunkdata.writeByte(1); //1 section
			Chunk.writeEmptySubChunk(chunkdata, ProtocolVersion.MINECRAFT_PE_1_8);
			chunkdata.writeZero(512); //heightmap.
			chunkdata.writeZero(256); //Biomedata.
			chunkdata.writeByte(0); //borders
		});
		fakePEChunkData = MiscSerializer.readAllBytes(serializer);
	}

	static {
		ByteBuf serializer = Unpooled.buffer();
		serializer.writeShortLE(1); //1 section
		ArraySerializer.writeVarIntByteArray(serializer, chunkdata -> {
			Chunk.writeEmptySubChunk(chunkdata, ProtocolVersion.MINECRAFT_PE_1_12);
			chunkdata.writeZero(512); //heightmap.
			chunkdata.writeZero(256); //Biomedata.
			chunkdata.writeByte(0); //borders
		});
		fakePEChunkData112 = MiscSerializer.readAllBytes(serializer);
	}

}
