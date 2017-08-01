package protocolsupport.protocol.typeremapper.legacy;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;

public class LegacyPotion {

	private static final TObjectIntHashMap<String> toLegacyId = new TObjectIntHashMap<>();
	private static final TIntObjectHashMap<String> fromLegacyId = new TIntObjectHashMap<>();
	static {
		register("night_vision", 8230);
		register("long_night_vision", 8262);
        register("invisibility", 8238);
        register("long_invisibility", 8270);
        register("leaping", 8203);
        register("long_leaping", 8267);
        register("strong_leaping", 8235);
        register("fire_resistance", 8227);
        register("long_fire_resistance", 8259);
        register("swiftness", 8194);
        register("long_swiftness", 8258);
        register("strong_swiftness", 8226);
        register("slowness", 8234);
        register("long_slowness", 8266);
        register("water_breathing", 8237);
        register("long_water_breathing", 8269);
        register("healing", 8261);
        register("strong_healing", 8229);
        register("harming", 8268);
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
        register("weakness", 8232);
        register("long_weakness", 8264);
	}

	private static void register(String name, int id) {
		toLegacyId.put(name, id);
		toLegacyId.put(MinecraftData.addNamespacePrefix(name), id);
		fromLegacyId.put(id, MinecraftData.addNamespacePrefix(name));
	}

	public static int toLegacyId(String nbttag, boolean isThrowable) {
		int value = toLegacyId.get(nbttag);
		return isThrowable ? value + 8192 : value;
	}

	public static boolean isThrowable(int legacyId) {
		return legacyId > 16384;
	}

	public static String fromLegacyId(int legacyId) {
		if (isThrowable(legacyId)) {
			legacyId -= 8192;
		}
		return fromLegacyId.get(legacyId);
	}

	public static String getBasicTypeName(String nbttag) {
		switch (nbttag) {
			case "mundane":
			case "minecraft:mundane": {
				return "potion.effect.mundane";
			}
			case "thick":
			case "minecraft:thick": {
				return "potion.effect.thick";
			}
			case "awkward":
			case "minecraft:awkward": {
				return "potion.effect.awkward";
			}
			default: {
				return null;
			}
		}
	}

}
