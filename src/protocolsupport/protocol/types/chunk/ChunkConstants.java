package protocolsupport.protocol.types.chunk;

import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ChunkConstants {

	private ChunkConstants() {
	}

	public static final int GLOBAL_PALETTE_BITS_PER_BLOCK = 15;
	public static final short[] GLOBAL_PALETTE = new short[MinecraftBlockData.BLOCKDATA_COUNT];
	static {
		for (int i = 0; i < GLOBAL_PALETTE.length; i++) {
			GLOBAL_PALETTE[i] = (short) i;
		}
	}

	public static final int BLOCKS_IN_SECTION = 16 * 16 * 16;

	public static final int LIGHT_DATA_LENGTH = BLOCKS_IN_SECTION / 2;

	public static final int LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS = 16;
	public static final int LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS = 18;

}
