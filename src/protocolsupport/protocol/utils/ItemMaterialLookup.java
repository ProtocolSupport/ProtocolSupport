package protocolsupport.protocol.utils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.protocol.utils.minecraftdata.MinecraftItemData;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ItemMaterialLookup {

	private ItemMaterialLookup() {
	}

	private static final Map<String, Material> byKey = new HashMap<>();
	private static final ArrayMap<Material> byRuntimeId = new ArrayMap<>(MinecraftItemData.ITEM_COUNT);
	private static final Object2IntMap<Material> toRuntimeId = new Object2IntOpenHashMap<>();

	static {
		toRuntimeId.defaultReturnValue(-1);
		MinecraftItemData.getItems()
		.forEach(material -> {
			int id = MinecraftItemData.getIdByName(material.getKey().toString());
			if (id != -1) {
				byKey.put(material.getKey().getKey(), material);
				byKey.put(material.getKey().toString(), material);
				byRuntimeId.put(id, material);
				toRuntimeId.put(material, id);
			}
		});
	}

	public static @Nullable Material getByKey(@Nonnull String key) {
		return byKey.get(key);
	}

	public static @Nullable Material getByRuntimeId(@Nonnegative int runtimeId) {
		return byRuntimeId.get(runtimeId);
	}

	public static @CheckForSigned int getRuntimeId(@Nonnull Material material) {
		return toRuntimeId.getInt(material);
	}

}
