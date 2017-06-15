package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.utils.Utils;

public class BlockStorageWriter {

	private final long[] blockdata;
	private final int bitsPerBlock;
	private final long singleValMask;

	public BlockStorageWriter(int bitsPerBlock, int blocks) {
		this.bitsPerBlock = bitsPerBlock;
		this.singleValMask = (1L << bitsPerBlock) - 1L;
		this.blockdata = new long[Utils.ceilToBase(blocks * bitsPerBlock, 64) / 64];
	}

	public void setBlockState(int index, int blockstate) {
		final int bitStartIndex = index * this.bitsPerBlock;
		final int arrStartIndex = bitStartIndex >> 6;
		final int arrEndIndex = ((bitStartIndex + bitsPerBlock) - 1) >> 6;
		final int localStartBitIndex = bitStartIndex & 63;
		this.blockdata[arrStartIndex] = ((this.blockdata[arrStartIndex] & ~(this.singleValMask << localStartBitIndex)) | ((blockstate & this.singleValMask) << localStartBitIndex));
		if (arrStartIndex != arrEndIndex) {
			final int thisPartSift = 64 - localStartBitIndex;
			final int otherPartShift = this.bitsPerBlock - thisPartSift;
			this.blockdata[arrEndIndex] = (((this.blockdata[arrEndIndex] >>> otherPartShift) << otherPartShift) | ((blockstate & this.singleValMask) >> thisPartSift));
		}
	}

	public long[] getBlockData() {
		return blockdata;
	}

}
