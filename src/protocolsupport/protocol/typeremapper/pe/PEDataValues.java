package protocolsupport.protocol.typeremapper.pe;

import java.io.BufferedReader;
import java.util.EnumMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.bukkit.Particle;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.legacy.LegacyEnchantmentId;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.utils.Utils;

public class PEDataValues {

	public static String getResourcePath(String name) {
		return "pe/" + name;
	}

	public static BufferedReader getResource(String name) {
		return Utils.getResourceBuffered(getResourcePath(name));
	}

	private static JsonObject getFileObject(String name) {
		return new JsonParser().parse(getResource(name)).getAsJsonObject();
	}

	private static final EnumMap<NetworkEntityType, String> entityKey = new EnumMap<>(NetworkEntityType.class);
	private static final EnumMap<NetworkEntityType, Integer> entityNetworkId = new EnumMap<>(NetworkEntityType.class);
	private static final Int2ObjectOpenHashMap<NetworkEntityType> entityNetworkIdToType = new Int2ObjectOpenHashMap<>();
	static {
		entityNetworkId.put(NetworkEntityType.WITHER_SKELETON, 48);
		entityNetworkId.put(NetworkEntityType.WOLF, 14);
		entityNetworkId.put(NetworkEntityType.RABBIT, 18);
		entityNetworkId.put(NetworkEntityType.CHICKEN, 10);
		entityNetworkId.put(NetworkEntityType.COW, 11);
		entityNetworkId.put(NetworkEntityType.SHEEP, 13);
		entityNetworkId.put(NetworkEntityType.PIG, 12);
		entityNetworkId.put(NetworkEntityType.MUSHROOM_COW, 16);
		entityNetworkId.put(NetworkEntityType.SHULKER, 54);
		entityNetworkId.put(NetworkEntityType.GUARDIAN, 49);
		entityNetworkId.put(NetworkEntityType.ENDERMITE, 55);
		entityNetworkId.put(NetworkEntityType.WITCH, 45);
		entityNetworkId.put(NetworkEntityType.BAT, 19);
		entityNetworkId.put(NetworkEntityType.WITHER, 52);
		entityNetworkId.put(NetworkEntityType.ENDER_DRAGON, 53);
		entityNetworkId.put(NetworkEntityType.MAGMA_CUBE, 42);
		entityNetworkId.put(NetworkEntityType.BLAZE, 43);
		entityNetworkId.put(NetworkEntityType.SILVERFISH, 39);
		entityNetworkId.put(NetworkEntityType.CAVE_SPIDER, 40);
		entityNetworkId.put(NetworkEntityType.ENDERMAN, 38);
		entityNetworkId.put(NetworkEntityType.ZOMBIE_PIGMAN, 36);
		entityNetworkId.put(NetworkEntityType.GHAST, 41);
		entityNetworkId.put(NetworkEntityType.SLIME, 37);
		entityNetworkId.put(NetworkEntityType.ZOMBIE, 32);
		entityNetworkId.put(NetworkEntityType.SPIDER, 35);
		entityNetworkId.put(NetworkEntityType.SKELETON, 34);
		entityNetworkId.put(NetworkEntityType.CREEPER, 33);
		entityNetworkId.put(NetworkEntityType.VILLAGER, 15);
		entityNetworkId.put(NetworkEntityType.MULE, 25);
		entityNetworkId.put(NetworkEntityType.DONKEY, 24);
		entityNetworkId.put(NetworkEntityType.ZOMBIE_HORSE, 27);
		entityNetworkId.put(NetworkEntityType.SKELETON_HORSE, 26);
		entityNetworkId.put(NetworkEntityType.ZOMBIE_VILLAGER, 44);
		entityNetworkId.put(NetworkEntityType.HUSK, 47);
		entityNetworkId.put(NetworkEntityType.SQUID, 17);
		entityNetworkId.put(NetworkEntityType.STRAY, 46);
		entityNetworkId.put(NetworkEntityType.POLAR_BEAR, 28);
		entityNetworkId.put(NetworkEntityType.ELDER_GUARDIAN, 50);
		entityNetworkId.put(NetworkEntityType.COMMON_HORSE, 23);
		entityNetworkId.put(NetworkEntityType.IRON_GOLEM, 20);
		entityNetworkId.put(NetworkEntityType.OCELOT, 22);
		entityNetworkId.put(NetworkEntityType.SNOWMAN, 21);
		entityNetworkId.put(NetworkEntityType.LAMA, 29);
		entityNetworkId.put(NetworkEntityType.PARROT, 30);
		entityNetworkId.put(NetworkEntityType.ARMOR_STAND_MOB, 61);
		entityNetworkId.put(NetworkEntityType.VINDICATOR, 57);
		entityNetworkId.put(NetworkEntityType.EVOKER, 104);
		entityNetworkId.put(NetworkEntityType.VEX, 105);
		entityNetworkId.put(NetworkEntityType.DOLPHIN, 31);
		entityNetworkId.put(NetworkEntityType.PUFFERFISH, 108);
		entityNetworkId.put(NetworkEntityType.SALMON, 109);
		entityNetworkId.put(NetworkEntityType.TROPICAL_FISH, 111);
		entityNetworkId.put(NetworkEntityType.COD, 112);
		entityNetworkId.put(NetworkEntityType.DROWNED, 110);
		entityNetworkId.put(NetworkEntityType.TURTLE, 74);
		entityNetworkId.put(NetworkEntityType.PHANTOM, 58);
		
		//entityKey.put(NetworkEntityType.NPC, "minecraft:npc");
		entityKey.put(NetworkEntityType.PLAYER, "minecraft:player");
		entityKey.put(NetworkEntityType.WITHER_SKELETON, "minecraft:wither_skeleton");
		entityKey.put(NetworkEntityType.HUSK, "minecraft:husk");
		entityKey.put(NetworkEntityType.STRAY, "minecraft:stray");
		entityKey.put(NetworkEntityType.WITCH, "minecraft:witch");
		entityKey.put(NetworkEntityType.ZOMBIE_VILLAGER, "minecraft:zombie_villager");
		entityKey.put(NetworkEntityType.BLAZE, "minecraft:blaze");
		entityKey.put(NetworkEntityType.MAGMA_CUBE, "minecraft:magma_cube");
		entityKey.put(NetworkEntityType.GHAST, "minecraft:ghast");
		entityKey.put(NetworkEntityType.CAVE_SPIDER, "minecraft:cave_spider");
		entityKey.put(NetworkEntityType.SILVERFISH, "minecraft:silverfish");
		entityKey.put(NetworkEntityType.ENDERMAN, "minecraft:enderman");
		entityKey.put(NetworkEntityType.SLIME, "minecraft:slime");
		entityKey.put(NetworkEntityType.ZOMBIE_PIGMAN, "minecraft:zombie_pigman");
		entityKey.put(NetworkEntityType.SPIDER, "minecraft:spider");
		entityKey.put(NetworkEntityType.SKELETON, "minecraft:skeleton");
		entityKey.put(NetworkEntityType.CREEPER, "minecraft:creeper");
		entityKey.put(NetworkEntityType.ZOMBIE, "minecraft:zombie");
		entityKey.put(NetworkEntityType.SKELETON_HORSE, "minecraft:skeleton_horse");
		entityKey.put(NetworkEntityType.MULE, "minecraft:mule");
		entityKey.put(NetworkEntityType.DONKEY, "minecraft:donkey");
		entityKey.put(NetworkEntityType.DOLPHIN, "minecraft:dolphin");
		entityKey.put(NetworkEntityType.TROPICAL_FISH, "minecraft:tropicalfish");
		entityKey.put(NetworkEntityType.WOLF, "minecraft:wolf");
		entityKey.put(NetworkEntityType.SQUID, "minecraft:squid");
		entityKey.put(NetworkEntityType.DROWNED, "minecraft:drowned");
		entityKey.put(NetworkEntityType.SHEEP, "minecraft:sheep");
		entityKey.put(NetworkEntityType.MUSHROOM_COW, "minecraft:mooshroom");
		//entityKey.put(NetworkEntityType.PANDA, "minecraft:panda");
		entityKey.put(NetworkEntityType.SALMON, "minecraft:salmon");
		entityKey.put(NetworkEntityType.PIG, "minecraft:pig");
		entityKey.put(NetworkEntityType.VILLAGER, "minecraft:villager");
		entityKey.put(NetworkEntityType.COD, "minecraft:cod");
		entityKey.put(NetworkEntityType.PUFFERFISH, "minecraft:pufferfish");
		entityKey.put(NetworkEntityType.COW, "minecraft:cow");
		entityKey.put(NetworkEntityType.CHICKEN, "minecraft:chicken");
		//entityKey.put(NetworkEntityType.BALLOON, "minecraft:balloon");
		entityKey.put(NetworkEntityType.LAMA, "minecraft:llama");
		entityKey.put(NetworkEntityType.IRON_GOLEM, "minecraft:iron_golem");
		entityKey.put(NetworkEntityType.RABBIT, "minecraft:rabbit");
		//entityKey.put(NetworkEntityType.SNOW_GOLEM, "minecraft:snow_golem");
		entityKey.put(NetworkEntityType.BAT, "minecraft:bat");
		entityKey.put(NetworkEntityType.OCELOT, "minecraft:ocelot");
		entityKey.put(NetworkEntityType.COMMON_HORSE, "minecraft:horse");
		entityKey.put(NetworkEntityType.OCELOT, "minecraft:cat");
		entityKey.put(NetworkEntityType.POLAR_BEAR, "minecraft:polar_bear");
		entityKey.put(NetworkEntityType.ZOMBIE_HORSE, "minecraft:zombie_horse");
		entityKey.put(NetworkEntityType.TURTLE, "minecraft:turtle");
		entityKey.put(NetworkEntityType.PARROT, "minecraft:parrot");
		entityKey.put(NetworkEntityType.GUARDIAN, "minecraft:guardian");
		entityKey.put(NetworkEntityType.ELDER_GUARDIAN, "minecraft:elder_guardian");
		entityKey.put(NetworkEntityType.VINDICATOR, "minecraft:vindicator");
		entityKey.put(NetworkEntityType.WITHER, "minecraft:wither");
		entityKey.put(NetworkEntityType.ENDER_DRAGON, "minecraft:ender_dragon");
		entityKey.put(NetworkEntityType.SHULKER, "minecraft:shulker");
		entityKey.put(NetworkEntityType.ENDERMITE, "minecraft:endermite");
		entityKey.put(NetworkEntityType.MINECART, "minecraft:minecart");
		entityKey.put(NetworkEntityType.MINECART_HOPPER, "minecraft:hopper_minecart");
		entityKey.put(NetworkEntityType.MINECART_TNT, "minecraft:tnt_minecart");
		entityKey.put(NetworkEntityType.MINECART_CHEST, "minecraft:chest_minecart");
		entityKey.put(NetworkEntityType.MINECART_COMMAND, "minecraft:command_block_minecart");
		entityKey.put(NetworkEntityType.ARMOR_STAND_OBJECT, "minecraft:armor_stand");
		//entityKey.put(NetworkEntityType.ARMOR_STAND_MOB, "minecraft:armor_stand");
		entityKey.put(NetworkEntityType.ITEM, "minecraft:item");
		entityKey.put(NetworkEntityType.TNT, "minecraft:tnt");
		entityKey.put(NetworkEntityType.FALLING_OBJECT, "minecraft:falling_block");
		entityKey.put(NetworkEntityType.EXP_BOTTLE, "minecraft:xp_bottle");
		entityKey.put(NetworkEntityType.EXP_ORB, "minecraft:xp_orb");
		entityKey.put(NetworkEntityType.ENDEREYE, "minecraft:eye_of_ender_signal");
		entityKey.put(NetworkEntityType.ENDER_CRYSTAL, "minecraft:ender_crystal");
		entityKey.put(NetworkEntityType.SHULKER_BULLET, "minecraft:shulker_bullet");
		entityKey.put(NetworkEntityType.FISHING_FLOAT, "minecraft:fishing_hook");
		entityKey.put(NetworkEntityType.DRAGON_FIREBALL, "minecraft:dragon_fireball");
		entityKey.put(NetworkEntityType.ARROW, "minecraft:arrow");
		entityKey.put(NetworkEntityType.SNOWBALL, "minecraft:snowball");
		entityKey.put(NetworkEntityType.EGG, "minecraft:egg");
		entityKey.put(NetworkEntityType.PAINTING, "minecraft:painting");
		entityKey.put(NetworkEntityType.THROWN_TRIDENT, "minecraft:thrown_trident");
		entityKey.put(NetworkEntityType.FIREBALL, "minecraft:fireball");
		entityKey.put(NetworkEntityType.POTION, "minecraft:splash_potion");
		entityKey.put(NetworkEntityType.ENDERPEARL, "minecraft:ender_pearl");
		entityKey.put(NetworkEntityType.LEASH_KNOT, "minecraft:leash_knot");
		entityKey.put(NetworkEntityType.WITHER_SKULL, "minecraft:wither_skull");
		//entityKey.put(NetworkEntityType.WITHER_SKULL_DANGEROUS, "minecraft:wither_skull_dangerous");
		entityKey.put(NetworkEntityType.BOAT, "minecraft:boat");
		//entityKey.put(NetworkEntityType.LIGHTNING_BOLT, "minecraft:lightning_bolt");
		entityKey.put(NetworkEntityType.FIREBALL, "minecraft:small_fireball");
		entityKey.put(NetworkEntityType.LAMA_SPIT, "minecraft:llama_spit");
		entityKey.put(NetworkEntityType.AREA_EFFECT_CLOUD, "minecraft:area_effect_cloud");
		//entityKey.put(NetworkEntityType.LINGERING_POTION, "minecraft:lingering_potion");
		entityKey.put(NetworkEntityType.FIREWORK, "minecraft:fireworks_rocket");
		entityKey.put(NetworkEntityType.EVOCATOR_FANGS, "minecraft:evocation_fang");
		//entityKey.put(NetworkEntityType.EVOCATION_ILLAGER, "minecraft:evocation_illager");
		entityKey.put(NetworkEntityType.VEX, "minecraft:vex");
		//entityKey.put(NetworkEntityType.AGENT, "minecraft:agent");
		//entityKey.put(NetworkEntityType.ICE_BOMB, "minecraft:ice_bomb");
		entityKey.put(NetworkEntityType.PHANTOM, "minecraft:phantom");
		//entityKey.put(NetworkEntityType.TRIPOD_CAMERA, "minecraft:tripod_camera"

		for (Map.Entry<NetworkEntityType, Integer> entry : entityNetworkId.entrySet()) {
			entityNetworkIdToType.put((int) entry.getValue(), entry.getKey());
		}
	}

