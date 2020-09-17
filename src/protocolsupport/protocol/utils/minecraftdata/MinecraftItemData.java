package protocolsupport.protocol.utils.minecraftdata;

import org.bukkit.NamespacedKey;

import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.utils.JsonUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MinecraftItemData {

	protected static final Object2IntMap<String> nameToId = new Object2IntOpenHashMap<>();

	protected static void register(String name, int id) {
		nameToId.put(name, id);
		nameToId.put(NamespacedKey.minecraft(name).toString(), id);
	}

	static {
		JsonObject rootObject = MinecraftDataResourceUtils.getResourceAsJsonObject("item.json");
		for (String entityNameString : rootObject.keySet()) {
			register(entityNameString, JsonUtils.getInt(rootObject, entityNameString));
		}
	}

	public static int getIdByName(String id) {
		return nameToId.getOrDefault(id, -1);
	}

}
