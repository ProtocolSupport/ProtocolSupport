package protocolsupport.protocol.typeremapper.pe;

import java.io.BufferedReader;
import java.util.EnumMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.legacy.LegacyEnchantmentId;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.HashMapBasedIdRemappingTable;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.WindowType;
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

	private static final EnumMap<NetworkEntityType, Integer> entityType = new EnumMap<>(NetworkEntityType.class);
	private static final Int2ObjectOpenHashMap<NetworkEntityType> livingTypeFromNetwork = new Int2ObjectOpenHashMap<>();
	private static void registerLivingType(NetworkEntityType type, int networkId) {
		entityType.put(type, networkId);
		livingTypeFromNetwork.put(networkId, type);
	}
	static {
		registerLivingType(NetworkEntityType.WITHER_SKELETON, 48);
		registerLivingType(NetworkEntityType.WOLF, 14);
		registerLivingType(NetworkEntityType.RABBIT, 18);
		registerLivingType(NetworkEntityType.CHICKEN, 10);
		registerLivingType(NetworkEntityType.COW, 11);
		registerLivingType(NetworkEntityType.SHEEP, 13);
		registerLivingType(NetworkEntityType.PIG, 12);
		registerLivingType(NetworkEntityType.MUSHROOM_COW, 16);
		registerLivingType(NetworkEntityType.SHULKER, 54);
		registerLivingType(NetworkEntityType.GUARDIAN, 49);
		registerLivingType(NetworkEntityType.ENDERMITE, 55);
		registerLivingType(NetworkEntityType.WITCH, 45);
		registerLivingType(NetworkEntityType.BAT, 19);
		registerLivingType(NetworkEntityType.WITHER, 52);
		registerLivingType(NetworkEntityType.ENDER_DRAGON, 53);
		registerLivingType(NetworkEntityType.MAGMA_CUBE, 42);
		registerLivingType(NetworkEntityType.BLAZE, 43);
		registerLivingType(NetworkEntityType.SILVERFISH, 39);
		registerLivingType(NetworkEntityType.CAVE_SPIDER, 40);
		registerLivingType(NetworkEntityType.ENDERMAN, 38);
		registerLivingType(NetworkEntityType.ZOMBIE_PIGMAN, 36);
		registerLivingType(NetworkEntityType.GHAST, 41);
		registerLivingType(NetworkEntityType.SLIME, 37);
		registerLivingType(NetworkEntityType.ZOMBIE, 32);
		registerLivingType(NetworkEntityType.SPIDER, 35);
		registerLivingType(NetworkEntityType.SKELETON, 34);
		registerLivingType(NetworkEntityType.CREEPER, 33);
		registerLivingType(NetworkEntityType.VILLAGER, 15);
		registerLivingType(NetworkEntityType.MULE, 25);
		registerLivingType(NetworkEntityType.DONKEY, 24);
		registerLivingType(NetworkEntityType.ZOMBIE_HORSE, 27);
		registerLivingType(NetworkEntityType.SKELETON_HORSE, 26);
		registerLivingType(NetworkEntityType.ZOMBIE_VILLAGER, 44);
		registerLivingType(NetworkEntityType.HUSK, 47);
		registerLivingType(NetworkEntityType.SQUID, 17);
		registerLivingType(NetworkEntityType.STRAY, 46);
		registerLivingType(NetworkEntityType.POLAR_BEAR, 28);
		registerLivingType(NetworkEntityType.ELDER_GUARDIAN, 50);
		registerLivingType(NetworkEntityType.COMMON_HORSE, 23);
		registerLivingType(NetworkEntityType.IRON_GOLEM, 20);
		registerLivingType(NetworkEntityType.OCELOT, 22);
		registerLivingType(NetworkEntityType.SNOWMAN, 21);
		registerLivingType(NetworkEntityType.LAMA, 29);
		registerLivingType(NetworkEntityType.PARROT, 30);
		registerLivingType(NetworkEntityType.ARMOR_STAND_MOB, 61);
		registerLivingType(NetworkEntityType.VINDICATOR, 57);
		registerLivingType(NetworkEntityType.EVOKER, 104);
		registerLivingType(NetworkEntityType.VEX, 105);
		registerLivingType(NetworkEntityType.DOLPHIN, 31);
		registerLivingType(NetworkEntityType.PUFFERFISH, 108);
		registerLivingType(NetworkEntityType.SALMON, 109);
		registerLivingType(NetworkEntityType.TROPICAL_FISH, 111);
		registerLivingType(NetworkEntityType.COD, 112);
		registerLivingType(NetworkEntityType.DROWNED, 110);
		registerLivingType(NetworkEntityType.TURTLE, 74);
		registerLivingType(NetworkEntityType.PHANTOM, 58);

		entityType.put(NetworkEntityType.ARMOR_STAND_OBJECT, 61);
		entityType.put(NetworkEntityType.TNT, 65);
		entityType.put(NetworkEntityType.FALLING_OBJECT, 66);
		//TODO: Fix pistons, moving blocks? -> 67
		entityType.put(NetworkEntityType.EXP_BOTTLE, 68);
		entityType.put(NetworkEntityType.EXP_ORB, 69);
		entityType.put(NetworkEntityType.ENDEREYE, 70);
		entityType.put(NetworkEntityType.ENDER_CRYSTAL, 71);
		entityType.put(NetworkEntityType.FIREWORK, 72);
		entityType.put(NetworkEntityType.THROWN_TRIDENT, 73);
		entityType.put(NetworkEntityType.SHULKER_BULLET, 76);
		entityType.put(NetworkEntityType.FISHING_FLOAT, 77);
		entityType.put(NetworkEntityType.DRAGON_FIREBALL, 79);
		entityType.put(NetworkEntityType.ARROW, 80);
		entityType.put(NetworkEntityType.SNOWBALL, 81);
		entityType.put(NetworkEntityType.EGG, 82);
		entityType.put(NetworkEntityType.MINECART, 84);
		entityType.put(NetworkEntityType.FIREBALL, 85);
		entityType.put(NetworkEntityType.POTION, 86);
		entityType.put(NetworkEntityType.ENDERPEARL, 87);
		entityType.put(NetworkEntityType.LEASH_KNOT, 88);
		entityType.put(NetworkEntityType.WITHER_SKULL, 89);
		entityType.put(NetworkEntityType.BOAT, 90);
		//TODO: WitherSkull dangerous? -> 91
		entityType.put(NetworkEntityType.FIRECHARGE, 94);
		entityType.put(NetworkEntityType.AREA_EFFECT_CLOUD, 95);
		entityType.put(NetworkEntityType.MINECART_HOPPER, 96);
		entityType.put(NetworkEntityType.MINECART_TNT, 97);
		entityType.put(NetworkEntityType.MINECART_CHEST, 98);
		entityType.put(NetworkEntityType.MINECART_COMMAND, 100);
		//TODO: Lingering Potion? -> 101
		entityType.put(NetworkEntityType.LAMA_SPIT, 102);
		entityType.put(NetworkEntityType.EVOCATOR_FANGS, 103);
	}

	public static int getEntityTypeId(NetworkEntityType type) {
		return entityType.get(type);
	}

	public static NetworkEntityType getLivingTypeFromPeNetworkId(int networkId) {
		return livingTypeFromNetwork.get(networkId);
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
			//TODO: Use particle enum
			registerRemapEntry(0, PELevelEvent.PARTICLE_EXPLODE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(1, PELevelEvent.PARTICLE_HUGE_EXPLOSION, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(2, PELevelEvent.PARTICLE_HUGE_EXPLOSION_SEED, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(4, PELevelEvent.PARTICLE_BUBBLE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(5, PELevelEvent.PARTICLE_SPLASH, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(6, PELevelEvent.PARTICLE_WATER_WAKE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(9, PELevelEvent.PARTICLE_CRITICAL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(10, PELevelEvent.PARTICLE_CRITICAL, ProtocolVersion.MINECRAFT_PE); //Magiccrit..?
			registerRemapEntry(11, PELevelEvent.PARTICLE_SMOKE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(12, PELevelEvent.PARTICLE_LARGE_SMOKE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(13, PELevelEvent.PARTICLE_MOB_SPELL, ProtocolVersion.MINECRAFT_PE); //Speculative
			registerRemapEntry(14, PELevelEvent.PARTICLE_MOB_SPELL_INSTANTANIOUS, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(15, PELevelEvent.PARTICLE_MOB_SPELL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(16, PELevelEvent.PARTICLE_MOB_SPELL_INSTANTANIOUS, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(17, PELevelEvent.PARTICLE_WITCH_SPELL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(18, PELevelEvent.PARTICLE_DRIP_WATER, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(19, PELevelEvent.PARTICLE_DRIP_LAVA, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(20, PELevelEvent.PARTICLE_VILLAGER_ANGRY, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(21, PELevelEvent.PARTICLE_VILLAGER_HAPPY, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(22, PELevelEvent.PARTICLE_TOWN_AURA, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(23, PELevelEvent.PARTICLE_NOTE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(24, PELevelEvent.PARTICLE_PORTAL, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(25, PELevelEvent.PARTICLE_ENCHANTMENT_TABLE, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(26, PELevelEvent.PARTICLE_FLAME, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(27, PELevelEvent.PARTICLE_LAVA, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(30, PELevelEvent.PARTICLE_RISING_RED_DUST, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(31, PELevelEvent.PARTICLE_SNOWBALL_POOF, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(33, PELevelEvent.PARTICLE_SLIME, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(34, PELevelEvent.PARTICLE_HEART, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(35, PELevelEvent.PARTICLE_BLOCK_FORCE_FIELD, ProtocolVersion.MINECRAFT_PE); //Speculative
			registerRemapEntry(40, PELevelEvent.CAULDRON_TAKE_WATER, ProtocolVersion.MINECRAFT_PE); //Speculative
			registerRemapEntry(42, PELevelEvent.PARTICLE_DRAGONS_BREATH, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(43, PELevelEvent.PARTICLE_END_ROT, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(46, PELevelEvent.PARTICLE_FALLING_DUST, ProtocolVersion.MINECRAFT_PE);
		}
		@Override
		protected HashMapBasedIdRemappingTable createTable() {
			return new HashMapBasedIdRemappingTable();
		}
	};

	public static final IdRemappingRegistry<ArrayBasedIdRemappingTable> WINDOWTYPE = new IdRemappingRegistry<ArrayBasedIdRemappingTable>() {
		{
			registerRemapEntry(WindowType.PLAYER, -1, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.CHEST, 0, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.CRAFTING_TABLE, 1, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.FURNACE, 2, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.ENCHANT, 3, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.BREWING, 4, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.ANVIL, 5, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.DISPENSER, 6, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.DROPPER, 7, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.HOPPER, 8, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.HORSE, 12, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.BEACON, 13, ProtocolVersion.MINECRAFT_PE);
			registerRemapEntry(WindowType.VILLAGER, 15, ProtocolVersion.MINECRAFT_PE);
		}
		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(14);
		}

		private void registerRemapEntry(WindowType type, int to, ProtocolVersion version) {
			registerRemapEntry(type.toLegacyId(), to, version);
		}
	};


	private final static Map<NetworkEntityType, PEEntityData> entityData = new EnumMap<NetworkEntityType, PEEntityData>(NetworkEntityType.class);
	static {
		getFileObject("entitydata.json").entrySet().forEach(entry -> {
			entityData.put(NetworkEntityType.valueOf(entry.getKey()), Utils.GSON.fromJson(entry.getValue(), PEEntityData.class));
		});
	}

	public static PEEntityData getEntityData(NetworkEntityType type) {
		return entityData.get(type);
	}

	public static class PEEntityData {
		private BoundingBox boundingBox;
		private Offset offset;
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

}