	public static NetworkEntityType getEntityTypeFromNetworkId(int networkId) {
		return entityNetworkIdToType.get(networkId);
	}

	public static int getEntityNetworkId(NetworkEntityType type) {
		Integer id = entityNetworkId.get(type);
		if (id == null) {
			System.err.println("Missing PE entity key for " + type);
			id = entityNetworkId.get(NetworkEntityType.ARMOR_STAND_MOB);
		}
		return id;
	}

	public static String getEntityKey(NetworkEntityType type) {
		String key = entityKey.get(type);
		if (key == null) {
			System.err.println("Missing PE entity key for " + type);
			key = "minecraft:armor_stand";
		}
		return key;
	}

	private static final Int2IntOpenHashMap pcEnchantToPe = new Int2IntOpenHashMap();
	private static final Int2IntOpenHashMap peEnchantToPc = new Int2IntOpenHashMap();

	private static void registerEnchantRemap(Enchantment enchantment, int peId) {
		pcEnchantToPe.put(LegacyEnchantmentId.getId(enchantment), peId);
		peEnchantToPc.put(peId, LegacyEnchantmentId.getId(enchantment));
	}

	static {
		registerEnchantRemap(Enchantment.OXYGEN, 6);
		registerEnchantRemap(Enchantment.WATER_WORKER, 8);
		registerEnchantRemap(Enchantment.THORNS, 5);
		registerEnchantRemap(Enchantment.DEPTH_STRIDER, 7);
		registerEnchantRemap(Enchantment.FROST_WALKER, 25);
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
		registerEnchantRemap(Enchantment.MENDING, 26);
	}

