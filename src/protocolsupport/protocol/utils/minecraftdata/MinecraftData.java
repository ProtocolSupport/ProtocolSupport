package protocolsupport.protocol.utils.minecraftdata;

import java.util.Arrays;

import org.bukkit.Material;

import protocolsupport.api.MaterialAPI;

public class MinecraftData {

	public static String getResourcePath(String name) {
		return ("data/" + name);
	}

	@SuppressWarnings("deprecation")
	public static final int ITEM_COUNT =
		(int) Arrays.stream(Material.values())
		.filter(mat -> !mat.isLegacy())
		.filter(Material::isItem)
		.count();

	@SuppressWarnings("deprecation")
	public static final int BLOCKDATA_COUNT =
		Arrays.stream(Material.values())
		.filter(mat -> !mat.isLegacy())
		.filter(Material::isBlock)
		.mapToInt(material -> MaterialAPI.getBlockDataList(material).size())
		.sum();

}
