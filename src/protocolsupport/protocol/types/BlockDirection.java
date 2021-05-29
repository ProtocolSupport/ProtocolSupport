package protocolsupport.protocol.types;

import protocolsupport.protocol.utils.EnumConstantLookup;

public enum BlockDirection {

	DOWN(0), UP(0),
	NORTH(2), SOUTH(0),
	WEST(1), EAST(3);

	protected int id2d;

	public static final EnumConstantLookup<BlockDirection> CONSTANT_LOOKUP = new EnumConstantLookup<>(BlockDirection.class);

	BlockDirection(int id2d) {
		this.id2d = id2d;
	}

	public int get2DId() {
		return id2d;
	}

}
