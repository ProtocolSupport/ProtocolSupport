package protocolsupport.protocol.types.chunk;

import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData;
import protocolsupport.utils.MiscUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ChunkConstants {

	private ChunkConstants() {
	}

	public static final byte PALETTED_STORAGE_BLOCKS_GLOBAL_BITS = (byte) MiscUtils.ceilLog2(MinecraftBlockData.BLOCK_COUNT);

	public static final int SECTION_BLOCK_COUNT = 16 * 16 * 16;
	public static final int SECTION_BIOME_COUNT = SECTION_BLOCK_COUNT / (4 * 4 * 4);

	public static final int LIGHT_DATA_LENGTH = SECTION_BLOCK_COUNT / 2;

	public static final int LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS = 16;
	public static final int LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK = LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS << 4;
	public static final int LEGACY_LIMITED_HEIGHT_CHUNK_LIGHT_SECTIONS = 18;

}
