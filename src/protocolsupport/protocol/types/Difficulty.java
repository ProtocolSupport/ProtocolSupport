package protocolsupport.protocol.types;

import protocolsupport.protocol.utils.EnumConstantLookup;

public enum Difficulty {
	PEACEFUL, EASY, NORMAL, HARD;
	public static final EnumConstantLookup<Difficulty> CONSTANT_LOOKUP = new EnumConstantLookup<>(Difficulty.class);
}
