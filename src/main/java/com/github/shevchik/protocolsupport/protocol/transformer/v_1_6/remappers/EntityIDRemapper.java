package com.github.shevchik.protocolsupport.protocol.transformer.v_1_6.remappers;

public class EntityIDRemapper {

	private static int replacementsLiving[] = new int[256];
	static {
		for (int i = 0; i < replacementsLiving.length; i++) {
			replacementsLiving[i] = -1;
		}
		// endermite -> silverfish
		replacementsLiving[67] = 60;
		// guardian -> sqiud
		replacementsLiving[68] = 94;
		// rabbit -> chicken
		replacementsLiving[101] = 93;
	}

	public static int replaceLivingEntityId(int oldId) {
		if (replacementsLiving[oldId] != -1) {
			return replacementsLiving[oldId];
		}
		return oldId;
	}

}
