package protocolsupport.protocol.utils.minecraftdata;

import java.util.Arrays;
import java.util.stream.Stream;

import org.bukkit.Material;

public class MinecraftData {

	public static final int ITEM_COUNT = (int) getItems().count();

	@SuppressWarnings("deprecation")
	public static Stream<Material> getItems() {
		return
			Arrays.stream(Material.values())
			.filter(mat -> !mat.isLegacy())
			.filter(Material::isItem);
	}

}
