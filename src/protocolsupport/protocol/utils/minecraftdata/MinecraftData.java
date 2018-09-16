package protocolsupport.protocol.utils.minecraftdata;

import java.util.Arrays;

import org.bukkit.Material;

import protocolsupport.api.MaterialAPI;

public class MinecraftData {

	public static String getResourcePath(String name) {
		return ("data/" + name);
	}

	public static final int ITEM_COUNT =
		(int) Arrays.stream(Material.values())
		.filter(Material::isItem)
		.count();

	public static final int BLOCKDATA_COUNT =
		Arrays.stream(Material.values())
		.filter(Material::isBlock)
		.mapToInt(material -> MaterialAPI.getBlockDataList(material).size())
		.sum();

	public static int getBlockStateFromObjData(int objectdata) {
		return ((objectdata & 0xFFF) << 4) | (objectdata >> 12);
	}

	public static int getObjDataFromBlockState(int blockstate) {
		return (blockstate >> 4) | ((blockstate & 0xF) << 12);
	}

}
