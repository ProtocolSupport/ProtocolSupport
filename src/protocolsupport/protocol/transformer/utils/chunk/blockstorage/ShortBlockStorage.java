package protocolsupport.protocol.transformer.utils.chunk.blockstorage;

import io.netty.buffer.ByteBuf;

public class ShortBlockStorage extends BlockStorage {

	private final byte[] blocks = new byte[8192];

	protected ShortBlockStorage() {
		super(-1);
	}

	@Override
	public void readFromStream(ByteBuf stream) {
		stream.readBytes(blocks);
	}

	@Override
	public int getPaletteIndex(int blockIndex) {
		int sIndex = blockIndex << 1;
		int index = (((sIndex >> 4) << 4) | (15 - (sIndex & 15)));
		return (blocks[index] << 8) | blocks[index];
	}

}
