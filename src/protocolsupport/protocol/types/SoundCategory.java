package protocolsupport.protocol.types;

import protocolsupport.protocol.utils.EnumConstantLookup;

public enum SoundCategory {
	MASTER, MUSIC, RECORDS, WEATHER, BLOCKS, HOSTILE, NEUTRAL, PLAYERS, AMBIENT, VOICE;
	public static final EnumConstantLookup<SoundCategory> CONSTANT_LOOKUP = new EnumConstantLookup<>(SoundCategory.class);
}
