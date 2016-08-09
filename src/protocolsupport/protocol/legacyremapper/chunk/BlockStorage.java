package protocolsupport.protocol.legacyremapper.chunk;

import java.util.Iterator;

import io.netty.buffer.ByteBuf;

import net.minecraft.server.v1_10_R1.Block;
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

	private final int[] palette;
	private final long[] blockdata;
	private final int bitsPerBlock;
	private final int singleValMask;

	public BlockStorage(int[] palette, int bitsPerBlock, int datalength) {
		this.palette = palette;
		this.blockdata = new long[datalength];
		this.bitsPerBlock = bitsPerBlock;
		this.singleValMask = (1 << bitsPerBlock) - 1;
	}

	public void readFromStream(ByteBuf stream) {
		for (int i = 0; i < blockdata.length; i++) {
			blockdata[i] = stream.readLong();
		}
	}

	public int getBlockState(int blockindex) {
		int blockState = palette[getPaletteIndex(blockindex)];
		if (blockState > 0 && blockState < validBlockState.length && validBlockState[blockState]) {
			return blockState;
		} else {
			return 0;
		}
	}

	private int getPaletteIndex(int blockIndex) {
		int bitStartIndex = blockIndex * bitsPerBlock;
		int arrStartIndex = bitStartIndex >> 6;
		int arrEndIndex = ((bitStartIndex + bitsPerBlock) - 1) >> 6;
		int localStartBitIndex = bitStartIndex & 63;
		if (arrStartIndex == arrEndIndex) {
			return (int) ((this.blockdata[arrStartIndex] >>> localStartBitIndex) & this.singleValMask);
		} else {
			return (int) (((this.blockdata[arrStartIndex] >>> localStartBitIndex) | (this.blockdata[arrEndIndex] << (64 - localStartBitIndex))) & this.singleValMask);
		}
	}

}