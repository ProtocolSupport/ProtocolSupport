package protocolsupport.protocol.utils.types;

import java.util.Arrays;
import java.util.HashMap;

import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public enum TileEntityType {

	UNKNOWN(0, "__FAKETYPE_NONE"),
	MOB_SPAWNER(1, "mob_spawner"),
	COMMAND_BLOCK(2, "command_block"),
	BEACON(3, "beacon"),
	SKULL(4, "skull"),
	FLOWER_POT(5, "flower_pot"),
	BANNER(6, "banner"),
	STRUCTURE(7, "structure_block"),
	END_GATEWAY(8, "end_gateway"),
	SIGN(9, "sign"),
	SHULKER_BOX(10, "shulker_box"),
	BED(11, "bed"),
	FURNACE(-1, "furnace"),
	CHEST(-1, "chest"),
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
	STRUCTURE_BLOCK(-1, "structure_block");

	private static final HashMap<String, TileEntityType> by_r_id = new HashMap<>();
	private static final ArrayMap<TileEntityType> by_n_id = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(values()).filter(v -> v.networkId > 0), (v -> v.networkId));
	static {
		Arrays.stream(values()).forEach(v -> {
			by_r_id.put(v.registryId, v);
			by_r_id.put(MinecraftData.addNamespacePrefix(v.registryId), v);
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