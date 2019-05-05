package protocolsupport.protocol.types;

import protocolsupport.protocol.utils.EnumConstantLookups;

public enum UsedHand {
	MAIN, OFF;
	public static final EnumConstantLookups.EnumConstantLookup<UsedHand> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(UsedHand.class);
}
