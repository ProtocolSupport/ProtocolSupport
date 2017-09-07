package protocolsupport.protocol.typeremapper.legacy;

import java.util.EnumMap;

import protocolsupport.protocol.utils.types.NetworkEntityType;

public class LegacyEntityType {

	private static final EnumMap<NetworkEntityType, String> legacyNames = new EnumMap<>(NetworkEntityType.class);
	static {
		legacyNames.put(NetworkEntityType.FIREWORK, "FireworksRocketEntity");
		legacyNames.put(NetworkEntityType.SKELETON_HORSE, "SkeletonHorse");
		legacyNames.put(NetworkEntityType.ILLUSIONER, "VindicationIllager");
		legacyNames.put(NetworkEntityType.EXP_BOTTLE, "ThrownExpBottle");
		legacyNames.put(NetworkEntityType.ELDER_GUARDIAN, "ElderGuardian");
		legacyNames.put(NetworkEntityType.IRON_GOLEM, "VillagerGolem");
		legacyNames.put(NetworkEntityType.ZOMBIE_PIGMAN, "PigZombie");
		legacyNames.put(NetworkEntityType.WITHER_SKELETON, "WitherSkeleton");
		legacyNames.put(NetworkEntityType.MULE, "Mule");
		legacyNames.put(NetworkEntityType.AREA_EFFECT_CLOUD, "AreaEffectCloud");
		legacyNames.put(NetworkEntityType.ENDER_CRYSTAL, "EnderCrystal");
		legacyNames.put(NetworkEntityType.EVOCATOR_FANGS, "EvocationFangs");
		legacyNames.put(NetworkEntityType.EVOKER, "EvocationIllager");
		legacyNames.put(NetworkEntityType.FIREBALL, "Fireball");
		legacyNames.put(NetworkEntityType.ZOMBIE_VILLAGER, "ZombieVillager");
		legacyNames.put(NetworkEntityType.GIANT, "Giant");
		legacyNames.put(NetworkEntityType.SILVERFISH, "Silverfish");
		legacyNames.put(NetworkEntityType.SHEEP, "Sheep");
		legacyNames.put(NetworkEntityType.HUSK, "Husk");
		legacyNames.put(NetworkEntityType.GUARDIAN, "Guardian");
		legacyNames.put(NetworkEntityType.ENDERMITE, "Endermite");
		legacyNames.put(NetworkEntityType.ARROW, "Arrow");
		legacyNames.put(NetworkEntityType.ZOMBIE_HORSE, "ZombieHorse");
		legacyNames.put(NetworkEntityType.DRAGON_FIREBALL, "DragonFireball");
		legacyNames.put(NetworkEntityType.ENDERPEARL, "ThrownEnderpearl");
		legacyNames.put(NetworkEntityType.COW, "Cow");
		legacyNames.put(NetworkEntityType.SNOWMAN, "SnowMan");
		legacyNames.put(NetworkEntityType.MINECART_HOPPER, "MinecartHopper");
		legacyNames.put(NetworkEntityType.SPIDER, "Spider");
		legacyNames.put(NetworkEntityType.FIRECHARGE, "SmallFireball");
		legacyNames.put(NetworkEntityType.WITCH, "Witch");
		legacyNames.put(NetworkEntityType.WITHER, "WitherBoss");
		legacyNames.put(NetworkEntityType.STRAY, "Stray");
		legacyNames.put(NetworkEntityType.CREEPER, "Creeper");
		legacyNames.put(NetworkEntityType.WITHER_SKULL, "WitherSkull");
		legacyNames.put(NetworkEntityType.POLAR_BEAR, "PolarBear");
		legacyNames.put(NetworkEntityType.POTION, "ThrownPotion");
		legacyNames.put(NetworkEntityType.MINECART_FURNACE, "MinecartFurnace");
		legacyNames.put(NetworkEntityType.SHULKER, "Shulker");
		legacyNames.put(NetworkEntityType.SPECTRAL_ARROW, "SpectralArrow");
		legacyNames.put(NetworkEntityType.MINECART_TNT, "MinecartTNT");
		legacyNames.put(NetworkEntityType.CAVE_SPIDER, "CaveSpider");
		legacyNames.put(NetworkEntityType.BOAT, "Boat");
		legacyNames.put(NetworkEntityType.WOLF, "Wolf");
		legacyNames.put(NetworkEntityType.VILLAGER, "Villager");
		legacyNames.put(NetworkEntityType.OCELOT, "Ozelot");
		legacyNames.put(NetworkEntityType.VEX, "Vex");
		legacyNames.put(NetworkEntityType.PIG, "Pig");
		legacyNames.put(NetworkEntityType.RABBIT, "Rabbit");
		legacyNames.put(NetworkEntityType.MAGMA_CUBE, "LavaSlime");
		legacyNames.put(NetworkEntityType.ZOMBIE, "Zombie");
		legacyNames.put(NetworkEntityType.GHAST, "Ghast");
		legacyNames.put(NetworkEntityType.BAT, "Bat");
		legacyNames.put(NetworkEntityType.BLAZE, "Blaze");
		legacyNames.put(NetworkEntityType.LAMA, "Llama");
		legacyNames.put(NetworkEntityType.CHICKEN, "Chicken");
		legacyNames.put(NetworkEntityType.SLIME, "Slime");
		legacyNames.put(NetworkEntityType.EXP_ORB, "XPOrb");
		legacyNames.put(NetworkEntityType.MUSHROOM_COW, "MushroomCow");
		legacyNames.put(NetworkEntityType.MINECART_COMMAND, "MinecartCommandBlock");
		legacyNames.put(NetworkEntityType.ARMOR_STAND, "ArmorStand");
		legacyNames.put(NetworkEntityType.DONKEY, "Donkey");
		legacyNames.put(NetworkEntityType.LEASH_KNOT, "LeashKnot");
		legacyNames.put(NetworkEntityType.SKELETON, "Skeleton");
		legacyNames.put(NetworkEntityType.COMMON_HORSE, "Horse");
		legacyNames.put(NetworkEntityType.SKELETON_HORSE, "Horse");
		legacyNames.put(NetworkEntityType.ZOMBIE_HORSE, "Horse");
		legacyNames.put(NetworkEntityType.MINECART_MOB_SPAWNER, "MinecartSpawner");
		legacyNames.put(NetworkEntityType.MINECART, "MinecartRideable");
		legacyNames.put(NetworkEntityType.MINECART_CHEST, "MinecartChest");
		legacyNames.put(NetworkEntityType.ENDEREYE, "EyeOfEnderSignal");
		legacyNames.put(NetworkEntityType.SHULKER_BULLET, "ShulkerBullet");
		legacyNames.put(NetworkEntityType.ENDER_DRAGON, "EnderDragon");
		legacyNames.put(NetworkEntityType.ENDERMAN, "Enderman");
		legacyNames.put(NetworkEntityType.LAMA_SPIT, "LlamaSpit");
		legacyNames.put(NetworkEntityType.SNOWBALL, "Snowball");
		legacyNames.put(NetworkEntityType.TNT, "PrimedTnt");
		legacyNames.put(NetworkEntityType.SQUID, "Squid");
	}

	public static String getLegacyName(NetworkEntityType type) {
		return legacyNames.getOrDefault(type, type.getRegistrySTypeId());
	}

}
