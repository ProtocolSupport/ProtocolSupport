package protocolsupport.protocol.types;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.NamespacedKey;

import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public enum TileEntityType {

	UNKNOWN(0, "__faketype_unknown"),
	MOB_SPAWNER(1, "mob_spawner"),
	COMMAND_BLOCK(2, "command_block"),
	BEACON(3, "beacon"),
	SKULL(4, "skull"),
	CONDUIT(5, "conduit"),
	BANNER(6, "banner"),
	STRUCTURE(7, "structure_block"),
	END_GATEWAY(8, "end_gateway"),
	SIGN(9, "sign"),
	SHULKER_BOX(10, "shulker_box"),
	BED(11, "bed"),
	JIGSAW(12, "jigsaw"),
	CAMPFIRE(13, "campfire"),
	FURNACE(-1, "furnace"),
	CHEST(-1, "chest"),
	TRAPPED_CHEST(-1, "trapped_chest"),
	ENDER_CHEST(-1, "ender_chest"),
	JUKEXBOX(-1, "jukebox"),
	DISPENSER(-1, "dispenser"),
	DROPPER(-1, "dropper"),
	NOTE_BLOCK(-1, "noteblock"),
	PISTON(-1, "piston"),
	BREWING_STAND(-1, "brewing_stand"),
	ENCHANTING_TABLE(-1, "enchanting_table"),
	END_PORTAL(-1, "end_portal"),
	DAYLIGHT_DETECTOR(-1, "daylight_detector"),
	HOPPER(-1, "hopper"),
	COMPARATOR(-1, "comparator"),
	STRUCTURE_BLOCK(-1, "structure_block"),
	BARREL(-1, "barrel"),
	SMOKER(-1, "smoker"),
	BLAST_FURNACE(-1, "blast_furnace"),
	LECTERN(-1, "lectern"),
	BELL(-1, "bell");

	private static final HashMap<String, TileEntityType> by_r_id = new HashMap<>();
	private static final ArrayMap<TileEntityType> by_n_id = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(values()).filter(v -> v.networkId > 0), (v -> v.networkId));
	static {
		Arrays.stream(values()).forEach(v -> {
			by_r_id.put(v.registryId, v);
			by_r_id.put(NamespacedKey.minecraft(v.registryId).toString(), v);
		});
	}

	public static TileEntityType getByNetworkId(int id) {
		TileEntityType type = by_n_id.get(id);
		return type != null ? type : UNKNOWN;
	}

	public static TileEntityType getByRegistryId(String type) {
		return by_r_id.getOrDefault(type, TileEntityType.UNKNOWN);
	}

	private final int networkId;
	private final String registryId;
	TileEntityType(int networkId, String registryId) {
		this.networkId = networkId;
		this.registryId = registryId;
	}

	public String getRegistryId() {
		return registryId;
	}

	public int getNetworkId() {
		return networkId;
	}

}