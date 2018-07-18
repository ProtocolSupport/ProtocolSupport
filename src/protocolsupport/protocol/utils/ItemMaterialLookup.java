package protocolsupport.protocol.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.zplatform.ServerPlatform;

@SuppressWarnings("deprecation")
public class ItemMaterialLookup {

	private static final Map<String, Material> byKey = new HashMap<>();
	private static final ArrayMap<Material> byRuntimeId = new ArrayMap<>(MinecraftData.ID_MAX);
	private static final Object2IntMap<Material> toRuntimeId = new Object2IntOpenHashMap<>();

	static {
		toRuntimeId.defaultReturnValue(-1);
		Arrays.stream(Material.values())
		.filter(m -> !m.isLegacy())
		.forEach(material -> {
			int id = ServerPlatform.get().getMiscUtils().getNetworkItemId(material);
			if (id != -1) {
				byKey.put(material.getKey().getKey(), material);
				byKey.put(material.getKey().toString(), material);
				byRuntimeId.put(id, material);
				toRuntimeId.put(material, id);
			}
		});
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
