package protocolsupport.protocol.typeremapper.legacy;

import java.util.HashMap;

public class LegacyEntityType {

	private static final HashMap<String, String> legacyNames = new HashMap<>();
	static {
		register("fireworks_rocket", "FireworksRocketEntity");
		register("skeleton_horse", "SkeletonHorse");
		register("vindication_illager", "VindicationIllager");
		register("xp_bottle", "ThrownExpBottle");
		register("elder_guardian", "ElderGuardian");
		register("villager_golem", "VillagerGolem");
		register("zombie_pigman", "PigZombie");
		register("wither_skeleton", "WitherSkeleton");
		register("mule", "Mule");
		register("area_effect_cloud", "AreaEffectCloud");
		register("ender_crystal", "EnderCrystal");
		register("evocation_fangs", "EvocationFangs");
		register("evocation_illager", "EvocationIllager");
		register("fireball", "Fireball");
		register("zombie_villager", "ZombieVillager");
		register("giant", "Giant");
		register("silverfish", "Silverfish");
		register("sheep", "Sheep");
		register("husk", "Husk");
		register("guardian", "Guardian");
		register("endermite", "Endermite");
		register("arrow", "Arrow");
		register("zombie_horse", "ZombieHorse");
		register("dragon_fireball", "DragonFireball");
		register("ender_pearl", "ThrownEnderpearl");
		register("cow", "Cow");
		register("snowman", "SnowMan");
		register("hopper_minecart", "MinecartHopper");
		register("spider", "Spider");
		register("small_fireball", "SmallFireball");
		register("witch", "Witch");
		register("wither", "WitherBoss");
		register("stray", "Stray");
		register("creeper", "Creeper");
		register("wither_skull", "WitherSkull");
		register("polar_bear", "PolarBear");
		register("potion", "ThrownPotion");
		register("furnace_minecart", "MinecartFurnace");
		register("shulker", "Shulker");
		register("spectral_arrow", "SpectralArrow");
		register("tnt_minecart", "MinecartTNT");
		register("cave_spider", "CaveSpider");
		register("boat", "Boat");
		register("wolf", "Wolf");
		register("villager", "Villager");
		register("ocelot", "Ozelot");
		register("vex", "Vex");
		register("pig", "Pig");
		register("rabbit", "Rabbit");
		register("magma_cube", "LavaSlime");
		register("zombie", "Zombie");
		register("ghast", "Ghast");
		register("bat", "Bat");
		register("blaze", "Blaze");
		register("llama", "Llama");
		register("chicken", "Chicken");
		register("slime", "Slime");
		register("xp_orb", "XPOrb");
		register("mooshroom", "MushroomCow");
		register("commandblock_minecart", "MinecartCommandBlock");
		register("armor_stand", "ArmorStand");
		register("donkey", "Donkey");
		register("leash_knot", "LeashKnot");
		register("skeleton", "Skeleton");
		register("horse", "Horse");
		register("spawner_minecart", "MinecartSpawner");
		register("minecart", "MinecartRideable");
		register("chest_minecart", "MinecartChest");
		register("eye_of_ender_signal", "EyeOfEnderSignal");
		register("shulker_bullet", "ShulkerBullet");
		register("ender_dragon", "EnderDragon");
		register("enderman", "Enderman");
		register("llama_spit", "LlamaSpit");
		register("snowball", "Snowball");
		register("tnt", "PrimedTnt");
		register("squid", "Squid");
	}

	private static void register(String name, String legacy) {
		legacyNames.put(name, legacy);
		legacyNames.put("minecraft:"+name, legacy);
	}

	public static String getLegacyName(String name) {
		return legacyNames.getOrDefault(name, "Unknown");
	}

}
