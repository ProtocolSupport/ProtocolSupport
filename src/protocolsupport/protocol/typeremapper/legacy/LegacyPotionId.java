package protocolsupport.protocol.typeremapper.legacy;

import org.bukkit.NamespacedKey;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyPotionId {

	private static final Object2IntOpenHashMap<String> toLegacyId = new Object2IntOpenHashMap<>();
	private static final Int2ObjectOpenHashMap<String> fromLegacyId = new Int2ObjectOpenHashMap<>();
	static {
		register("water", 0);
		register("awkward", 16);
		register("thick", 32);
		register("mundane", 64);
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
		toLegacyId.put(NamespacedKey.minecraft(name).toString(), id);
		fromLegacyId.put(id, NamespacedKey.minecraft(name).toString());
	}

	public static int toLegacyId(String nbttag, boolean isThrowable) {
		int value = toLegacyId.getInt(nbttag);
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

}
