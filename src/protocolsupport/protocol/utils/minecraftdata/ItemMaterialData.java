package protocolsupport.protocol.utils.minecraftdata;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class ItemMaterialData {

	private static final Map<String, Material> byKey = new HashMap<>();
	private static final ArrayMap<Material> byRuntimeId = new ArrayMap<>(MinecraftData.ID_MAX);
	private static final Object2IntMap<Material> toRuntimeId = new Object2IntOpenHashMap<>();

	static {
		toRuntimeId.defaultReturnValue(-1);
		for (JsonElement element : MinecraftData.iterateJsonArrayResource("itemmaterials.json")) {
			JsonObject object = element.getAsJsonObject();
			Material material = Material.getMaterial(JsonUtils.getString(object, "material"));
			int id = JsonUtils.getInt(object, "id");
			byKey.put(material.getKey().getKey(), material);
			byKey.put(material.getKey().toString(), material);
			byRuntimeId.put(id, material);
			toRuntimeId.put(material, id);
		}
	}

	public static Material getByKey(String key) {
		return byKey.get(key);
	}

	public static Material getByRuntimeId(int runtimeId) {
		return byRuntimeId.get(runtimeId);
	}

	public static int getRuntimeId(Material material) {
		return toRuntimeId.getInt(material);
	}

}
