package protocolsupport.protocol.typeremapper.legacy;

import java.util.Arrays;

import javax.annotation.Nonnull;

public class LegacyBiomeData {

	private LegacyBiomeData() {
	}

	public static @Nonnull int[] toLegacy1024EntryBiomeData(@Nonnull int[] biomeData) {
		if (biomeData.length != 1024) {
			return Arrays.copyOfRange(biomeData, 0, 1024);
		} else {
			return biomeData;
		}
	}

	public static @Nonnull int[] toLegacyBiomeData(@Nonnull int[] biomeData) {
		biomeData = toLegacy1024EntryBiomeData(biomeData);
		int[] legacyBiomeData = new int[256];
		for (int z = 0; z < 16; z += 4) {
			for (int x = 0; x < 16; x += 4) {
				int biomeId = getBiomeId(biomeData, x, z);
				fillLegacyBiomeData(legacyBiomeData, x, z, biomeId);
				fillLegacyBiomeData(legacyBiomeData, x, z + 1, biomeId);
				fillLegacyBiomeData(legacyBiomeData, x, z + 2, biomeId);
				fillLegacyBiomeData(legacyBiomeData, x, z + 3, biomeId);
			}
		}
		return legacyBiomeData;
	}

	protected static void fillLegacyBiomeData(@Nonnull int[] legacyBiomeData, int x, int z, int biomeId) {
		int offset = (z << 4) | x;
		Arrays.fill(legacyBiomeData, offset, offset + 4, biomeId);
	}

	/*
	 * biomeData[((y >> 2) << 4) | ((z >> 2) << 2) | ((x >> 2)]
	 * Uses y == 0, because vanilla 1.15+ only uses lowest layer for biome
	 * Transform 2 z bit shifts into mask
	 */
	protected static int getBiomeId(@Nonnull int[] biomeData, int x, int z) {
		return biomeData[(z & 0b1100) | (x >> 2)];
	}

}