	public static int pcToPeEnchant(int pcId) {
		return pcEnchantToPe.get(pcId);
	}

	public static int peToPcEnchant(int peId) {
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
			registerRemapEntry(WindowType.CHEST, 0, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.CRAFTING_TABLE, 1, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.FURNACE, 2, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.ENCHANT, 3, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.BREWING, 4, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.ANVIL, 5, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.DISPENSER, 6, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.DROPPER, 7, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.HOPPER, 8, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.HORSE, 12, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.BEACON, 13, ProtocolVersionsHelper.ALL_PE);
			registerRemapEntry(WindowType.VILLAGER, 15, ProtocolVersionsHelper.ALL_PE);
		}

		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(14);
		}

		private void registerRemapEntry(WindowType type, int to, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				registerRemapEntry(type.toLegacyId(), to, version);
			}
		}
	};

	private static EnumMap<NetworkEntityType, Int2IntOpenHashMap> entityStatusRemaps = new EnumMap<>(NetworkEntityType.class);

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
		registerEntityStatusRemap(3, -1, NetworkEntityType.SNOWBALL); // SNOWBALL_POOF
		registerEntityStatusRemap(3, -1, NetworkEntityType.EGG); // EGG_ICONCRACK
		registerEntityStatusRemap(3, 3); // DEATH_ANIMATION
		registerEntityStatusRemap(6, 6); // TAME_FAIL
		registerEntityStatusRemap(7, 7); // TAME_SUCCESS
		registerEntityStatusRemap(8, 8); // SHAKE_WET
		registerEntityStatusRemap(9, 9); // USE_ITEM
		registerEntityStatusRemap(10, 10, NetworkEntityType.SHEEP); // EAT_GRASS_ANIMATION
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
		ENTITY_HURT_THORNS = 33;
		ENTITY_HURT_DROWN = 36;
		ENTITY_HURT_BURN = 37;

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
		EATING_ITEM = 57;
		BABY_ANIMAL_FEED = 60;
		DEATH_SMOKE_CLOUD = 61;
		COMPLETE_TRADE = 62;
		PLAYER_CHECK_TREASURE_HUNTER_ACHIEVEMENT = 66;
		ENTITY_SPAWN = 67;
		DRAGON_PUKE = 68;
		ITEM_ENTITY_MERGE = 69;
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

	private final static Map<NetworkEntityType, PEEntityData> entityData = new EnumMap<NetworkEntityType, PEEntityData>(NetworkEntityType.class);
	private final static Map<NetworkEntityType, PEEntityInventoryData> entityInventoryData = new EnumMap<NetworkEntityType, PEEntityInventoryData>(NetworkEntityType.class);

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
				System.out.println("Skipping inventory filter because NBT code still needs to be formatted: " + Filter);
				//TODO GET THIS???
				//filterNBT = new createNBTCompoundFromJson(Filter.replaceAll("\'", "\""));
			}

			public NBTCompound getFilter() {
				return filterNBT;
			}
		}
	}
}
