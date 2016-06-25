package protocolsupport.protocol.legacyremapper.chunk.blockstorage;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_10_R1.DataBits;

public class DataBitsBlockStorage extends BlockStorage {

	private final DataBits databits;
	protected DataBitsBlockStorage(int[] palette, int bitsPerBlock) {
		super(palette, bitsPerBlock);
		databits = new DataBits(bitsPerBlock, 4096);
	}

	@Override
	public void readFromStream(ByteBuf stream) {
		long[] data = databits.a();
		for (int i = 0; i < data.length; i++) {
			data[i] = stream.readLong();
		}
	}

	@Override
	protected int getPaletteIndex(int blockIndex) {
		return databits.a(blockIndex);
	}

}
