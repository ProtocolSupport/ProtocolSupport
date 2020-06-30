package protocolsupport.protocol.typeremapper.mapcolor;

import java.util.Arrays;

import protocolsupport.utils.Utils;

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
		return Utils.getColorDiff(color1.getRed(), color2.getRed(), color1.getGreen(), color2.getGreen(), color1.getBlue(), color2.getBlue());
	}

}
