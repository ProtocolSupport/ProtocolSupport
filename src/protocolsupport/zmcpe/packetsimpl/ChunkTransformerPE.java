package protocolsupport.zmcpe.packetsimpl;

import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.chunk.ChunkTransformer;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class ChunkTransformerPE extends ChunkTransformer {

	//TODO: optimize
	@Override
	protected byte[] toLegacyData0(ProtocolVersion version) {
		ProtocolSupportPacketDataSerializer stream = new ProtocolSupportPacketDataSerializer(Unpooled.buffer(), ProtocolVersion.MINECRAFT_PE);
		stream.writeByte(columnsCount);
		for (int i = 0; i < columnsCount; i++) {
			ChunkSection section = sections[i];
			stream.writeByte(0); //type
	        byte[] blocks = new byte[4096];
	        byte[] data = new byte[2048];
	        byte[] skyLight = new byte[2048];
	        byte[] blockLight = new byte[2048];
	        for (int x = 0; x < 16; x++) {
	            for (int z = 0; z < 16; z++) {
	                int offset = (x << 7) | (z << 3);
	                for (int y = 0; y < 16; y += 2) {
	                	int stateL = getBlockState(section, x, y, z);
	                	int stateH = getBlockState(section, x, y + 1, z);
	                    blocks[(offset << 1) | y] = (byte) (stateL >> 4);
	                    blocks[(offset << 1) | (y + 1)] = (byte) (stateH >> 4);
	                    data[offset | (y >> 1)] = (byte) (((stateH & 0xF) << 4) | (stateL & 0xF));
	                    if (hasSkyLight) {
		                    skyLight[offset | (y >> 1)] = (byte) ((getSkyLight(section, x, y + 1, z) << 4) | getSkyLight(section, x, y, z));
	                    }
	                    blockLight[offset | (y >> 1)] = (byte) ((getBlockLight(section, x, y + 1, z) << 4) | getBlockLight(section, x, y, z));
	                }
	            }
	        }
	        stream.writeBytes(blocks);
	        stream.writeBytes(data);
	        stream.writeBytes(skyLight);
	        stream.writeBytes(blockLight);
		}
		return ProtocolSupportPacketDataSerializer.toArray(stream);
	}

	private int getBlockState(ChunkSection section, int x, int y, int z) {
		return section.blockdata.getBlockState((y << 8) | (z << 4) | (x));
	}

	private int getSkyLight(ChunkSection section, int x, int y, int z) {
		return 0; //TODO
	}

	private int getBlockLight(ChunkSection section, int x, int y, int z) {
		return 0; //TODO
	}

}
