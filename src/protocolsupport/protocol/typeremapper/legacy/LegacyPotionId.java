package protocolsupport.protocol.typeremapper.legacy;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.protocol.utils.NamespacedKeyUtils;
import protocolsupport.utils.BitUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyPotionId {

	private LegacyPotionId() {
	}

	private static final Object2IntOpenHashMap<String> toLegacyTypeId = new Object2IntOpenHashMap<>();
	private static final Int2ObjectOpenHashMap<String> fromLegacyTypeId = new Int2ObjectOpenHashMap<>();
	private static final int bitmaskStrong = BitUtils.createIBitMaskFromBit(5);
	private static final int bitmaskExtended = BitUtils.createIBitMaskFromBit(6);
	private static final int bitmaskType = BitUtils.createIBitMaskFromBits(new int[] {0, 1, 2, 3}) | bitmaskStrong | bitmaskExtended;
	private static final int bitmaskDrinkable = BitUtils.createIBitMaskFromBit(13);
	private static final int bitmaskThrowable = BitUtils.createIBitMaskFromBit(14);

	private static void register(String name, int id, int... additionalIds) {
		NamespacedKey key = NamespacedKeyUtils.fromString(name);
		toLegacyTypeId.put(key.getKey(), id);
		toLegacyTypeId.put(key.toString(), id);
		fromLegacyTypeId.put(id, key.toString());
		for (int aId : additionalIds) {
			fromLegacyTypeId.put(aId, key.toString());
		}
	}

	private static int makeStrong(int baseId) {
		return baseId | bitmaskStrong;
	}

	private static int makeExtended(int baseId) {
		return baseId | bitmaskExtended;
	}

	static {
		register("water", 0);
		register("awkward", 16);
		register("thick", makeStrong(0));
		register("mundane", makeExtended(0));

		register("regeneration", 1);
		register("long_regeneration", makeExtended(1));
		register("strong_regeneration", makeStrong(1));

		register("swiftness", 2);
		register("long_swiftness", makeExtended(2));
		register("strong_swiftness", makeStrong(2));

		register("fire_resistance", makeStrong(3), 3);
		register("long_fire_resistance", makeExtended(3));

		register("poison", 4);
		register("long_poison", makeExtended(4));
		register("strong_poison", makeStrong(4));

		register("healing", makeStrong(5), 5);
		register("strong_healing", makeExtended(5));

		register("night_vision", makeStrong(6), 6);
		register("long_night_vision", makeExtended(6));

		register("weakness", makeStrong(8), 8);
		register("long_weakness", makeExtended(8));

		register("strength", 9);
		register("long_strength", makeExtended(9));
		register("strong_strength", makeStrong(9));

		register("slowness", makeStrong(10), 10);
		register("long_slowness", makeExtended(10));

		register("leaping", 11);
		register("long_leaping", makeExtended(11));
		register("strong_leaping", makeStrong(11));

		register("harming", makeExtended(12), 12);
		register("strong_harming", makeStrong(12));

		register("water_breathing", makeStrong(13), 13);
		register("long_water_breathing", makeExtended(13));

		register("invisibility", makeStrong(14), 14);
		register("long_invisibility", makeExtended(14));
	}

	public static @Nonnegative int toLegacyId(@Nonnull String nbttag, boolean isThrowable) {
		int value = toLegacyTypeId.getInt(nbttag);
		return isThrowable ? value | bitmaskThrowable : value | bitmaskDrinkable;
	}

	public static boolean isThrowable(@Nonnegative int legacyId) {
		return (legacyId & bitmaskThrowable) != 0;
	}

	public static @Nullable String fromLegacyId(@Nonnegative int legacyId) {
		return fromLegacyTypeId.get(legacyId & bitmaskType);
	}

}
