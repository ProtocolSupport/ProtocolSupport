package protocolsupport.protocol.typeremapper.legacy;

import javax.annotation.Nonnull;

import org.bukkit.block.BlockFace;

public class LegacyBlockFace {

	private LegacyBlockFace() {
	}

	public static byte getLegacyRotatableId(@Nonnull BlockFace face) {
		return switch (face) {
			case SOUTH -> 0;
			case SOUTH_SOUTH_WEST -> 1;
			case SOUTH_WEST -> 2;
			case WEST_SOUTH_WEST -> 3;
			case WEST -> 4;
			case WEST_NORTH_WEST -> 5;
			case NORTH_WEST -> 6;
			case NORTH_NORTH_WEST -> 7;
			case NORTH -> 8;
			case NORTH_NORTH_EAST -> 9;
			case NORTH_EAST -> 10;
			case EAST_NORTH_EAST -> 11;
			case EAST -> 12;
			case EAST_SOUTH_EAST -> 13;
			case SOUTH_EAST -> 14;
			case SOUTH_SOUTH_EAST -> 15;
			default -> 0;
		};
	}

	public static byte getLegacyDirectionalId(@Nonnull BlockFace face) {
		return switch (face) {
			case NORTH -> 2;
			case SOUTH -> 3;
			case WEST -> 4;
			case EAST -> 5;
			default -> 1;
		};
	}

}
