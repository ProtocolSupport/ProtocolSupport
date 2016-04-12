package protocolsupport.protocol.transformer.utils.chunk.blockstorage;

import io.netty.buffer.ByteBuf;

public abstract class BlockStorage {

	public static BlockStorage create(int[] palette, int bitsPerBlock, int dataLength) {
		switch (bitsPerBlock) {
			case 4: {
				return new NibbleBlockStorage(palette, dataLength);
			}
			//TODO: find a way to test it
			/*case 8: {
				return new ByteBlockStorage();
			}
			case 0:
			case 16: {
				return new ShortBlockStorage(palette, dataLength);
			}*/
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