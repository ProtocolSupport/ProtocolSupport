package protocolsupport.protocol.typeremapper.chunk;

public class BlockStorageWriterPE {

	private static final int bitsPerWord = 4 * 8;
	private final int[] blockdata;
	private final int bitsPerBlock;
	private final int blocksPerWord;
	private final int singleValMask;

	public BlockStorageWriterPE(int bitsPerBlock, int blocks) {
		assert(bitsPerBlock <= bitsPerWord);
		this.bitsPerBlock = bitsPerBlock;
		this.blocksPerWord = bitsPerWord / bitsPerBlock;
		int blockdataSize = (int) Math.ceil(blocks / (double) this.blocksPerWord);
		this.blockdata = new int[blockdataSize];
		this.singleValMask = (1 << bitsPerBlock) - 1;
	}

	public void setBlockState(int index, int blockstate) {
		final int arrIndex = index / blocksPerWord;
		final int bitIndex =  (index % blocksPerWord) * bitsPerBlock;
		this.blockdata[arrIndex] = ((this.blockdata[arrIndex] & ~(this.singleValMask << bitIndex)) | ((blockstate & this.singleValMask) << bitIndex));
	}

	public int[] getBlockData() {
		return blockdata;
	}

}
