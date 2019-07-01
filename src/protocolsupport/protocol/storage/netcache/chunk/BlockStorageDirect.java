package protocolsupport.protocol.storage.netcache.chunk;

import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.Utils;

public class BlockStorageDirect extends BlockStorage {

	protected final long[] blockdata;
	protected final int bitsPerBlock;
	private final int singleValMask;

	public BlockStorageDirect() {
		this.blockdata = new long[Utils.ceilToBase(ChunkConstants.BLOCKS_IN_SECTION * ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK, 64) / 64];
		this.bitsPerBlock = ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK;
		this.singleValMask = (1 << bitsPerBlock) - 1;
	}

	public BlockStorageDirect(long[] data) {
		this.blockdata = data;
		this.bitsPerBlock = ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK;
		this.singleValMask = (1 << bitsPerBlock) - 1;
	}

	@Override
	public short getBlockData(int blockIndex) {
		int bitStartIndex = blockIndex * bitsPerBlock;
		int arrStartIndex = bitStartIndex >> 6;
		int arrEndIndex = ((bitStartIndex + bitsPerBlock) - 1) >> 6;
		int localStartBitIndex = bitStartIndex & 63;
		if (arrStartIndex == arrEndIndex) {
			return (short) ((this.blockdata[arrStartIndex] >>> localStartBitIndex) & this.singleValMask);
		} else {
			return (short) (((this.blockdata[arrStartIndex] >>> localStartBitIndex) | (this.blockdata[arrEndIndex] << (64 - localStartBitIndex))) & this.singleValMask);
		}
	}

	@Override
	public void setBlockData(int index, short blockstate) {
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

}