package protocolsupport.protocol.typeremapper.pe;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.bukkit.Particle;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonParser;
import protocolsupport.utils.ResourceUtils;
import protocolsupport.utils.Utils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class PEDataValues {

	public static String getResourcePath(String name) {
		return "pe/" + name;
	}

	public static BufferedReader getResource(String name) {
		return ResourceUtils.getAsBufferedReader(getResourcePath(name));
	}

	private static JsonObject getFileObject(String name) {
		return new JsonParser().parse(getResource(name)).getAsJsonObject();
	}

	private static final EnumMap<NetworkEntityType, String> entityKey = new EnumMap<>(NetworkEntityType.class);
	private static final EnumMap<NetworkEntityType, Integer> entityNetworkId = new EnumMap<>(NetworkEntityType.class);
	private static final Int2ObjectOpenHashMap<NetworkEntityType> entityNetworkIdToType = new Int2ObjectOpenHashMap<>();

	private static void registerEntity(NetworkEntityType type, Integer id, String key) {
		entityKey.put(type, key);
		entityNetworkId.put(type, id);
		if (id != null) {
			entityNetworkIdToType.put((int) id, type);
		}
	}

	static {
		//registerEntity(NetworkEntityType.NONE, null, null);
		//registerEntity(NetworkEntityType.ENTITY, null, null);
		//registerEntity(NetworkEntityType.LIVING, null, null);
		//registerEntity(NetworkEntityType.INSENTIENT, null, null);
		registerEntity(NetworkEntityType.PLAYER, null, "minecraft:player");
		//registerEntity(NetworkEntityType.AGEABLE, null, null);
		//registerEntity(NetworkEntityType.TAMEABLE, null, null);
		//registerEntity(NetworkEntityType.BASE_FISH, null, null);
		registerEntity(NetworkEntityType.EXP_ORB, null, "minecraft:xp_orb");
		registerEntity(NetworkEntityType.PAINTING, null, "minecraft:painting");
		//registerEntity(NetworkEntityType.THUNDERBOLT, null, null);
		registerEntity(NetworkEntityType.COW, 11, "minecraft:cow");
		registerEntity(NetworkEntityType.MUSHROOM_COW, 16, "minecraft:mooshroom");
		registerEntity(NetworkEntityType.CHICKEN, 10, "minecraft:chicken");
		registerEntity(NetworkEntityType.SQUID, 17, "minecraft:squid");
		registerEntity(NetworkEntityType.COMMON_HORSE, 23, "minecraft:horse");
		registerEntity(NetworkEntityType.ZOMBIE_HORSE, 27, "minecraft:zombie_horse");
		registerEntity(NetworkEntityType.SKELETON_HORSE, 26, "minecraft:skeleton_horse");
		registerEntity(NetworkEntityType.DONKEY, 24, "minecraft:donkey");
		registerEntity(NetworkEntityType.MULE, 25, "minecraft:mule");
		registerEntity(NetworkEntityType.LAMA, 29, "minecraft:llama");
		registerEntity(NetworkEntityType.BAT, 19, "minecraft:bat");
		registerEntity(NetworkEntityType.OCELOT, 22, "minecraft:cat");
		registerEntity(NetworkEntityType.CAT, 22, "minecraft:cat");
		registerEntity(NetworkEntityType.WOLF, 14, "minecraft:wolf");
		registerEntity(NetworkEntityType.PIG, 12, "minecraft:pig");
		registerEntity(NetworkEntityType.RABBIT, 18, "minecraft:rabbit");
		registerEntity(NetworkEntityType.SHEEP, 13, "minecraft:sheep");
		registerEntity(NetworkEntityType.POLAR_BEAR, 28, "minecraft:polar_bear");
		registerEntity(NetworkEntityType.VILLAGER, 15, "minecraft:villager");
		registerEntity(NetworkEntityType.ENDERMAN, 38, "minecraft:enderman");
		//registerEntity(NetworkEntityType.GIANT, null, null);
		registerEntity(NetworkEntityType.SILVERFISH, 39, "minecraft:silverfish");
		registerEntity(NetworkEntityType.ENDERMITE, 55, "minecraft:endermite");
		registerEntity(NetworkEntityType.ENDER_DRAGON, 53, "minecraft:ender_dragon");
		registerEntity(NetworkEntityType.SNOWMAN, 21, "minecraft:snow_golem");
		registerEntity(NetworkEntityType.ZOMBIE, 32, "minecraft:zombie");
		registerEntity(NetworkEntityType.ZOMBIE_VILLAGER, 44, "minecraft:zombie_villager");
		registerEntity(NetworkEntityType.HUSK, 47, "minecraft:husk");
		registerEntity(NetworkEntityType.ZOMBIE_PIGMAN, 36, "minecraft:zombie_pigman");
		registerEntity(NetworkEntityType.DROWNED, 110, "minecraft:drowned");
		registerEntity(NetworkEntityType.BLAZE, 43, "minecraft:blaze");
		registerEntity(NetworkEntityType.SPIDER, 35, "minecraft:spider");
		registerEntity(NetworkEntityType.CAVE_SPIDER, 40, "minecraft:cave_spider");
		registerEntity(NetworkEntityType.CREEPER, 33, "minecraft:creeper");
		registerEntity(NetworkEntityType.GHAST, 41, "minecraft:ghast");
		registerEntity(NetworkEntityType.SLIME, 37, "minecraft:slime");
		registerEntity(NetworkEntityType.MAGMA_CUBE, 42, "minecraft:magma_cube");
		registerEntity(NetworkEntityType.SKELETON, 34, "minecraft:skeleton");
		registerEntity(NetworkEntityType.WITHER_SKELETON, 48, "minecraft:wither_skeleton");
		registerEntity(NetworkEntityType.STRAY, 46, "minecraft:stray");
		registerEntity(NetworkEntityType.WITCH, 45, "minecraft:witch");
		registerEntity(NetworkEntityType.IRON_GOLEM, 20, "minecraft:iron_golem");
		registerEntity(NetworkEntityType.SHULKER, 54, "minecraft:shulker");
		registerEntity(NetworkEntityType.WITHER, 52, "minecraft:wither");
		registerEntity(NetworkEntityType.GUARDIAN, 49, "minecraft:guardian");
		registerEntity(NetworkEntityType.ELDER_GUARDIAN, 50, "minecraft:elder_guardian");
		registerEntity(NetworkEntityType.VINDICATOR, 57, "minecraft:vindicator");
		registerEntity(NetworkEntityType.EVOKER, 104, "minecraft:evocation_illager");
		//registerEntity(NetworkEntityType.ILLUSIONER, null, null);
		registerEntity(NetworkEntityType.VEX, 105, "minecraft:vex");
		registerEntity(NetworkEntityType.PARROT, 30, "minecraft:parrot");
		registerEntity(NetworkEntityType.PHANTOM, 58, "minecraft:phantom");
		registerEntity(NetworkEntityType.DOLPHIN, 31, "minecraft:dolphin");
		registerEntity(NetworkEntityType.TURTLE, 74, "minecraft:turtle");
		registerEntity(NetworkEntityType.COD, 112, "minecraft:cod");
		registerEntity(NetworkEntityType.SALMON, 109, "minecraft:salmon");
		registerEntity(NetworkEntityType.PUFFERFISH, 108, "minecraft:pufferfish");
		registerEntity(NetworkEntityType.TROPICAL_FISH, 111, "minecraft:tropicalfish");
		registerEntity(NetworkEntityType.ARMOR_STAND_OBJECT, 61, "minecraft:armor_stand");
		registerEntity(NetworkEntityType.BOAT, null, "minecraft:boat");
		registerEntity(NetworkEntityType.TNT, null, "minecraft:tnt");
		registerEntity(NetworkEntityType.SNOWBALL, null, "minecraft:snowball");
		registerEntity(NetworkEntityType.EGG, null, "minecraft:egg");
		registerEntity(NetworkEntityType.FIREBALL, 85, "minecraft:fireball");
		registerEntity(NetworkEntityType.FIRECHARGE, 64, "minecraft:small_fireball");
		registerEntity(NetworkEntityType.ENDERPEARL, null, "minecraft:ender_pearl");
		registerEntity(NetworkEntityType.WITHER_SKULL, null, "minecraft:wither_skull");
		registerEntity(NetworkEntityType.FALLING_OBJECT, null, "minecraft:falling_block");
		registerEntity(NetworkEntityType.ENDEREYE, null, "minecraft:eye_of_ender_signal");
		registerEntity(NetworkEntityType.POTION, null, "minecraft:splash_potion");
		registerEntity(NetworkEntityType.EXP_BOTTLE, null, "minecraft:xp_bottle");
		registerEntity(NetworkEntityType.LEASH_KNOT, null, "minecraft:leash_knot");
		registerEntity(NetworkEntityType.FISHING_FLOAT, null, "minecraft:fishing_hook");
		registerEntity(NetworkEntityType.ITEM, null, "minecraft:item");
		registerEntity(NetworkEntityType.ARROW, null, "minecraft:arrow");
		//registerEntity(NetworkEntityType.SPECTRAL_ARROW, null, null);
		//registerEntity(NetworkEntityType.TIPPED_ARROW, null, null);
		registerEntity(NetworkEntityType.THROWN_TRIDENT, null, "minecraft:thrown_trident");
		registerEntity(NetworkEntityType.FIREWORK, null, "minecraft:fireworks_rocket");
		registerEntity(NetworkEntityType.ITEM_FRAME, 199, "minecraft:item_frame");
		registerEntity(NetworkEntityType.ENDER_CRYSTAL, null, "minecraft:ender_crystal");
		registerEntity(NetworkEntityType.AREA_EFFECT_CLOUD, null, "minecraft:area_effect_cloud");
		registerEntity(NetworkEntityType.SHULKER_BULLET, null, "minecraft:shulker_bullet");
		registerEntity(NetworkEntityType.LAMA_SPIT, null, "minecraft:llama_spit");
		registerEntity(NetworkEntityType.DRAGON_FIREBALL, null, "minecraft:dragon_fireball");
		registerEntity(NetworkEntityType.EVOCATOR_FANGS, null, "minecraft:evocation_fang");
		//registerEntity(NetworkEntityType.ARMOR_STAND_MOB, null, "minecraft:armor_stand");
		registerEntity(NetworkEntityType.MINECART, null, "minecraft:minecart");
		registerEntity(NetworkEntityType.MINECART_CHEST, null, "minecraft:chest_minecart");
		//registerEntity(NetworkEntityType.MINECART_FURNACE, null, null);
		registerEntity(NetworkEntityType.MINECART_TNT, null, "minecraft:tnt_minecart");
		//registerEntity(NetworkEntityType.MINECART_MOB_SPAWNER, null, null);
		registerEntity(NetworkEntityType.MINECART_HOPPER, null, "minecraft:hopper_minecart");
		registerEntity(NetworkEntityType.MINECART_COMMAND, null, "minecraft:command_block_minecart");
		//registerEntity(NetworkEntityType.NPC, null, "minecraft:npc");
		registerEntity(NetworkEntityType.PANDA, null, "minecraft:panda");
		registerEntity(NetworkEntityType.PILLAGER, null, "minecraft:pillager");
		registerEntity(NetworkEntityType.FOX, null, "minecraft:fox");
		registerEntity(NetworkEntityType.TRADER_LAMA, null, "minecraft:villager");
		registerEntity(NetworkEntityType.WANDERING_TRADER, null, "minecraft:villager");
		//registerEntity(NetworkEntityType.BALLOON, null, "minecraft:balloon");
		//registerEntity(NetworkEntityType.WITHER_SKULL_DANGEROUS, null, "minecraft:wither_skull_dangerous");
		//registerEntity(NetworkEntityType.LIGHTNING_BOLT, null, "minecraft:lightning_bolt");
		//registerEntity(NetworkEntityType.LINGERING_POTION, null, "minecraft:lingering_potion");
		//registerEntity(NetworkEntityType.EVOCATION_ILLAGER, null, "minecraft:evocation_illager");
		//registerEntity(NetworkEntityType.AGENT, null, "minecraft:agent");
		//registerEntity(NetworkEntityType.ICE_BOMB, null, "minecraft:ice_bomb");
		//registerEntity(NetworkEntityType.TRIPOD_CAMERA, null, "minecraft:tripod_camera"
	}

	public static NetworkEntityType getEntityTypeFromNetworkId(int networkId) {
		return entityNetworkIdToType.get(networkId);
	}

	public static int getEntityNetworkId(NetworkEntityType type) {
		Integer id = entityNetworkId.get(type);
		if (id == null) {
			System.err.println("Missing PE entity id for " + type);
			return entityNetworkId.get(NetworkEntityType.ARMOR_STAND_OBJECT);
		}
		return id;
	}

	public static String getEntityKey(NetworkEntityType type) {
		String key = entityKey.get(type);
		if (key == null) {
			System.err.println("Missing PE entity key for " + type);
			return entityKey.get(NetworkEntityType.ARMOR_STAND_OBJECT);
		}
		return key;
	}

	private static final Object2IntOpenHashMap<String> pcEnchantToPe = new Object2IntOpenHashMap<>();
	private static final Int2ObjectOpenHashMap<String> peEnchantToPc = new Int2ObjectOpenHashMap<>();

	private static void registerEnchantRemap(Enchantment enchantment, int peId) {
		pcEnchantToPe.put(enchantment.getKey().toString(), peId);
		peEnchantToPc.put(peId, enchantment.getKey().toString());
	}

	static {
		registerEnchantRemap(Enchantment.PROTECTION_ENVIRONMENTAL, 0);
		registerEnchantRemap(Enchantment.PROTECTION_FIRE, 1);
		registerEnchantRemap(Enchantment.PROTECTION_FALL, 2);
		registerEnchantRemap(Enchantment.PROTECTION_EXPLOSIONS, 3);
		registerEnchantRemap(Enchantment.PROTECTION_PROJECTILE, 4);
		registerEnchantRemap(Enchantment.THORNS, 5);
		registerEnchantRemap(Enchantment.OXYGEN, 6);
		registerEnchantRemap(Enchantment.DEPTH_STRIDER, 7);
		registerEnchantRemap(Enchantment.WATER_WORKER, 8);
		registerEnchantRemap(Enchantment.DAMAGE_ALL, 9);
		registerEnchantRemap(Enchantment.DAMAGE_UNDEAD, 10);
		registerEnchantRemap(Enchantment.DAMAGE_ARTHROPODS, 11);
		registerEnchantRemap(Enchantment.KNOCKBACK, 12);
		registerEnchantRemap(Enchantment.FIRE_ASPECT, 13);
		registerEnchantRemap(Enchantment.LOOT_BONUS_MOBS, 14);
		registerEnchantRemap(Enchantment.DIG_SPEED, 15);
		registerEnchantRemap(Enchantment.SILK_TOUCH, 16);
		registerEnchantRemap(Enchantment.DURABILITY, 17);
		registerEnchantRemap(Enchantment.LOOT_BONUS_BLOCKS, 18);
		registerEnchantRemap(Enchantment.ARROW_DAMAGE, 19);
		registerEnchantRemap(Enchantment.ARROW_KNOCKBACK, 20);
		registerEnchantRemap(Enchantment.ARROW_FIRE, 21);
		registerEnchantRemap(Enchantment.ARROW_INFINITE, 22);
		registerEnchantRemap(Enchantment.LUCK, 23);
		registerEnchantRemap(Enchantment.LURE, 24);
		registerEnchantRemap(Enchantment.FROST_WALKER, 25);
		registerEnchantRemap(Enchantment.MENDING, 26);
		registerEnchantRemap(Enchantment.BINDING_CURSE, 27);
		registerEnchantRemap(Enchantment.VANISHING_CURSE, 28);
		registerEnchantRemap(Enchantment.IMPALING, 29);
		registerEnchantRemap(Enchantment.RIPTIDE, 30);
		registerEnchantRemap(Enchantment.LOYALTY, 31);
		registerEnchantRemap(Enchantment.CHANNELING, 32);
	}

	public static int pcToPeEnchant(String pcKey) {
		return pcEnchantToPe.getInt(pcKey);
	}

	public static String peToPcEnchant(int peId) {
		return peEnchantToPc.get(peId);
	}

	public static final IdRemappingRegistry<HashMapBasedIdRemappingTable> PARTICLE = new IdRemappingRegistry<HashMapBasedIdRemappingTable>() {
		{
			//TODO: Check values. (Speculative = names don't match) Only a few values have been tested by hand.
			registerRemapEntry(Particle.EXPLOSION_NORMAL.ordinal(), PELevelEvent.PARTICLE_EXPLODE, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.EXPLOSION_LARGE.ordinal(), PELevelEvent.PARTICLE_HUGE_EXPLOSION, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.EXPLOSION_HUGE.ordinal(), PELevelEvent.PARTICLE_HUGE_EXPLOSION_SEED, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.WATER_BUBBLE.ordinal(), PELevelEvent.PARTICLE_BUBBLE, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.WATER_SPLASH.ordinal(), PELevelEvent.PARTICLE_SPLASH, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.WATER_WAKE.ordinal(), PELevelEvent.PARTICLE_WATER_WAKE, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.CRIT.ordinal(), PELevelEvent.PARTICLE_CRITICAL, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.CRIT_MAGIC.ordinal(), PELevelEvent.PARTICLE_CRITICAL, ProtocolVersionsHelper.ALL_PE); //Magiccrit..?
			registerRemapEntry(Particle.SMOKE_NORMAL.ordinal(), PELevelEvent.PARTICLE_SMOKE, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.SMOKE_LARGE.ordinal(), PELevelEvent.PARTICLE_LARGE_SMOKE, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.SPELL.ordinal(), PELevelEvent.PARTICLE_MOB_SPELL, ProtocolVersionsHelper.ALL_PE); //Speculative
			registerRemapEntry(Particle.SPELL_INSTANT.ordinal(), PELevelEvent.PARTICLE_MOB_SPELL_INSTANTANIOUS, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.SPELL_MOB.ordinal(), PELevelEvent.PARTICLE_MOB_SPELL, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.SPELL_MOB_AMBIENT.ordinal(), PELevelEvent.PARTICLE_MOB_SPELL_INSTANTANIOUS, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.SPELL_WITCH.ordinal(), PELevelEvent.PARTICLE_WITCH_SPELL, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.DRIP_WATER.ordinal(), PELevelEvent.PARTICLE_DRIP_WATER, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.DRIP_LAVA.ordinal(), PELevelEvent.PARTICLE_DRIP_LAVA, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.VILLAGER_ANGRY.ordinal(), PELevelEvent.PARTICLE_VILLAGER_ANGRY, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.VILLAGER_HAPPY.ordinal(), PELevelEvent.PARTICLE_VILLAGER_HAPPY, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.TOWN_AURA.ordinal(), PELevelEvent.PARTICLE_TOWN_AURA, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.NOTE.ordinal(), PELevelEvent.PARTICLE_NOTE, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.PORTAL.ordinal(), PELevelEvent.PARTICLE_PORTAL, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.ENCHANTMENT_TABLE.ordinal(), PELevelEvent.PARTICLE_ENCHANTMENT_TABLE, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.FLAME.ordinal(), PELevelEvent.PARTICLE_FLAME, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.LAVA.ordinal(), PELevelEvent.PARTICLE_LAVA, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.REDSTONE.ordinal(), PELevelEvent.PARTICLE_RISING_RED_DUST, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.SNOWBALL.ordinal(), PELevelEvent.PARTICLE_SNOWBALL_POOF, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.SLIME.ordinal(), PELevelEvent.PARTICLE_SLIME, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.HEART.ordinal(), PELevelEvent.PARTICLE_HEART, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.BARRIER.ordinal(), PELevelEvent.PARTICLE_BLOCK_FORCE_FIELD, ProtocolVersionsHelper.ALL_PE); //Speculative
			registerRemapEntry(Particle.WATER_DROP.ordinal(), PELevelEvent.CAULDRON_TAKE_WATER, ProtocolVersionsHelper.ALL_PE); //Speculative
			registerRemapEntry(Particle.DRAGON_BREATH.ordinal(), PELevelEvent.PARTICLE_DRAGONS_BREATH, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.END_ROD.ordinal(), PELevelEvent.PARTICLE_END_ROT, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Particle.FALLING_DUST.ordinal(), PELevelEvent.PARTICLE_FALLING_DUST, ProtocolVersionsHelper.ALL_PE);
		}

		@Override
		protected HashMapBasedIdRemappingTable createTable() {
			return new HashMapBasedIdRemappingTable();
		}
	};

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> BIOME = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		{
			registerRemapEntry(Biome.SMALL_END_ISLANDS, Biome.THE_END, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.END_MIDLANDS, Biome.THE_END, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.END_HIGHLANDS, Biome.THE_END, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.END_BARRENS, Biome.THE_END, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.THE_VOID, Biome.THE_END, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.WARM_OCEAN, 40, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.LUKEWARM_OCEAN, 42, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.COLD_OCEAN, 44, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.DEEP_WARM_OCEAN, 41, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.DEEP_LUKEWARM_OCEAN, 43, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.DEEP_COLD_OCEAN, 45, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.FROZEN_OCEAN, 46, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(Biome.DEEP_FROZEN_OCEAN, 47, ProtocolVersionsHelper.ALL_PE);
		}

		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(167); //Largest biome id.
		}

		private void registerRemapEntry(Biome type, int to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				registerRemapEntry(type.ordinal(), to, version);
			}
		}

		private void registerRemapEntry(Biome type, Biome to, ProtocolVersion... versions) {
			registerRemapEntry(type.ordinal(), to.ordinal(), versions);
		}
	};

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> WINDOWTYPE = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		{
			registerRemapEntry(WindowType.PLAYER, -1, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.GENERIC_9X1, 0, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.GENERIC_9X2, 0, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.GENERIC_9X3, 0, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.GENERIC_9X4, 0, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.GENERIC_9X5, 0, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.GENERIC_9X6, 0, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.CRAFTING, 1, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.FURNACE, 2, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.ENCHANTMENT, 3, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.BREWING_STAND, 4, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.ANVIL, 5, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.GENERIC_3X3, 6, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.HOPPER, 8, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.HORSE, 12, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.BEACON, 13, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.MERCHANT, 15, ProtocolVersionsHelper.ALL_PE);
		}

		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(32);
		}

		private void registerRemapEntry(WindowType type, int to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				registerRemapEntry(type.ordinal(), to, version);
			}
		}
	};

	private static final EnumMap<NetworkEntityType, Int2IntOpenHashMap> entityStatusRemaps = new EnumMap<>(NetworkEntityType.class);

	private static void registerEntityStatusRemap(int pcStatus, int peStatus, NetworkEntityType... entityTypes) {
		for (NetworkEntityType entityType : entityTypes) {
			Int2IntOpenHashMap mapping = entityStatusRemaps.computeIfAbsent(entityType, m -> new Int2IntOpenHashMap());
			mapping.put(pcStatus, peStatus);
		}
	}

	private static void registerEntityStatusRemap(int pcStatus, int peStatus) {
		registerEntityStatusRemap(pcStatus, peStatus, NetworkEntityType.values());
	}

	private static void initEntityStatusRemaps() {
		registerEntityStatusRemap(2, 2); // HURT_ANIMATION
		registerEntityStatusRemap(33, 2); // ENTITY_HURT_THORNS
		registerEntityStatusRemap(36, 2); // ENTITY_HURT_DROWN
		registerEntityStatusRemap(37, 2); // ENTITY_HURT_BURN
		registerEntityStatusRemap(3, -1, NetworkEntityType.SNOWBALL); // SNOWBALL_POOF
		registerEntityStatusRemap(3, -1, NetworkEntityType.EGG); // EGG_ICONCRACK
		registerEntityStatusRemap(3, 3); // DEATH_ANIMATION
		registerEntityStatusRemap(6, 6); // TAME_FAIL
		registerEntityStatusRemap(7, 7); // TAME_SUCCESS
		registerEntityStatusRemap(8, 8); // SHAKE_WET
		registerEntityStatusRemap(9, 9); // USE_ITEM
		registerEntityStatusRemap(10, 10, NetworkEntityType.SHEEP); // EAT_GRASS_ANIMATION
		registerEntityStatusRemap(10, 10, NetworkEntityType.COMMON_HORSE); // EAT_GRASS_ANIMATION
		registerEntityStatusRemap(10, 10, NetworkEntityType.MINECART_TNT); // MINECART_TNT_PRIME_FUSE
		registerEntityStatusRemap(11, 19); // IRON_GOLEM_OFFER_FLOWER
		registerEntityStatusRemap(15, 24); // WITCH_SPELL_PARTICLES
		registerEntityStatusRemap(16, 16); // ZOMBIE_VILLAGER_CURE
		registerEntityStatusRemap(17, 25); // FIREWORK_PARTICLES
		registerEntityStatusRemap(18, 21); // LOVE_PARTICLES
		registerEntityStatusRemap(20, 27); // SILVERFISH_SPAWN_ANIMATION
		registerEntityStatusRemap(31, 13); // FISH_HOOK_HOOK
		registerEntityStatusRemap(34, 20); // IRON_GOLEM_WITHDRAW_FLOWER
		registerEntityStatusRemap(35, 65); // CONSUME_TOTEM

		/*
		TODO: List of known PC entity status codes that are currently missing remaps. This
		might result in	missing/broken functionality on PE.
		TIPPED_ARROW = 0;
		ROTATED_JUMP_rabbit = 1;
		RESET_SPAWNER_minecartspawner = 1;
		SNOWBALL_POOF_snowball = 3;
		EGG_ICONCRACK_egg = 3;
		ATTACK = 4;
		VILLAGER_MATE_HEARTS = 12;
		VILLAGER_ANGRY = 13;
		VILLAGER_HAPPY = 14;
		RESET_ROTATION = 19;
		ENABLE_REDUCED_DEBUG = 22;
		DISABLE_REDUCED_DEBUG = 23;
		SET_OP_LEVEL_0 = 24;
		SET_OP_LEVEL_1 = 25;
		SET_OP_LEVEL_2 = 26;
		SET_OP_LEVEL_3 = 27
		SET_OP_LEVEL_4 = 28;
		SHIELD_BLOCK = 29;
		SHIELD_BREAK = 30;
		ARMOR_STAND_HIT = 32;

		TODO: List of known PE entity status codes that are never sent. Presumably,
		some PE functionality is missing because of this.
		FISH_HOOK_BUBBLE = 11;
		FISH_HOOK_POSITION = 12;
		FISH_HOOK_TEASE = 14;
		SQUID_INK_CLOUD = 15;
		RESPAWN = 18;
		WITCH_DRINK_POTION = 29;
		WITCH_THROW_POTION = 30;
		PLAYER_ADD_XP_LEVELS = 34;
		ELDER_GUARDIAN_CURSE = 35;
		AGENT_ARM_SWING = 36;
		ENDER_DRAGON_DEATH = 37;
		DUST_PARTICLES = 38;
		ARROW_SHAKE = 39;
		BABY_ANIMAL_FEED = 60;
		DEATH_SMOKE_CLOUD = 61;
		COMPLETE_TRADE = 62;
		PLAYER_CHECK_TREASURE_HUNTER_ACHIEVEMENT = 66;
		ENTITY_SPAWN = 67;
		DRAGON_PUKE = 68;
		ITEM_ENTITY_MERGE = 69;
		*/

		/*
		sent elsewhere
		EATING_ITEM = 57; sent in Animation
		 */
	}

	static {
		initEntityStatusRemaps();
	}

	/**
	 * Return the PE entity status code for the given PC entity status code and
	 * entity type. If no mapping is possible, -1 is returned.
	 */
	public static int getEntityStatusRemap(int pcStatus, NetworkEntityType entityType) {
		return entityStatusRemaps.get(entityType).getOrDefault(pcStatus, -1);
	}

	private final static Map<NetworkEntityType, PEEntityData> entityData = new EnumMap<>(NetworkEntityType.class);
	private final static Map<NetworkEntityType, PEEntityInventoryData> entityInventoryData = new EnumMap<>(NetworkEntityType.class);

	static {
		getFileObject("entitydata.json").entrySet().forEach(entry -> {
			entityData.put(NetworkEntityType.valueOf(entry.getKey()), Utils.GSON.fromJson(entry.getValue(), PEEntityData.class));
		});
		getFileObject("entitydata_inventory.json").entrySet().forEach(entry -> {
			entityInventoryData.put(NetworkEntityType.valueOf(entry.getKey()), Utils.GSON.fromJson(entry.getValue(), PEEntityInventoryData.class).init());
		});
	}

	public static PEEntityData getEntityData(NetworkEntityType type) {
		return entityData.get(type);
	}

	public static PEEntityInventoryData getEntityInventoryData(NetworkEntityType type) {
		return entityInventoryData.get(type);
	}

	public static class PEEntityData {
		@SerializedName("BoundingBox")
		private BoundingBox boundingBox;

		@SerializedName("Offset")
		private Offset offset;

		@SerializedName("RiderInfo")
		private RiderInfo riderInfo;

		public BoundingBox getBoundingBox() {
			return boundingBox;
		}

		public Offset getOffset() {
			return offset;
		}

		public RiderInfo getRiderInfo() {
			return riderInfo;
		}

		public static class BoundingBox {
			private float width;
			private float height;

			public float getWidth() {
				return width;
			}

			public float getHeight() {
				return height;
			}
		}

		public static class Offset {
			private float x;
			private float y;
			private float z;
			private byte yaw;
			private byte pitch;

			public float getX() {
				return x;
			}

			public float getY() {
				return y;
			}

			public float getZ() {
				return z;
			}

			public byte getYaw() {
				return yaw;
			}

			public byte getPitch() {
				return pitch;
			}
		}

		public static class RiderInfo {
			private double x;
			private double y;
			private double z;
			private final Float rotationlock = null; //Optional

			public Vector getPosition() {
				return new Vector(x, y + 1.2, z);
			}

			public Float getRotationLock() {
				return rotationlock;
			}
		}
	}

	public static class PEEntityInventoryData {
		@SerializedName("InventoryFilter")
		private PocketInventoryFilter inventoryFilter;

		public PocketInventoryFilter getInventoryFilter() {
			return inventoryFilter;
		}

		public PEEntityInventoryData init() {
			if (inventoryFilter != null) {
				inventoryFilter.init();
			}
			return this;
		}

		public static class PocketInventoryFilter {
			private String Filter;
			private transient NBTCompound filterNBT = null;

			protected void init() {
				try {
					filterNBT = MojangsonParser.parse(Filter.replaceAll("'", "\""));
				} catch (IOException e) {
					throw new RuntimeException("Failed to parse NBT JSON: " + Filter, e);
				}
			}

			public NBTCompound getFilter() {
				return filterNBT;
			}
		}
	}
}
