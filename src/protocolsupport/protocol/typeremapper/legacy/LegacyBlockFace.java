package protocolsupport.protocol.typeremapper.legacy;

import org.bukkit.block.BlockFace;

public class LegacyBlockFace {

	public static byte getLegacyRotatableId(BlockFace face) {
		switch (face) {
			case SOUTH: return 0;
			case SOUTH_SOUTH_WEST: return 1;
			case SOUTH_WEST: return 2;
			case WEST_SOUTH_WEST: return 3;
			case WEST: return 4;
			case WEST_NORTH_WEST: return 5;
			case NORTH_WEST: return 6;
			case NORTH_NORTH_WEST: return 7;
			case NORTH: return 8;
			case NORTH_NORTH_EAST: return 9;
			case NORTH_EAST: return 10;
			case EAST_NORTH_EAST: return 11;
			case EAST: return 12;
			case EAST_SOUTH_EAST: return 13;
			case SOUTH_EAST: return 14;
			case SOUTH_SOUTH_EAST: return 15;
			default: return 0;
		}
	}

	public static byte getLegacyDirectionalId(BlockFace face) {
		switch (face) {
			case NORTH: return 2;
			case SOUTH: return 3;
			case WEST: return 4;
			case EAST: return 5;
			default: return 1;
		}
	}

}
