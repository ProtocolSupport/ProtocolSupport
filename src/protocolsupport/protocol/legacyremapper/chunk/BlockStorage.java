package protocolsupport.protocol.legacyremapper.chunk;

import java.util.Iterator;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.DataBits;
import net.minecraft.server.v1_10_R1.IBlockData;

public class BlockStorage {

	private static final boolean[] validBlockState = new boolean[Short.MAX_VALUE * 2];
	static {
		Iterator<Block> iter = Block.REGISTRY.iterator();
		while (iter.hasNext()) {
			Block block = iter.next();
			int blockId = Block.getId(block);
			validBlockState[blockId << 4] = true;
			for (IBlockData blockdata : block.t().a()) {
				validBlockState[(blockId << 4) | block.toLegacyData(blockdata)] = true;
			}
		}
	}

	public static void init() {
	}

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
		int blockState = palette[databits.a(blockindex)];
		if (blockState > 0 && blockState < validBlockState.length && validBlockState[blockState]) {
			return blockState;
		} else {
			return 0;
		}
	}

}