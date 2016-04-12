package protocolsupport.protocol.transformer.utils.chunk.blockstorage;

import io.netty.buffer.ByteBuf;

public class ShortBlockStorage extends BlockStorage {

	private static final int singleValMask = (1 << Short.SIZE) - 1;

	private final long[] blocks;

	public ShortBlockStorage(int[] palette, int dataLength) {
		super(palette, Short.SIZE);
		this.blocks = new long[dataLength];
	}

	@Override
	public void readFromStream(ByteBuf stream) {
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = stream.readLong();
		}
	}

	@Override
	public int getPaletteIndex(int blockIndex) {
		int bitStartIndex = blockIndex << 4;
		return (int) ((this.blocks[bitStartIndex >> 6] >>> (bitStartIndex & 63)) & singleValMask);
	}

}
