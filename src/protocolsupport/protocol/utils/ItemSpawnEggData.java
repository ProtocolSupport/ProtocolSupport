package protocolsupport.protocol.utils;

import java.util.Arrays;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupportbuildprocessor.Preload;

@SuppressWarnings("deprecation")
@Preload
public class ItemSpawnEggData {

	protected static final NetworkEntityType[] toSpawnedType = new NetworkEntityType[MinecraftData.ITEM_COUNT];
	protected static final Object2IntMap<NetworkEntityType> fromSpawnedType = new Object2IntOpenHashMap<>();

	protected static void register(Material spawnegg, NetworkEntityType type) {
		int materialId = ItemMaterialLookup.getRuntimeId(spawnegg);
		toSpawnedType[materialId] = type;
		fromSpawnedType.put(type, materialId);
	}

	protected static final String spawnEggSuffix = "_SPAWN_EGG";

	public static Stream<Material> getSpawnEggs() {
		return MinecraftData.getItems().filter(m -> m.toString().endsWith(spawnEggSuffix));
	}

	static {
		Arrays.fill(toSpawnedType, NetworkEntityType.NONE);
		getSpawnEggs().forEach(m -> register(m, NetworkEntityType.getByBukkitType(EntityType.fromName(m.toString().replace(spawnEggSuffix, "").toLowerCase()))));
	}

	/**
	 * Returns entity type spawned by provided spawn egg material id <br>
	 * If material is not a spawn egg returns {@link NetworkEntityType#NONE}
	 * @param materialId spawn egg material id
	 * @return entity type
	 */
	public static NetworkEntityType getSpawnedType(int materialId) {
		return toSpawnedType[materialId];
	}

	/**
	 * Returns spawn egg material id for provided entity type <br>
	 * If no spawn egg exists from provided entity type returns -1
	 * @param spawnedType entity type spawned by egg
	 * @return spawn egg material id
	 */
	public static int getMaterialIdBySpawnedType(NetworkEntityType spawnedType) {
		return fromSpawnedType.getOrDefault(spawnedType, -1);
	}

}
