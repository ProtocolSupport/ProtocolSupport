package protocolsupport.protocol.types.chunk;

import protocolsupport.protocol.utils.minecraftdata.MinecraftData;

public class ChunkConstants {

	public static final int GLOBAL_PALETTE_BITS_PER_BLOCK = 14;
	public static final short[] GLOBAL_PALETTE = new short[MinecraftData.BLOCKDATA_COUNT];
	static {
		for (int i = 0; i < GLOBAL_PALETTE.length; i++) {
			GLOBAL_PALETTE[i] = (short) i;
		}
	}

	public static final int SECTION_COUNT_BLOCKS = 16;
	public static final int SECTION_COUNT_LIGHT = 18;

	public static final int BLOCKS_IN_SECTION = 16 * 16 * 16;

	public static final int LIGHT_DATA_LENGTH = BLOCKS_IN_SECTION / 2;

}
