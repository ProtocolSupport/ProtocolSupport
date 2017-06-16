package protocolsupport.protocol.typeremapper.tileentity;

import java.util.HashMap;

public enum TileEntityUpdateType {

	UNKNOWN, MOB_SPAWNER, COMMAND_BLOCK, BEACON, SKULL, FLOWER_POT, BANNER, STRUCTURE, END_GATEWAY, SIGN, SHULKER_BOX, BED;

	private static final HashMap<String, TileEntityUpdateType> updateTypes = new HashMap<>();
	static {
		updateTypes.put("minecraft:mob_spawner", TileEntityUpdateType.MOB_SPAWNER);
		updateTypes.put("minecraft:command_block", TileEntityUpdateType.COMMAND_BLOCK);
		updateTypes.put("minecraft:beacon", TileEntityUpdateType.BEACON);
		updateTypes.put("minecraft:skull", TileEntityUpdateType.SKULL);
		updateTypes.put("minecraft:flower_pot", TileEntityUpdateType.FLOWER_POT);
		updateTypes.put("minecraft:banner", TileEntityUpdateType.BANNER);
		updateTypes.put("minecraft:structure_block", TileEntityUpdateType.STRUCTURE);
		updateTypes.put("minecraft:end_gateway", TileEntityUpdateType.END_GATEWAY);
		updateTypes.put("minecraft:sign", TileEntityUpdateType.SIGN);
	}

	public static TileEntityUpdateType fromType(String type) {
		return updateTypes.getOrDefault(type, TileEntityUpdateType.UNKNOWN);
	}

	public int getId() {
		return ordinal();
	}

	public static TileEntityUpdateType fromId(int id) {
		return values()[id];
	}

}