package protocolsupport.protocol.types.chunk;

public class BlocksSection {

	protected int blockCount;
	protected final short[] blockdata = new short[ChunkConstants.BLOCKS_IN_SECTION];

	public BlocksSection() {
	}

	public BlocksSection(int blockCount) {
		this.blockCount =  blockCount;
	}

	public int getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}

	public int getBlockData(int index) {
		return blockdata[index];
	}

	public void setBlockData(int index, int blockdata) {
		this.blockdata[index] = (short) blockdata;
	}

}
