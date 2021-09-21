package protocolsupport.protocol.typeremapper.legacy;

import java.text.MessageFormat;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import it.unimi.dsi.fastutil.objects.Object2ShortOpenHashMap;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyEnchantmentId {

	private LegacyEnchantmentId() {
	}


	private static final short legacyIdNone = -1;
	private static final Object2ShortOpenHashMap<String> toLegacyId = new Object2ShortOpenHashMap<>();
	private static final String[] byLegacyId = new String[128];

	private static void register(@Nonnull NamespacedKey key, @Nonnegative short id) {
		toLegacyId.put(key.getKey(), id);
		String keyStr = key.toString();
		toLegacyId.put(keyStr, id);
		byLegacyId[id] = keyStr;
	}

	private static void register(@Nonnull Enchantment ench, @Nonnegative short id) {
		register(ench.getKey(), id);
	}

	static {
		toLegacyId.defaultReturnValue(legacyIdNone);
		register(Enchantment.PROTECTION_ENVIRONMENTAL, (short) 0);
		register(Enchantment.PROTECTION_FIRE, (short) 1);
		register(Enchantment.PROTECTION_FALL, (short) 2);
		register(Enchantment.PROTECTION_EXPLOSIONS, (short) 3);
		register(Enchantment.PROTECTION_PROJECTILE, (short) 4);
		register(Enchantment.OXYGEN, (short) 5);
		register(Enchantment.WATER_WORKER, (short) 6);
		register(Enchantment.MENDING, (short) 70);
		register(Enchantment.THORNS, (short) 7);
		register(Enchantment.VANISHING_CURSE, (short) 71);
		register(Enchantment.DEPTH_STRIDER, (short) 8);
		register(Enchantment.FROST_WALKER, (short) 9);
		register(Enchantment.BINDING_CURSE, (short) 10);
		register(Enchantment.DAMAGE_ALL, (short) 16);
		register(Enchantment.DAMAGE_UNDEAD, (short) 17);
		register(Enchantment.DAMAGE_ARTHROPODS, (short) 18);
		register(Enchantment.KNOCKBACK, (short) 19);
		register(Enchantment.FIRE_ASPECT, (short) 20);
		register(Enchantment.LOOT_BONUS_MOBS, (short) 21);
		register(Enchantment.SWEEPING_EDGE, (short) 22);
		register(Enchantment.DIG_SPEED, (short) 32);
		register(Enchantment.SILK_TOUCH, (short) 33);
		register(Enchantment.DURABILITY, (short) 34);
		register(Enchantment.LOOT_BONUS_BLOCKS, (short) 35);
		register(Enchantment.ARROW_DAMAGE, (short) 48);
		register(Enchantment.ARROW_KNOCKBACK, (short) 49);
		register(Enchantment.ARROW_FIRE, (short) 50);
		register(Enchantment.ARROW_INFINITE, (short) 51);
		register(Enchantment.LUCK, (short) 61);
		register(Enchantment.LURE, (short) 62);
		register(CommonNBT.FAKE_ENCHANTMENT_KEY, (short) 127);
	}

	public static @Nonnegative short getId(@Nonnull String key) {
		short legacyId = toLegacyId.getShort(key);
		if (legacyId == legacyIdNone) {
			throw new IllegalArgumentException(MessageFormat.format("Missing enchantment {0} legacy id", key));
		}
		return legacyId;
	}

	public static @Nullable String getById(@Nonnegative int id) {
		if ((id >= 0) && (id < byLegacyId.length)) {
			return byLegacyId[id];
		} else {
			return null;
		}
	}

}
