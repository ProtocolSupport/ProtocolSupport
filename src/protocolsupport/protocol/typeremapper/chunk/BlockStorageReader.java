package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;

public class BlockStorageReader {

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
		return palette[getPaletteIndex(blockindex)];
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