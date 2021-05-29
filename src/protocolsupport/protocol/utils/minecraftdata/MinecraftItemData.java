package protocolsupport.protocol.utils.minecraftdata;

import java.util.Arrays;
import java.util.stream.Stream;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.utils.JsonUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MinecraftItemData {

	private MinecraftItemData() {
	}

	public static final int ITEM_COUNT = (int) getItems().count();

	@SuppressWarnings("deprecation")
	public static Stream<Material> getItems() {
		return
			Arrays.stream(Material.values())
			.filter(mat -> !mat.isLegacy())
			.filter(Material::isItem);
	}

	private static final Object2IntMap<String> nameToId = new Object2IntOpenHashMap<>();

	private static void register(String name, int id) {
		nameToId.put(name, id);
		nameToId.put(NamespacedKey.minecraft(name).toString(), id);
	}

	static {
		JsonObject rootObject = MinecraftDataResourceUtils.getResourceAsJsonObject("item.json");
		for (String entityNameString : rootObject.keySet()) {
			register(entityNameString, JsonUtils.getInt(rootObject, entityNameString));
		}
	}

	public static @CheckForSigned int getIdByName(@Nonnull String id) {
		return nameToId.getOrDefault(id, -1);
	}

}
