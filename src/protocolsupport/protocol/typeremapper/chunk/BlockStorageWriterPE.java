package protocolsupport.protocol.typeremapper.chunk;

import protocolsupport.protocol.types.chunk.ChunkConstants;

public class BlockStorageWriterPE {

	public final int[] blockdata;
	public final int bitsPerBlock;
	public final int blocksPerWord;
	public final int singleValMask;

	public BlockStorageWriterPE(int bitsPerBlock) {
		assert(bitsPerBlock <= Integer.SIZE);
		this.bitsPerBlock = bitsPerBlock;
		blocksPerWord = Integer.SIZE / bitsPerBlock;
		blockdata = new int[(ChunkConstants.BLOCKS_IN_SECTION + blocksPerWord - 1) / blocksPerWord];
		singleValMask = (1 << bitsPerBlock) - 1;
	}

	public void setBlockState(int index, int blockstate) {
		final int arrIndex = index / blocksPerWord;
		final int bitIndex = (index % blocksPerWord) * bitsPerBlock;
		if (singleValMask >> bitsPerBlock != 0) {
			throw new IllegalArgumentException("Block ID too large for palette's bitsPerBlock");
		}
		blockdata[arrIndex] = ((blockdata[arrIndex] & ~(singleValMask << bitIndex)) | ((blockstate & singleValMask) << bitIndex));
	}

	public int[] getBlockData() {
		return blockdata;
	}

}
