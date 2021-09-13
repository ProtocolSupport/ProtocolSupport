package protocolsupport.protocol.typeremapper.mapcolor;

import java.util.Arrays;

import protocolsupport.protocol.typeremapper.utils.MappingRegistry.IntMappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable.ArrayBasedIntMappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.MiscUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MapColorMappingRegistry extends IntMappingRegistry<ArrayBasedIntMappingTable> {

	public static final MapColorMappingRegistry INSTANCE = new MapColorMappingRegistry();

	public MapColorMappingRegistry() {
		IMapColor[] colors = getAllMapColors(ModernBaseMapColor.values(), new int[] {180, 220, 255, 135});
		IMapColor[] legacyColors = getAllMapColors(LegacyBaseMapColor.values(), new int[] {180, 220, 255, 220});

		for (IMapColor color : colors) {
			if (color.getId() > 231) {
				register(color.getId(), getSimilarMapColor(color, colors, 231 - 4).getId(), ProtocolVersionsHelper.DOWN_1_16_4);
			}
			if (color.getId() > 203) {
				register(color.getId(), getSimilarMapColor(color, colors, 203 - 4).getId(), ProtocolVersionsHelper.DOWN_1_15_2);
			}
			if (color.getId() > 143) {
				register(color.getId(), getSimilarMapColor(color, colors, 143 - 4).getId(), ProtocolVersionsHelper.DOWN_1_11_1);
			}
			register(color.getId(), getSimilarMapColor(color, legacyColors, legacyColors.length - 1).getId(), ProtocolVersionsHelper.DOWN_1_6_4);
		}
	}

	protected static IMapColor getSimilarMapColor(IMapColor color, IMapColor[] availableColors, int endId) {
		IMapColor resultColor = availableColors[0];
		long minDiff = getDiff(color, resultColor);
		for (int i = 1; i <= endId; i++) {
			IMapColor availableColor = availableColors[i];
			long diff = getDiff(color, availableColor);
			if (diff < minDiff) {
				resultColor = availableColor;
				minDiff = diff;
			}
		}
		return resultColor;
	}

	protected static long getDiff(IMapColor color1, IMapColor color2) {
		return MiscUtils.getColorDiff(color1.getRed(), color2.getRed(), color1.getGreen(), color2.getGreen(), color1.getBlue(), color2.getBlue());
	}

	@Override
	protected ArrayBasedIntMappingTable createTable() {
		return new ArrayBasedIntMappingTable(256);
	}

	protected static IMapColor[] getAllMapColors(IMapColor[] baseColors, int[] multipliers) {
		return
			Arrays.stream(baseColors)
			.skip(1)
			.flatMap(baseColor -> {
				int r = baseColor.getRed();
				int g = baseColor.getGreen();
				int b = baseColor.getBlue();
				int colorsLength = multipliers.length;
				int idOffset = baseColor.getId() * colorsLength;
				IMapColor[] colors = new IMapColor[colorsLength];
				for (int i = 0; i < colorsLength; i++) {
					int multiplier = multipliers[i];
					colors[i] = new MapColor(idOffset + i, computeColor(r, multiplier), computeColor(g, multiplier), computeColor(b, multiplier));
				}
				return Arrays.stream(colors);
			})
			.toArray(IMapColor[]::new);
	}

	protected static int computeColor(int colorComponent, int multiplier) {
		return (colorComponent * multiplier) / 255;
	}

}
