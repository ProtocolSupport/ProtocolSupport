package protocolsupport.protocol.types;

import java.util.Arrays;

import protocolsupport.protocol.utils.minecraftdata.MinecraftTileData;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public enum TileEntityType {

	UNKNOWN("___unknown"),
	MOB_SPAWNER("mob_spawner"),
	COMMAND_BLOCK("command_block"),
	BEACON("beacon"),
	SKULL("skull"),
	CONDUIT("conduit"),
	BANNER("banner"),
	STRUCTURE("structure_block"),
	END_GATEWAY("end_gateway"),
	SIGN("sign"),
	SHULKER_BOX("shulker_box"),
	BED("bed"),
	JIGSAW("jigsaw"),
	CAMPFIRE("campfire"),
	BEEHIVE("beehive"),
	FURNACE("furnace"),
	CHEST("chest"),
	TRAPPED_CHEST("trapped_chest"),
	ENDER_CHEST("ender_chest"),
	JUKEXBOX("jukebox"),
	DISPENSER("dispenser"),
	DROPPER("dropper"),
	PISTON("piston"),
	BREWING_STAND("brewing_stand"),
	ENCHANTING_TABLE("enchanting_table"),
	END_PORTAL("end_portal"),
	DAYLIGHT_DETECTOR("daylight_detector"),
	HOPPER("hopper"),
	COMPARATOR("comparator"),
	STRUCTURE_BLOCK("structure_block"),
	BARREL("barrel"),
	SMOKER("smoker"),
	BLAST_FURNACE("blast_furnace"),
	LECTERN("lectern"),
	BELL("bell"),
	SKULK_SENSOR("sculk_sensor");

	private static final ArrayMap<TileEntityType> by_n_id = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(values()), (v -> v.networkId));

	public static TileEntityType getByNetworkId(int id) {
		TileEntityType type = by_n_id.get(id);
		return type != null ? type : UNKNOWN;
	}

	private final int networkId;
	private final String registryId;

	TileEntityType(String registryId) {
		this.registryId = registryId;
		this.networkId = MinecraftTileData.getIdByName(registryId);
	}

	public String getRegistryId() {
		return registryId;
	}

	public int getNetworkId() {
		return networkId;
	}

}