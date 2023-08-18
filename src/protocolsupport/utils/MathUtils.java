package protocolsupport.utils;

public class MathUtils {

	public static int ceilDiv(int value, int base) {
		return -Math.floorDiv(-value, base);
	}

}
