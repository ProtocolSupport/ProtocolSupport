package protocolsupport.protocol.types;

import protocolsupport.protocol.utils.EnumConstantLookups;

public enum BlockDirection {

	DOWN(-1), UP(-1),
	NORTH(2), SOUTH(0),
	WEST(1), EAST(3);

	protected int id2d;

	public static final EnumConstantLookups.EnumConstantLookup<BlockDirection> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(BlockDirection.class);

	BlockDirection(int id2d) {
		this.id2d = id2d;
	}

	public int get2DId() {
		return id2d;
	}

}
