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

	public static int getBlockStateFromObjData(int objectdata) {
		return ((objectdata & 0xFFF) << 4) | (objectdata >> 12);
	}

	public static int getObjDataFromBlockState(int blockstate) {
		return (blockstate >> 4) | ((blockstate & 0xF) << 12);
	}

	@SuppressWarnings("deprecation")
	public static Stream<Material> getItems() {
		return
			Arrays.stream(Material.values())
			.filter(mat -> !mat.isLegacy())
			.filter(Material::isItem);
	}

}
