package protocolsupport.protocol.typeremapper.chunk;

public class BlockStorageWriterPE {

	private static final int bitsPerWord = 4 * 8;
	private final int[] blockdata;
	private final int bitsPerBlock;
	private final double blocksPerWord;
	private final int singleValMask;

	public BlockStorageWriterPE(int bitsPerBlock, int blocks) {
		this.bitsPerBlock = bitsPerBlock;
		this.blocksPerWord = Math.floor(bitsPerWord / (double) bitsPerBlock);
		this.blockdata = new int[(int) Math.ceil(blocks / this.blocksPerWord)];
		this.singleValMask = (1 << bitsPerBlock) - 1;
	}

	public void setBlockState(int index, int blockstate) {
		final int arrIndex = (int) (index / blocksPerWord);
		final int bitIndex =  ((int) ((index % blocksPerWord))) * bitsPerBlock;
		this.blockdata[arrIndex] = ((this.blockdata[arrIndex] & ~(this.singleValMask << bitIndex)) | ((blockstate & this.singleValMask) << bitIndex));
	}

	public int[] getBlockData() {
		return blockdata;
	}

}
