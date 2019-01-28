package protocolsupport.protocol.typeremapper.legacy;

import org.bukkit.enchantments.Enchantment;

import it.unimi.dsi.fastutil.objects.Object2ShortOpenHashMap;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyEnchantmentId {

	private static final Object2ShortOpenHashMap<Enchantment> toLegacyId = new Object2ShortOpenHashMap<>();
	private static final Enchantment[] byLegacyId = new Enchantment[128];

	private static void register(Enchantment ench, short id) {
		toLegacyId.put(ench, id);
		byLegacyId[id] = ench;
	}

	static {
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
	}

	public static short getId(Enchantment ench) {
		return toLegacyId.getShort(ench);
	}

	public static Enchantment getById(int id) {
		if ((id >= 0) && (id < byLegacyId.length)) {
			return byLegacyId[id];
		} else {
			return null;
		}
	}

}
