package protocolsupport.protocol.utils.minecraftdata;

import java.util.Arrays;
import java.util.stream.Stream;

import org.bukkit.Material;

import protocolsupport.api.MaterialAPI;

public class MinecraftData {

	public static String getResourcePath(String name) {
		return ("data/" + name);
	}

	public static final int ITEM_COUNT = (int) getItems().count();

	@SuppressWarnings("deprecation")
	public static final int BLOCKDATA_COUNT =
		Arrays.stream(Material.values())
		.filter(mat -> !mat.isLegacy())
		.filter(Material::isBlock)
		.mapToInt(material -> MaterialAPI.getBlockDataList(material).size())
		.sum();

	@SuppressWarnings("deprecation")
	public static Stream<Material> getItems() {
		return
			Arrays.stream(Material.values())
			.filter(mat -> !mat.isLegacy())
			.filter(Material::isItem);
	}

}
