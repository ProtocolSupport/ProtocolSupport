package protocolsupport.protocol.legacyremapper.chunk.blockstorage;

import io.netty.buffer.ByteBuf;

public class BitsBlockStorage extends BlockStorage {

	private final long[] blocks;
	private final int singleValMask;

	public BitsBlockStorage(int[] palette, int bitsPerBlock, int dataLength) {
		super(palette, bitsPerBlock);
		this.blocks = new long[dataLength];
		this.singleValMask = (1 << bitsPerBlock) - 1;
	}

	@Override
	public void readFromStream(ByteBuf stream) {
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = stream.readLong();
		}
	}

	@Override
	public int getPaletteIndex(int blockIndex) {
		int bitStartIndex = blockIndex * bitsPerBlock;
		int arrStartIndex = bitStartIndex >> 6;
		int arrEndIndex = ((bitStartIndex + bitsPerBlock) - 1) >> 6;
		int localStartBitIndex = bitStartIndex & 63;
		if (arrStartIndex == arrEndIndex) {
			return (int) ((this.blocks[arrStartIndex] >>> localStartBitIndex) & this.singleValMask);
		} else {
			return (int) (((this.blocks[arrStartIndex] >>> localStartBitIndex) | (this.blocks[arrEndIndex] << (64 - localStartBitIndex))) & this.singleValMask);
		}
	}

}
