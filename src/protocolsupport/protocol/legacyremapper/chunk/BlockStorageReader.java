package protocolsupport.protocol.legacyremapper.chunk;

import org.bukkit.Material;

import io.netty.buffer.ByteBuf;

@SuppressWarnings("deprecation")
public class BlockStorageReader {

	private static final boolean[] validBlockState = new boolean[Short.MAX_VALUE * 2];
	static {
		for (Material material : Material.values()) {
			for (int i = 0; i < 16; i++) {
				validBlockState[(material.getId() << 4) | i] = true;
			}
		}
	}

	public static void init() {
	}

	private final int[] palette;
	private final long[] blockdata;
	private final int bitsPerBlock;
	private final int singleValMask;

	public BlockStorageReader(int[] palette, int bitsPerBlock, int datalength) {
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
		if ((blockState > 0) && (blockState < validBlockState.length) && validBlockState[blockState]) {
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