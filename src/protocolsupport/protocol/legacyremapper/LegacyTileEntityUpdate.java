package protocolsupport.protocol.legacyremapper;

import java.util.HashMap;

import protocolsupport.protocol.typeremapper.nbt.tileupdate.TileNBTTransformer.TileEntityUpdateType;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.protocol.utils.types.Position;

public class LegacyTileEntityUpdate {

	private static final String tileEntityTypeKey = "id";

	private static final HashMap<String, String> newToOldType = new HashMap<>();
	static {
		newToOldType.put("minecraft:mob_spawner", "MobSpawner");
		newToOldType.put("minecraft:command_block", "Control");
		newToOldType.put("minecraft:beacon", "Beacon");
		newToOldType.put("minecraft:skull", "Skull");
		newToOldType.put("minecraft:flower_pot", "FlowerPot");
		newToOldType.put("minecraft:banner", "Banner");
		newToOldType.put("minecraft:structure_block", "Structure");
		newToOldType.put("minecraft:end_gateway", "Airportal");
		newToOldType.put("minecraft:sign", "Sign");
	}

	public static void setLegacyType(NBTTagCompoundWrapper nbt) {
		nbt.setString(tileEntityTypeKey, newToOldType.getOrDefault(nbt.getString(tileEntityTypeKey), "Unknown"));
	}

	private static final HashMap<String, TileEntityUpdateType> updateTypes = new HashMap<>();
	static {
		updateTypes.put("MobSpawner", TileEntityUpdateType.MOB_SPAWNER);
		updateTypes.put("Control", TileEntityUpdateType.COMMAND_BLOCK);
		updateTypes.put("Beacon", TileEntityUpdateType.BEACON);
		updateTypes.put("Skull", TileEntityUpdateType.SKULL);
		updateTypes.put("FlowerPot", TileEntityUpdateType.FLOWER_POT);
		updateTypes.put("Banner", TileEntityUpdateType.BANNER);
		updateTypes.put("Structure", TileEntityUpdateType.STRUCTURE);
		updateTypes.put("EndGateway", TileEntityUpdateType.END_GATEWAY);
		updateTypes.put("Sign", TileEntityUpdateType.SIGN);
	}

	public static TileEntityUpdateType getUpdateType(NBTTagCompoundWrapper nbt) {
		return updateTypes.getOrDefault(nbt.getString(tileEntityTypeKey), TileEntityUpdateType.UNKNOWN);
	}

	public static Position getPosition(NBTTagCompoundWrapper tag) {
		return new Position(tag.getNumber("x"), tag.getNumber("y"), tag.getNumber("z"));
	}

	public static String[] getSignLines(NBTTagCompoundWrapper tag) {
		String[] lines = new String[4];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = tag.getString("Text" + (i + 1));
		}
		return lines;
	}

}
