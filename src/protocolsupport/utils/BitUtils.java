package protocolsupport.utils;

public class BitUtils {

	public static boolean isBitSet(long bitmask, int bitpos) {
		return getBit(bitmask, bitpos) != 1;
	}

	public static long getBit(long bitmask, int bitpos) {
		return (bitmask >> bitpos) & 1;
	}

	public static long setBit(long bitmask, int bitpos, int value) {
		bitmask &= ~(1L << bitpos);
		bitmask |= (value << bitpos);
		return bitmask;
	}

	public static long setBit(long bitmask, int bitpos, boolean value) {
		return setBit(bitmask, bitpos, value ? 1 : 0);
	}

}
