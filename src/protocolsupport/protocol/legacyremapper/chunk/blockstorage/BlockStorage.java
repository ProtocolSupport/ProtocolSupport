package protocolsupport.protocol.legacyremapper.chunk.blockstorage;

import io.netty.buffer.ByteBuf;

public abstract class BlockStorage {

	public static BlockStorage create(int[] palette, int bitsPerBlock, int dataLength) {
		switch (bitsPerBlock) {
			case 4: {
				return new NibbleBlockStorage(palette, dataLength);
			}
			case 8: {
				return new ByteBlockStorage(palette);
			}
			default: {
				return new BitsBlockStorage(palette, bitsPerBlock, dataLength);
			}
		}
	}

	protected final int bitsPerBlock;
	private final int[] palette;
	protected BlockStorage(int[] palette, int bitsPerBlock) {
		this.palette = palette;
		this.bitsPerBlock = bitsPerBlock;
	}

	public abstract void readFromStream(ByteBuf stream);

	public int getBlockState(int blockindex) {
		return palette[getPaletteIndex(blockindex)];
	}

	protected abstract int getPaletteIndex(int blockIndex);

}