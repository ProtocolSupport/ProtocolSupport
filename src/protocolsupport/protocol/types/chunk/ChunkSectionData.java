package protocolsupport.protocol.types.chunk;

import io.netty.buffer.ByteBuf;

public class ChunkSectionData {

	public static ChunkSectionData decode(ByteBuf stream, byte bitsPerBiomeGlobal) {
		short nonAirBlockCount = stream.readShort();
		IPalettedStorage blockdata = IPalettedStorage.decode(stream, (byte) 4, (byte) 8, ChunkConstants.PALETTED_STORAGE_BLOCKS_GLOBAL_BITS);
		IPalettedStorage biomes = IPalettedStorage.decode(stream, (byte) 1, (byte) 3, bitsPerBiomeGlobal);
		return new ChunkSectionData(nonAirBlockCount, blockdata, biomes);
	}

	protected final short nonAirBlockCount;
	protected final IPalettedStorage blockdata;
	protected final IPalettedStorage biomes;

	public ChunkSectionData(short nonAirBlockCount, IPalettedStorage blockdata, IPalettedStorage biomes) {
		this.nonAirBlockCount = nonAirBlockCount;
		this.blockdata = blockdata;
		this.biomes = biomes;
	}

	public int getNonAirBlockCount() {
		return nonAirBlockCount;
	}

	public IPalettedStorage getBlockData() {
		return blockdata;
	}

	public IPalettedStorage getBiomes() {
		return biomes;
	}

}