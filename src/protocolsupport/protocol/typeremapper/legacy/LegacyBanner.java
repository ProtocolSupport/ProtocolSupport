package protocolsupport.protocol.typeremapper.legacy;

import java.util.EnumMap;

import org.bukkit.Material;

import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTInt;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyBanner {

	public static void transformBanner(NBTCompound tag) {
		NBTList<NBTCompound> patterns = tag.getTagListOfTypeOrNull(CommonNBT.BANNER_PATTERNS, NBTType.COMPOUND);
		if (patterns != null) {
			for (NBTCompound pattern : patterns.getTags()) {
				pattern.setTag(CommonNBT.BANNER_PATTERN_COLOR, new NBTInt(15 - pattern.getNumberTagOrThrow(CommonNBT.BANNER_PATTERN_COLOR).getAsInt()));
			}
		}
	}

	protected static final int[] colorToMaterial = new int[16];
	protected static final EnumMap<Material, Integer> materialToColor = new EnumMap<>(Material.class);

	protected static void register(Material material, int color) {
		colorToMaterial[color] = ItemMaterialLookup.getRuntimeId(material);
		materialToColor.put(material, color);
	}

	static {
		register(Material.WHITE_BANNER, 15);
		register(Material.ORANGE_BANNER, 14);
		register(Material.MAGENTA_BANNER, 13);
		register(Material.LIGHT_BLUE_BANNER, 12);
		register(Material.YELLOW_BANNER, 11);
		register(Material.LIME_BANNER, 10);
		register(Material.PINK_BANNER, 9);
		register(Material.GRAY_BANNER, 8);
		register(Material.LIGHT_GRAY_BANNER, 7);
		register(Material.CYAN_BANNER, 6);
		register(Material.PURPLE_BANNER, 5);
		register(Material.BLUE_BANNER, 4);
		register(Material.BROWN_BANNER, 3);
		register(Material.GREEN_BANNER, 2);
		register(Material.RED_BANNER, 1);
		register(Material.BLACK_BANNER, 0);
	}

	public static int getMaterialByColor(int color) {
		return colorToMaterial[color];
	}

	public static Integer getColorByMaterial(Material material) {
		return materialToColor.get(material);
	}

}
