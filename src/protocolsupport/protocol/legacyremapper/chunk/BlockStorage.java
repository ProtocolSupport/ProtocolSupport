package protocolsupport.protocol.legacyremapper.chunk;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_10_R1.DataBits;

public class BlockStorage {

	private final DataBits databits;
	private final int[] palette;
	public BlockStorage(int[] palette, int bitsPerBlock) {
		this.palette = palette;
		this.databits = new DataBits(bitsPerBlock, ChunkTransformer.blocksInSection);
	}

	public void readFromStream(ByteBuf stream) {
		long[] data = databits.a();
		for (int i = 0; i < data.length; i++) {
			data[i] = stream.readLong();
		}
	}

	public int getBlockState(int blockindex) {
		return palette[databits.a(blockindex)];
	}

}