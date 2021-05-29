package protocolsupport.protocol.typeremapper.legacy;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;

import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyEntityAttribute {

	private LegacyEntityAttribute() {
	}

	private static final Map<String, String> toLegacyId = new HashMap<>();
	private static final Map<String, String> toModernId = new HashMap<>();

	private static void register(@Nonnull String modernId, @Nonnull String legacyId) {
		toLegacyId.put(modernId, legacyId);
		toLegacyId.put(NamespacedKey.minecraft(modernId).toString(), legacyId);
		toModernId.put(legacyId, modernId);
	}

	static {
		register("generic.max_health", "generic.maxHealth");
		register("generic.attack_damage", "generic.attackDamage");
		register("generic.attack_speed", "generic.attackSpeed");
		register("generic.attack_knockback", "generic.attackKnockback");
		register("generic.armor", "generic.armor");
		register("generic.armor_toughness", "generic.armorToughness");
		register("generic.knockback_resistance", "generic.knockbackResistance");
		register("generic.movement_speed", "generic.movementSpeed");
		register("generic.flying_speed", "generic.flyingSpeed");
		register("generic.follow_range", "generic.followRange");
		register("generic.luck", "generic.luck");
		register("horse.jump_strength", "horse.jumpStrength");
		register("zombie.spawn_reinforcements", "zombie.spawnReinforcements");
	}

	public static @Nonnull String getLegacyId(@Nonnull String modernId) {
		return toLegacyId.getOrDefault(modernId, modernId);
	}

	public static @Nonnull String getModernId(@Nonnull String legacyId) {
		return toModernId.getOrDefault(legacyId, legacyId);
	}

}
