package protocolsupport.protocol.legacyremapper;

import net.minecraft.server.v1_9_R2.BlockPosition;
import net.minecraft.server.v1_9_R2.NBTTagCompound;

public class LegacyTileEntityUpdate {

	public static enum TileEntityUpdateType {
		UNKNOWN, MOB_SPAWNER, COMMAND_BLOCK, BEACON, SKULL, FLOWER_POT, BANNER, STRUCTURE, END_GATEWAY, SIGN; 
	}

	public static TileEntityUpdateType getUpdateType(NBTTagCompound tag) {
		switch (tag.getString("id")) {
			case "MobSpawner": {
				return TileEntityUpdateType.MOB_SPAWNER;
			}
			case "Control": {
				return TileEntityUpdateType.COMMAND_BLOCK;
			}
			case "Beacon": {
				return TileEntityUpdateType.BEACON;
			}
			case "Skull": {
				return TileEntityUpdateType.SKULL;
			}
			case "FlowerPot": {
				return TileEntityUpdateType.FLOWER_POT;
			}
			case "Banner": {
				return TileEntityUpdateType.BANNER;
			}
			case "Structure": {
				return TileEntityUpdateType.STRUCTURE;
			}
			case "EndGateway": {
				return TileEntityUpdateType.END_GATEWAY;
			}
			case "Sign": {
				return TileEntityUpdateType.SIGN;
			}
			default: {
				return TileEntityUpdateType.UNKNOWN;
			}
		}
	}

	public static BlockPosition getPosition(NBTTagCompound tag) {
		return new BlockPosition(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
	}

	public static String[] getSignLines(NBTTagCompound tag) {
		String[] lines = new String[4];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = tag.getString("Text" + (i + 1));
		}
		return lines;
	}

}
