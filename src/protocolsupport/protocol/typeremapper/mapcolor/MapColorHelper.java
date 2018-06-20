package protocolsupport.protocol.typeremapper.mapcolor;

import java.util.Arrays;

public class MapColorHelper {

	public static IMapColor getSimilarModernColor(ModernMapColor color, int maxMapColorId) {
		return getSimilarMapColor(color, ModernMapColor.values(), ModernMapColor.Color4.getId(), maxMapColorId);
	}

	public static IMapColor getSimilarLegacyColor(ModernMapColor color) {
		return getSimilarMapColor(color, LegacyMapColor.values(), LegacyMapColor.Color4.getId(), LegacyMapColor.Color55.getId());
	}

	private static IMapColor getSimilarMapColor(IMapColor color, IMapColor[] colors, int startId, int endId) {
		IMapColor newcolor = null;
		long mindiff = Integer.MAX_VALUE;
		IMapColor[] search = Arrays.copyOfRange(colors, startId, endId + 1);
		for (IMapColor ccolor : search) {
			long diff = getDiff(color, ccolor);
			if (diff < mindiff) {
				mindiff = diff;
				newcolor = ccolor;
			}
		}
		return newcolor;
	}

	private static long getDiff(IMapColor color1, IMapColor color2) {
		return getDiff(color1.getRed(), color2.getRed(), color1.getGreen(), color2.getGreen(), color1.getBlue(), color2.getBlue());
	}

	private static long getDiff(int r1, int r2, int g1, int g2, int b1, int b2) {
		long rmean = (r1 + r2) / 2;
		long r = r1 - r2;
		long g = g1 - g2;
		long b = b1 - b2;
		return (((512 + rmean) * r * r) >> 8) + (4 * g * g) + (((767 - rmean) * b * b) >> 8);
	}

}
