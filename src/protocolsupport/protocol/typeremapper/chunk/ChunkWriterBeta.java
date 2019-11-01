package protocolsupport.protocol.typeremapper.chunk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unimi.dsi.fastutil.ints.IntConsumer;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunkSectionBlockStorage;
import protocolsupport.protocol.typeremapper.block.BlockRemappingHelper;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.utils.BitUtils;

public class ChunkWriterBeta {

	public static class ChunkUpdateData {
		protected final int sectionStart;
		protected final int sectionCount;
		protected final byte[] data;
		public ChunkUpdateData(int sectionStart, int sectionCount, byte[] data) {
			this.sectionStart = sectionStart;
			this.sectionCount = sectionCount;
			this.data = data;
		}
		public int getSectionStart() {
			return sectionStart;
		}
		public int getSectionCount() {
			return sectionCount;
		}
		public byte[] getData() {
			return data;
		}
	}

	protected static final int OFFSET_BLOCKDATA = 4096;
	protected static final int OFFSET_BLOCKLIGHT = OFFSET_BLOCKDATA + 2048;
	protected static final int OFFSET_SKYLIGHT = OFFSET_BLOCKLIGHT + 2048;
	protected static final int SECTION_LENGTH = OFFSET_SKYLIGHT + 2048;

	public static List<ChunkUpdateData> serializeChunk(
		boolean full, int mask,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		CachedChunk chunk, boolean hasSkyLight,
		IntConsumer sectionPresentConsumer
	) {
		int highestSectionNumber = Math.min(8, Integer.SIZE - Integer.numberOfLeadingZeros(mask));

		if (full) {
			int sizeY = highestSectionNumber << 4;

			byte[] data = new byte[SECTION_LENGTH * highestSectionNumber];

			int blockDataIndex = OFFSET_BLOCKDATA * highestSectionNumber;
			int blockLightIndex = OFFSET_BLOCKLIGHT * highestSectionNumber;
			int skyLightIndex = OFFSET_SKYLIGHT * highestSectionNumber;

			for (int sectionNumber = 0; sectionNumber < highestSectionNumber; sectionNumber++) {
				if (BitUtils.isIBitSet(mask, sectionNumber)) {
					transformSection(
						chunk, sectionNumber, hasSkyLight,
						blockDataRemappingTable,
						data, sizeY, sectionNumber << 4,
						blockDataIndex, blockLightIndex, skyLightIndex
					);
					sectionPresentConsumer.accept(sectionNumber);
				}
			}

			return Collections.singletonList(new ChunkUpdateData(0, highestSectionNumber, data));
		} else {
			List<ChunkUpdateData> chunkdatas = new ArrayList<>();

			for (int sectionNumber = 0; sectionNumber < highestSectionNumber; sectionNumber++) {
				if (BitUtils.isIBitSet(mask, sectionNumber)) {
					byte[] data = new byte[SECTION_LENGTH];
					transformSection(
						chunk, sectionNumber, hasSkyLight,
						blockDataRemappingTable,
						data, 16, 0,
						OFFSET_BLOCKDATA, OFFSET_BLOCKLIGHT, OFFSET_SKYLIGHT
					);
					chunkdatas.add(new ChunkUpdateData(sectionNumber, 1, data));
					sectionPresentConsumer.accept(sectionNumber);
				}
			}

			return chunkdatas;
		}
	}

	protected static void transformSection(
		CachedChunk section, int sectionNumber, boolean hasSkyLight,
		ArrayBasedIdRemappingTable blockDataRemappingTable,
		byte[] target, int betaSizeY, int betaYOffset,
		int betaBlockDataIndex, int betaBlockLightIndex, int betaSkyLightIndex
	) {
		CachedChunkSectionBlockStorage storage = section.getBlocksSection(sectionNumber);
		byte[] blocklight = section.getBlockLight(sectionNumber);
		byte[] skylight = section.getSkyLight(sectionNumber);

		for (int x = 0; x < 16; x++) {
			int betaX = (x * 16 * betaSizeY);
			boolean blockIndexEven = (x & 1) == 0;
			for (int z = 0; z < 16; z++) {
				int betaZ = (z * betaSizeY);
				for (int y = 0; y < 16; y += 2) {
					int betaIndex = betaX + betaZ + betaYOffset + y;
					int betaNibbleIndex = betaIndex >> 1;

					int blockdataIndex1 = getBlockIndex(x, y, z);
					int blockdataIndex2 = getBlockIndex(x, y + 1, z);
					int blockdata1 = BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, storage.getBlockData(blockdataIndex1));
					int blockdata2 = BlockRemappingHelper.remapPreFlatteningBlockDataNormal(blockDataRemappingTable, storage.getBlockData(blockdataIndex2));
					target[betaIndex] = (byte) PreFlatteningBlockIdData.getIdFromCombinedId(blockdata1);
					target[betaIndex + 1] = (byte) PreFlatteningBlockIdData.getIdFromCombinedId(blockdata2);
					target[betaBlockDataIndex + betaNibbleIndex] = (byte) (
						(PreFlatteningBlockIdData.getDataFromCombinedId(blockdata2) << 4) |
						PreFlatteningBlockIdData.getDataFromCombinedId(blockdata1)
					);
					if (blocklight != null) {
						target[betaBlockLightIndex + betaNibbleIndex] = (byte) (
							(getNibbleVal(blocklight[blockdataIndex2 >> 1], blockIndexEven) << 4) |
							getNibbleVal(blocklight[blockdataIndex1 >> 1], blockIndexEven)
						);
					}
					if (hasSkyLight && (skylight != null)) {
						target[betaSkyLightIndex + betaNibbleIndex] = (byte) (
							(getNibbleVal(skylight[blockdataIndex2 >> 1], blockIndexEven) << 4) |
							getNibbleVal(skylight[blockdataIndex1 >> 1], blockIndexEven)
						);
					}
				}
			}
		}
	}

	protected static int getNibbleVal(int val, boolean lower) {
		return lower ? val & 0xF : val >> 4;
	}

	protected static int getBlockIndex(int x, int y, int z) {
		return (y << 8) | (z << 4) | x;
	}

}
