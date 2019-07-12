package protocolsupport.protocol.typeremapper.chunk;

import io.netty.buffer.ByteBuf;

import protocolsupport.protocol.types.chunk.ChunkConstants;

public class BlockStorageWriterPE {

	protected static final int FLAG_RUNTIME = 1;
	protected static final int EMPTY_SUBCHUNK_BYTES = ChunkConstants.BLOCKS_IN_SECTION / 8;

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

	public void writeTo(ByteBuf to) {
		to.writeByte(storageHeader(bitsPerBlock));
		for (int word : blockdata) {
			to.writeIntLE(word);
		}
	}

	public static void writeEmpty(ByteBuf to) {
		to.writeByte(storageHeader(1));
		to.writeZero(EMPTY_SUBCHUNK_BYTES);
	}

	protected static final int storageHeader(int bitsPerBlock) {
		return (bitsPerBlock << 1) | FLAG_RUNTIME;
	}

}
