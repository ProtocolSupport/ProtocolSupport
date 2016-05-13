package protocolsupport.protocol.legacyremapper.chunk.blockstorage;

import io.netty.buffer.ByteBuf;

public class ByteBlockStorage extends BlockStorage {

	private final byte[] blocks = new byte[4096];

	protected ByteBlockStorage(int[] palette) {
		super(palette, Byte.SIZE);
	}

	@Override
	public void readFromStream(ByteBuf stream) {
		stream.readBytes(blocks);
	}

	@Override
	public int getPaletteIndex(int blockIndex) {
		return blocks[blockIndex ^ 0b111] & 0xFF;
	}

}