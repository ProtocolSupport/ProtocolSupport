package protocolsupport.protocol.typeremapper.legacy;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.bukkit.entity.EntityType;

import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyEntityId {

	private LegacyEntityId() {
	}

	private static final Map<NetworkEntityType, String> toLegacyStringId = new EnumMap<>(NetworkEntityType.class);
	private static final Map<String, NetworkEntityType> fromLegacyStringId = new HashMap<>();

	private static void register(@Nonnull NetworkEntityType type, @Nonnull String stringId) {
		toLegacyStringId.put(type, stringId);
		fromLegacyStringId.put(stringId, type);
	}

	static {
		register(NetworkEntityType.FIREWORK, "FireworksRocketEntity");
		register(NetworkEntityType.SKELETON_HORSE, "SkeletonHorse");
		register(NetworkEntityType.ILLUSIONER, "VindicationIllager");
		register(NetworkEntityType.EXP_BOTTLE, "ThrownExpBottle");
		register(NetworkEntityType.ELDER_GUARDIAN, "ElderGuardian");
		register(NetworkEntityType.IRON_GOLEM, "VillagerGolem");
		register(NetworkEntityType.ZOMBIFIED_PIGLIN, "PigZombie");
		register(NetworkEntityType.WITHER_SKELETON, "WitherSkeleton");
		register(NetworkEntityType.MULE, "Mule");
		register(NetworkEntityType.AREA_EFFECT_CLOUD, "AreaEffectCloud");
		register(NetworkEntityType.ENDER_CRYSTAL, "EnderCrystal");
		register(NetworkEntityType.EVOCATOR_FANGS, "EvocationFangs");
		register(NetworkEntityType.EVOKER, "EvocationIllager");
		register(NetworkEntityType.FIREBALL, "Fireball");
		register(NetworkEntityType.ZOMBIE_VILLAGER, "ZombieVillager");
		register(NetworkEntityType.GIANT, "Giant");
		register(NetworkEntityType.SILVERFISH, "Silverfish");
		register(NetworkEntityType.SHEEP, "Sheep");
		register(NetworkEntityType.HUSK, "Husk");
		register(NetworkEntityType.GUARDIAN, "Guardian");
		register(NetworkEntityType.ENDERMITE, "Endermite");
		register(NetworkEntityType.ARROW, "Arrow");
		register(NetworkEntityType.ZOMBIE_HORSE, "ZombieHorse");
		register(NetworkEntityType.DRAGON_FIREBALL, "DragonFireball");
		register(NetworkEntityType.ENDERPEARL, "ThrownEnderpearl");
		register(NetworkEntityType.COW, "Cow");
		register(NetworkEntityType.SNOWMAN, "SnowMan");
		register(NetworkEntityType.MINECART_HOPPER, "MinecartHopper");
		register(NetworkEntityType.SPIDER, "Spider");
		register(NetworkEntityType.FIRECHARGE, "SmallFireball");
		register(NetworkEntityType.WITCH, "Witch");
		register(NetworkEntityType.WITHER, "WitherBoss");
		register(NetworkEntityType.STRAY, "Stray");
		register(NetworkEntityType.CREEPER, "Creeper");
		register(NetworkEntityType.WITHER_SKULL, "WitherSkull");
		register(NetworkEntityType.POLAR_BEAR, "PolarBear");
		register(NetworkEntityType.POTION, "ThrownPotion");
		register(NetworkEntityType.MINECART_FURNACE, "MinecartFurnace");
		register(NetworkEntityType.SHULKER, "Shulker");
		register(NetworkEntityType.SPECTRAL_ARROW, "SpectralArrow");
		register(NetworkEntityType.MINECART_TNT, "MinecartTNT");
		register(NetworkEntityType.CAVE_SPIDER, "CaveSpider");
		register(NetworkEntityType.BOAT, "Boat");
		register(NetworkEntityType.WOLF, "Wolf");
		register(NetworkEntityType.VILLAGER, "Villager");
		register(NetworkEntityType.OCELOT, "Ozelot");
		register(NetworkEntityType.VEX, "Vex");
		register(NetworkEntityType.PIG, "Pig");
		register(NetworkEntityType.RABBIT, "Rabbit");
		register(NetworkEntityType.MAGMA_CUBE, "LavaSlime");
		register(NetworkEntityType.ZOMBIE, "Zombie");
		register(NetworkEntityType.GHAST, "Ghast");
		register(NetworkEntityType.BAT, "Bat");
		register(NetworkEntityType.BLAZE, "Blaze");
		register(NetworkEntityType.LAMA, "Llama");
		register(NetworkEntityType.CHICKEN, "Chicken");
		register(NetworkEntityType.SLIME, "Slime");
		register(NetworkEntityType.EXP_ORB, "XPOrb");
		register(NetworkEntityType.MUSHROOM_COW, "MushroomCow");
		register(NetworkEntityType.MINECART_COMMAND, "MinecartCommandBlock");
		register(NetworkEntityType.ARMOR_STAND, "ArmorStand");
		register(NetworkEntityType.DONKEY, "Donkey");
		register(NetworkEntityType.LEASH_KNOT, "LeashKnot");
		register(NetworkEntityType.SKELETON, "Skeleton");
		register(NetworkEntityType.COMMON_HORSE, "EntityHorse");
		register(NetworkEntityType.SKELETON_HORSE, "EntityHorse");
		register(NetworkEntityType.ZOMBIE_HORSE, "EntityHorse");
		register(NetworkEntityType.MINECART_MOB_SPAWNER, "MinecartSpawner");
		register(NetworkEntityType.MINECART, "MinecartRideable");
		register(NetworkEntityType.MINECART_CHEST, "MinecartChest");
		register(NetworkEntityType.ENDEREYE, "EyeOfEnderSignal");
		register(NetworkEntityType.SHULKER_BULLET, "ShulkerBullet");
		register(NetworkEntityType.ENDER_DRAGON, "EnderDragon");
		register(NetworkEntityType.ENDERMAN, "Enderman");
		register(NetworkEntityType.LAMA_SPIT, "LlamaSpit");
		register(NetworkEntityType.SNOWBALL, "Snowball");
		register(NetworkEntityType.TNT, "PrimedTnt");
		register(NetworkEntityType.SQUID, "Squid");
	}

	public static @Nonnull String getStringId(@Nonnull NetworkEntityType type) {
		return toLegacyStringId.getOrDefault(type, type.getKey());
	}

	@SuppressWarnings("deprecation")
	public static @CheckForSigned int getIntId(@Nonnull NetworkEntityType type) {
		return type.getBukkitType().getTypeId();
	}

	public static @Nonnull NetworkEntityType getTypeByStringId(@Nonnull String stringId) {
		return fromLegacyStringId.getOrDefault(stringId, NetworkEntityType.NONE);
	}

	@SuppressWarnings("deprecation")
	public static @Nonnull NetworkEntityType getTypeByIntId(@Nonnegative int intId) {
		EntityType btype = EntityType.fromId(intId);
		return btype != null ? NetworkEntityType.getByBukkitType(btype) : NetworkEntityType.NONE;
	}

	private static final Map<NetworkEntityType, Integer> legacyEntityObjectIntId = new EnumMap<>(NetworkEntityType.class);
	static {
		legacyEntityObjectIntId.put(NetworkEntityType.BOAT, 1);
		legacyEntityObjectIntId.put(NetworkEntityType.TNT, 50);
		legacyEntityObjectIntId.put(NetworkEntityType.SNOWBALL, 61);
		legacyEntityObjectIntId.put(NetworkEntityType.EGG, 62);
		legacyEntityObjectIntId.put(NetworkEntityType.FIREBALL, 63);
		legacyEntityObjectIntId.put(NetworkEntityType.FIRECHARGE, 64);
		legacyEntityObjectIntId.put(NetworkEntityType.ENDERPEARL, 65);
		legacyEntityObjectIntId.put(NetworkEntityType.WITHER_SKULL, 66);
		legacyEntityObjectIntId.put(NetworkEntityType.FALLING_OBJECT, 70);
		legacyEntityObjectIntId.put(NetworkEntityType.ENDEREYE, 72);
		legacyEntityObjectIntId.put(NetworkEntityType.POTION, 73);
		legacyEntityObjectIntId.put(NetworkEntityType.EXP_BOTTLE, 75);
		legacyEntityObjectIntId.put(NetworkEntityType.LEASH_KNOT, 77);
		legacyEntityObjectIntId.put(NetworkEntityType.FISHING_FLOAT, 90);
		legacyEntityObjectIntId.put(NetworkEntityType.ITEM, 2);
		legacyEntityObjectIntId.put(NetworkEntityType.ARROW, 60);
		legacyEntityObjectIntId.put(NetworkEntityType.TIPPED_ARROW, 60);
		legacyEntityObjectIntId.put(NetworkEntityType.SPECTRAL_ARROW, 91);
		legacyEntityObjectIntId.put(NetworkEntityType.THROWN_TRIDENT, 94);
		legacyEntityObjectIntId.put(NetworkEntityType.FIREWORK, 76);
		legacyEntityObjectIntId.put(NetworkEntityType.ITEM_FRAME, 71);
		legacyEntityObjectIntId.put(NetworkEntityType.ENDER_CRYSTAL, 51);
		legacyEntityObjectIntId.put(NetworkEntityType.AREA_EFFECT_CLOUD, 3);
		legacyEntityObjectIntId.put(NetworkEntityType.SHULKER_BULLET, 67);
		legacyEntityObjectIntId.put(NetworkEntityType.LAMA_SPIT, 68);
		legacyEntityObjectIntId.put(NetworkEntityType.DRAGON_FIREBALL, 93);
		legacyEntityObjectIntId.put(NetworkEntityType.EVOCATOR_FANGS, 79);
		legacyEntityObjectIntId.put(NetworkEntityType.ARMOR_STAND, 78);
		legacyEntityObjectIntId.put(NetworkEntityType.MINECART, 10);
		legacyEntityObjectIntId.put(NetworkEntityType.MINECART_CHEST, 10);
		legacyEntityObjectIntId.put(NetworkEntityType.MINECART_FURNACE, 10);
		legacyEntityObjectIntId.put(NetworkEntityType.MINECART_TNT, 10);
		legacyEntityObjectIntId.put(NetworkEntityType.MINECART_MOB_SPAWNER, 10);
		legacyEntityObjectIntId.put(NetworkEntityType.MINECART_HOPPER, 10);
		legacyEntityObjectIntId.put(NetworkEntityType.MINECART_COMMAND, 10);
	}

	public static int getObjectIntId(@Nonnull NetworkEntityType type) {
		Integer legacyId = legacyEntityObjectIntId.get(type);
		if (legacyId == null) {
			throw new IllegalArgumentException(MessageFormat.format("Missing entity object {0} legacy id", legacyId));
		}
		return legacyId;
	}

}
