package protocolsupport.protocol.legacyremapper;

import gnu.trove.map.hash.TObjectIntHashMap;

public class LegacyPotion {

	private static final TObjectIntHashMap<String> potionToLegacyIds = new TObjectIntHashMap<String>();
	static {
		register("night_vision", 8198);
		register("long_night_vision", 8262);
        register("invisibility", 8206);
        register("long_invisibility", 8270);
        register("leaping", 8203);
        register("long_leaping", 8267);
        register("strong_leaping", 8235);
        register("fire_resistance", 8195);
        register("long_fire_resistance", 8259);
        register("swiftness", 8194);
        register("long_swiftness", 8258);
        register("strong_swiftness", 8226);
        register("slowness", 8202);
        register("long_slowness", 8266);
        register("water_breathing", 8205);
        register("long_water_breathing", 8269);
        register("healing", 8197);
        register("strong_healing", 8229);
        register("harming", 8204);
        register("strong_harming", 8236);
        register("poison", 8196);
        register("long_poison", 8260);
        register("strong_poison", 8228);
        register("regeneration", 8193);
        register("long_regeneration", 8257);
        register("strong_regeneration", 8225);
        register("strength", 8201);
        register("long_strength", 8265);
        register("strong_strength", 8233);
        register("weakness", 8200);
        register("long_weakness", 8264);
	}

	private static void register(String name, int id) {
		potionToLegacyIds.put(name, id);
		potionToLegacyIds.put("minecraft:"+name, id);
	}

	public static int toLegacyId(String nbttag, boolean isThrowable) {
		int value = potionToLegacyIds.get(nbttag);
		return isThrowable ? value + 8192 : value;
	}

	public static String getBasicTypeName(String nbttag) {
		switch (nbttag) {
			case "minecraft:mundane": {
				return "Mundane Potion";
			}
			case "minecraft:thick": {
				return "Thick Potion";
			}
			case "minecraft:awkward": {
				return "Awkward Potion";
			}
			default: {
				return null;
			}
		}
	}

}
