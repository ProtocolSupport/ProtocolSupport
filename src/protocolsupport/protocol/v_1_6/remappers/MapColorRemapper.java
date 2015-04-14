package protocolsupport.protocol.v_1_6.remappers;

public class MapColorRemapper {

	private static byte[] replacements = new byte[64];

	static {
		for (int i = 0; i < replacements.length; i++) {
			replacements[i] = -1;
		}
		//see http://minecraft.gamepedia.com/Map_item_format
		replacements[14] = 8;
		replacements[15] = 10;
		replacements[16] = 5;
		replacements[17] = 5;
		replacements[18] = 2;
		replacements[19] = 1;
		replacements[20] = 4;
		replacements[21] = 11;
		replacements[22] = 11;
		replacements[23] = 5;
		replacements[24] = 5;
		replacements[25] = 5;
		replacements[26] = 10;
		replacements[27] = 7;
		replacements[28] = 4;
		replacements[29] = 11;
		replacements[30] = 2;
		replacements[31] = 5;
		replacements[32] = 5;
		replacements[33] = 7;
		replacements[34] = 10;
		replacements[35] = 4;
		replacements[36] = 10;
	}

	public static byte replaceMapColor(byte mapColor) {
		int realColor = (mapColor & 0xFF) >> 2;
		if (replacements[realColor] != -1) {
			return (byte) ((replacements[realColor] << 2) + (mapColor & 3));
		}
		return mapColor;
	}

}
