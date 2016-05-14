package protocolsupport.protocol.legacyremapper;

import protocolsupport.protocol.typeremapper.nbt.tileupdate.TileNBTTransformer;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.protocol.utils.types.Position;

public class LegacyTileEntityUpdate {

	public static TileNBTTransformer.TileEntityUpdateType getUpdateType(NBTTagCompoundWrapper tag) {
		switch (tag.getString("id")) {
			case "MobSpawner": {
				return TileNBTTransformer.TileEntityUpdateType.MOB_SPAWNER;
			}
			case "Control": {
				return TileNBTTransformer.TileEntityUpdateType.COMMAND_BLOCK;
			}
			case "Beacon": {
				return TileNBTTransformer.TileEntityUpdateType.BEACON;
			}
			case "Skull": {
				return TileNBTTransformer.TileEntityUpdateType.SKULL;
			}
			case "FlowerPot": {
				return TileNBTTransformer.TileEntityUpdateType.FLOWER_POT;
			}
			case "Banner": {
				return TileNBTTransformer.TileEntityUpdateType.BANNER;
			}
			case "Structure": {
				return TileNBTTransformer.TileEntityUpdateType.STRUCTURE;
			}
			case "EndGateway": {
				return TileNBTTransformer.TileEntityUpdateType.END_GATEWAY;
			}
			case "Sign": {
				return TileNBTTransformer.TileEntityUpdateType.SIGN;
			}
			default: {
				return TileNBTTransformer.TileEntityUpdateType.UNKNOWN;
			}
		}
	}

	public static Position getPosition(NBTTagCompoundWrapper tag) {
		return new Position(tag.getNumberAsInt("x"), tag.getNumberAsInt("y"), tag.getNumberAsInt("z"));
	}

	public static String[] getSignLines(NBTTagCompoundWrapper tag) {
		String[] lines = new String[4];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = tag.getString("Text" + (i + 1));
		}
		return lines;
	}

}
