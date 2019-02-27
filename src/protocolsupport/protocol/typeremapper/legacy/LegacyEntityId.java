package protocolsupport.protocol.typeremapper.legacy;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.EntityType;

import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyEntityId {

	protected static final Map<NetworkEntityType, String> toLegacyStringId = new EnumMap<>(NetworkEntityType.class);
	protected static final Map<String, NetworkEntityType> fromLegacyStringId = new HashMap<>();

	protected static void register(NetworkEntityType type, String stringId) {
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
		register(NetworkEntityType.ZOMBIE_PIGMAN, "PigZombie");
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
		register(NetworkEntityType.ARMOR_STAND_MOB, "ArmorStand");
		register(NetworkEntityType.ARMOR_STAND_OBJECT, "ArmorStand");
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

	public static String getStringId(NetworkEntityType type) {
		return toLegacyStringId.getOrDefault(type, type.getKey());
	}

	@SuppressWarnings("deprecation")
	public static int getIntId(NetworkEntityType type) {
		return type.getBukkitType().getTypeId();
	}

	public static NetworkEntityType getTypeByStringId(String stringId) {
		return fromLegacyStringId.getOrDefault(stringId, NetworkEntityType.NONE);
	}

	@SuppressWarnings("deprecation")
	public static NetworkEntityType getTypeByIntId(int intId) {
		EntityType btype = EntityType.fromId(intId);
		return btype != null ? NetworkEntityType.getByBukkitType(btype) : NetworkEntityType.NONE;
	}

}
