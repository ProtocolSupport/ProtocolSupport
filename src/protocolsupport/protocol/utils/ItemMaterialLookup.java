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
import protocolsupportbuildprocessor.Preload;

@SuppressWarnings("deprecation")
@Preload
public class ItemMaterialLookup {

	protected static final Map<String, Material> byKey = new HashMap<>();
	protected static final ArrayMap<Material> byRuntimeId = new ArrayMap<>(MinecraftData.ITEM_COUNT);
	protected static final Object2IntMap<Material> toRuntimeId = new Object2IntOpenHashMap<>();

	static {
		toRuntimeId.defaultReturnValue(-1);
		Arrays.stream(Material.values())
		.filter(m -> !m.isLegacy())
		.forEach(material -> {
			int id = ServerPlatform.get().getMiscUtils().getItemNetworkId(material);
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
