package protocolsupport.protocol.utils.types;

import protocolsupport.protocol.utils.EnumConstantLookups;

public enum BlockDirection {
	DOWN, UP, NORTH, SOUTH, WEST, EAST;
	public static final EnumConstantLookups.EnumConstantLookup<BlockDirection> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(BlockDirection.class);
}
