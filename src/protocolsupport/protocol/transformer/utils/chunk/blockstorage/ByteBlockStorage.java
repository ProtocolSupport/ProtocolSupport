package protocolsupport.protocol.transformer.utils.chunk.blockstorage;

import io.netty.buffer.ByteBuf;

public class ByteBlockStorage extends BlockStorage {

	private final byte[] blocks = new byte[4096];

	protected ByteBlockStorage() {
		super(-1);
	}

	@Override
	public void readFromStream(ByteBuf stream) {
		stream.readBytes(blocks);
	}

	@Override
	public int getPaletteIndex(int blockIndex) {
		return blocks[((blockIndex >> 3) << 3) | (7 - (blockIndex & 7))];
	}

}