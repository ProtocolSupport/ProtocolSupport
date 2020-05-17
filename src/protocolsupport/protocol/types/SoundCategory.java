package protocolsupport.protocol.types;

import protocolsupport.protocol.utils.EnumConstantLookups;

public enum SoundCategory {
	MASTER, MUSIC, RECORDS, WEATHER, BLOCKS, HOSTILE, NEUTRAL, PLAYERS, AMBIENT, VOICE;
	public static final EnumConstantLookups.EnumConstantLookup<SoundCategory> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(SoundCategory.class);
}
