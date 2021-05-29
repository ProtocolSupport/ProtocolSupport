package protocolsupport.protocol.types;

import protocolsupport.protocol.utils.EnumConstantLookup;

public enum UsedHand {
	MAIN, OFF;
	public static final EnumConstantLookup<UsedHand> CONSTANT_LOOKUP = new EnumConstantLookup<>(UsedHand.class);
}
