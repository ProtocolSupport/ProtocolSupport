package protocolsupport.protocol.v_1_7.remappers;

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
		// armor stand -> ender crystal
		replacementsLiving[78] = 51; 
	}

	public static int replaceLivingEntityId(int oldId) {
		if (replacementsLiving[oldId] != -1) {
			return replacementsLiving[oldId];
		}
		return oldId;
	}

	private static int replacementsObjects[] = new int[256];
	static {
		for (int i = 0; i < replacementsObjects.length; i++) {
			replacementsObjects[i] = -1;
		}
		// armor stand -> ender crystal
		replacementsObjects[78] = 51; 
	}

	public static int replaceObjectEntityId(int oldId) {
		if (replacementsObjects[oldId] != -1) {
			return replacementsObjects[oldId];
		}
		return oldId;
	}

}
