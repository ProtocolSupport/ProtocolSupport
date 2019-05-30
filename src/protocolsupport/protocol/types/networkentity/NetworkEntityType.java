package protocolsupport.protocol.types.networkentity;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupportbuildprocessor.Preload;

@SuppressWarnings("deprecation")
@Preload
public enum NetworkEntityType {

	NONE(EType.NONE),
	// Specials (Spawned by separate packets)
	EXP_ORB(EType.NONE),
	PAINTING(EType.NONE),
	PLAYER(EType.NONE),
	// Globals
	THUNDERBOLT(EType.GLOBAL, 1, EntityType.LIGHTNING),
	// Mobs
	COW(EType.MOB, EntityType.COW),
	MUSHROOM_COW(EType.MOB, EntityType.MUSHROOM_COW),
	CHICKEN(EType.MOB, EntityType.CHICKEN),
	SQUID(EType.MOB, EntityType.SQUID),
	COMMON_HORSE(EType.MOB, EntityType.HORSE),
	ZOMBIE_HORSE(EType.MOB, EntityType.ZOMBIE_HORSE),
	SKELETON_HORSE(EType.MOB, EntityType.SKELETON_HORSE),
	DONKEY(EType.MOB, EntityType.DONKEY),
	MULE(EType.MOB, EntityType.MULE),
	LAMA(EType.MOB, EntityType.LLAMA),
	BAT(EType.MOB, EntityType.BAT),
	OCELOT(EType.MOB, EntityType.OCELOT),
	WOLF(EType.MOB, EntityType.WOLF),
	PIG(EType.MOB, EntityType.PIG),
	RABBIT(EType.MOB, EntityType.RABBIT),
	SHEEP(EType.MOB, EntityType.SHEEP),
	POLAR_BEAR(EType.MOB, EntityType.POLAR_BEAR),
	VILLAGER(EType.MOB, EntityType.VILLAGER),
	ENDERMAN(EType.MOB, EntityType.ENDERMAN),
	GIANT(EType.MOB, EntityType.GIANT),
	SILVERFISH(EType.MOB, EntityType.SILVERFISH),
	ENDERMITE(EType.MOB, EntityType.ENDERMITE),
	ENDER_DRAGON(EType.MOB, EntityType.ENDER_DRAGON),
	SNOWMAN(EType.MOB, EntityType.SNOWMAN),
	ZOMBIE(EType.MOB, EntityType.ZOMBIE),
	ZOMBIE_VILLAGER(EType.MOB, EntityType.ZOMBIE_VILLAGER, ZOMBIE),
	HUSK(EType.MOB, EntityType.HUSK, ZOMBIE),
	ZOMBIE_PIGMAN(EType.MOB, EntityType.PIG_ZOMBIE, ZOMBIE),
	DROWNED(EType.MOB, EntityType.DROWNED, ZOMBIE),
	BLAZE(EType.MOB, EntityType.BLAZE),
	SPIDER(EType.MOB, EntityType.SPIDER),
	CAVE_SPIDER(EType.MOB, EntityType.CAVE_SPIDER, SPIDER),
	CREEPER(EType.MOB, EntityType.CREEPER),
	GHAST(EType.MOB, EntityType.GHAST),
	SLIME(EType.MOB, EntityType.SLIME),
	MAGMA_CUBE(EType.MOB, EntityType.MAGMA_CUBE, SLIME),
	SKELETON(EType.MOB, EntityType.SKELETON),
	WITHER_SKELETON(EType.MOB, EntityType.WITHER_SKELETON, SKELETON),
	STRAY(EType.MOB, EntityType.STRAY, SKELETON),
	WITCH(EType.MOB, EntityType.WITCH),
	IRON_GOLEM(EType.MOB, EntityType.IRON_GOLEM),
	SHULKER(EType.MOB, EntityType.SHULKER),
	WITHER(EType.MOB, EntityType.WITHER),
	GUARDIAN(EType.MOB, EntityType.GUARDIAN),
	ELDER_GUARDIAN(EType.MOB, EntityType.ELDER_GUARDIAN, GUARDIAN),
	VINDICATOR(EType.MOB, EntityType.VINDICATOR),
	EVOKER(EType.MOB, EntityType.EVOKER),
	ILLUSIONER(EType.MOB, EntityType.ILLUSIONER, EVOKER),
	VEX(EType.MOB, EntityType.VEX),
	PARROT(EType.MOB, EntityType.PARROT),
	PHANTOM(EType.MOB, EntityType.PHANTOM),
	DOLPHIN(EType.MOB, EntityType.DOLPHIN),
	TURTLE(EType.MOB, EntityType.TURTLE),
	COD(EType.MOB, EntityType.COD),
	SALMON(EType.MOB, EntityType.SALMON),
	PUFFERFISH(EType.MOB, EntityType.PUFFERFISH),
	TROPICAL_FISH(EType.MOB, EntityType.TROPICAL_FISH),
	TRADER_LAMA(EType.MOB, EntityType.TRADER_LLAMA, LAMA),
	WANDERING_TRADER(EType.MOB, EntityType.WANDERING_TRADER),
	CAT(EType.MOB, EntityType.CAT),
	FOX(EType.MOB, EntityType.FOX),
	PANDA(EType.MOB, EntityType.PANDA),
	PILLAGER(EType.MOB, EntityType.PILLAGER),
	RAVAGER(EType.MOB, EntityType.RAVAGER),
	ARMOR_STAND_MOB(EType.MOB, EntityType.ARMOR_STAND),
	// Objects
	BOAT(EType.OBJECT, EntityType.BOAT),
	TNT(EType.OBJECT, EntityType.PRIMED_TNT),
	SNOWBALL(EType.OBJECT, EntityType.SNOWBALL),
	EGG(EType.OBJECT, EntityType.EGG),
	FIREBALL(EType.OBJECT, EntityType.FIREBALL),
	FIRECHARGE(EType.OBJECT, EntityType.SMALL_FIREBALL),
	ENDERPEARL(EType.OBJECT, EntityType.ENDER_PEARL),
	WITHER_SKULL(EType.OBJECT, EntityType.WITHER_SKULL, FIREBALL),
	FALLING_OBJECT(EType.OBJECT, EntityType.FALLING_BLOCK),
	ENDEREYE(EType.OBJECT, EntityType.ENDER_SIGNAL),
	POTION(EType.OBJECT, EntityType.SPLASH_POTION),
	EXP_BOTTLE(EType.OBJECT, EntityType.THROWN_EXP_BOTTLE),
	LEASH_KNOT(EType.OBJECT, EntityType.LEASH_HITCH),
	FISHING_FLOAT(EType.OBJECT, EntityType.FISHING_HOOK),
	ITEM(EType.OBJECT, EntityType.DROPPED_ITEM),
	ARROW(EType.OBJECT, EntityType.ARROW),
	SPECTRAL_ARROW(EType.OBJECT, EntityType.SPECTRAL_ARROW, ARROW),
	TIPPED_ARROW(EType.OBJECT, EntityType.ARROW, ARROW),
	THROWN_TRIDENT(EType.OBJECT, EntityType.TRIDENT, ARROW),
	FIREWORK(EType.OBJECT, EntityType.FIREWORK),
	ITEM_FRAME(EType.OBJECT, EntityType.ITEM_FRAME),
	ENDER_CRYSTAL(EType.OBJECT, EntityType.ENDER_CRYSTAL),
	AREA_EFFECT_CLOUD(EType.OBJECT, EntityType.AREA_EFFECT_CLOUD),
	SHULKER_BULLET(EType.OBJECT, EntityType.SHULKER_BULLET),
	LAMA_SPIT(EType.OBJECT, EntityType.LLAMA_SPIT),
	DRAGON_FIREBALL(EType.OBJECT, EntityType.DRAGON_FIREBALL),
	EVOCATOR_FANGS(EType.OBJECT, EntityType.EVOKER_FANGS),
	ARMOR_STAND_OBJECT(EType.OBJECT, EntityType.ARMOR_STAND),
	MINECART(EType.OBJECT, EntityType.MINECART),
	MINECART_CHEST(EType.OBJECT, EntityType.MINECART_CHEST, MINECART),
	MINECART_FURNACE(EType.OBJECT, EntityType.MINECART_FURNACE, MINECART),
	MINECART_TNT(EType.OBJECT, EntityType.MINECART_TNT, MINECART),
	MINECART_MOB_SPAWNER(EType.OBJECT, EntityType.MINECART_MOB_SPAWNER, MINECART),
	MINECART_HOPPER(EType.OBJECT, EntityType.MINECART_HOPPER, MINECART),
	MINECART_COMMAND(EType.OBJECT, EntityType.MINECART_COMMAND, MINECART);

	private final EType etype;
	private final int typeId;
	private final EntityType bukkitType;
	private final NetworkEntityType superType;

	public boolean isReal() {
		return etype != EType.NONE;
	}

	public boolean isAlive() {
		return etype == EType.MOB || this == PLAYER;
	}

	public NetworkEntityType getSuperType() {
		return superType;
	}

	public int getNetworkTypeId() {
		return typeId;
	}

	public String getKey() {
		return bukkitType != null ? NamespacedKey.minecraft(bukkitType.getName()).toString() : "";
	}

	public EntityType getBukkitType() {
		return bukkitType;
	}

	public boolean isOfType(NetworkEntityType type) {
		return ((type == this) || ((getSuperType() != null) && getSuperType().isOfType(type)));
	}

	public enum EType {
		NONE, OBJECT, MOB, GLOBAL
	}

	protected static final ArrayMap<NetworkEntityType> OBJECT_BY_N_ID = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(NetworkEntityType.values()).filter(w -> w.etype == EType.OBJECT), (w -> w.typeId));
	protected static final ArrayMap<NetworkEntityType> MOB_BY_N_ID = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(NetworkEntityType.values()).filter(w -> w.etype == EType.MOB), (w -> w.typeId));
	protected static final ArrayMap<NetworkEntityType> GLOBAL_BY_N_ID = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(NetworkEntityType.values()).filter(w -> w.etype == EType.GLOBAL), (w -> w.typeId));
	protected static final Map<EntityType, NetworkEntityType> BY_B_TYPE = CollectionsUtils.makeEnumMappingEnumMap(Arrays.stream(NetworkEntityType.values()).filter(NetworkEntityType::isReal), EntityType.class, NetworkEntityType::getBukkitType);
	protected static final Map<String, NetworkEntityType> BY_R_STRING_ID = new HashMap<>();
	static {
		Arrays.stream(NetworkEntityType.values())
		.filter(NetworkEntityType::isReal)
		.forEach(w -> {
			String rName = w.bukkitType.getName();
			if (rName != null) {
				BY_R_STRING_ID.put(rName, w);
				BY_R_STRING_ID.put(NamespacedKey.minecraft(rName).toString(), w);
			}
		});
	}

	public static NetworkEntityType getObjectByNetworkTypeId(int objectTypeId) {
		NetworkEntityType type = OBJECT_BY_N_ID.get(objectTypeId);
		if (type == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown object entity network type id {0}", objectTypeId));
		}
		return type;
	}

	public static NetworkEntityType getMobByNetworkTypeId(int mobTypeId) {
		NetworkEntityType type = MOB_BY_N_ID.get(mobTypeId);
		if (type == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown mob entity network type id {0}", mobTypeId));
		}
		return type;
	}

	public static NetworkEntityType getGlobalByNetworkTypeId(int globalTypeId) {
		NetworkEntityType type = GLOBAL_BY_N_ID.get(globalTypeId);
		if (type == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown global entity network type id {0}", globalTypeId));
		}
		return type;
	}

	public static NetworkEntityType getByRegistrySTypeId(String name) {
		return BY_R_STRING_ID.getOrDefault(name, NONE);
	}

	public static NetworkEntityType getByBukkitType(EntityType btype) {
		return BY_B_TYPE.getOrDefault(btype, NONE);
	}

	NetworkEntityType(EType etype, EntityType bukkitType, NetworkEntityType superType) {
		this.etype = etype;
		this.bukkitType = bukkitType;
		this.typeId = bukkitType != null ? ServerPlatform.get().getMiscUtils().getEntityTypeNetworkId(bukkitType) : -1;
		this.superType = superType;
	}

	NetworkEntityType(EType etype, int typeId, EntityType bukkitType) {
		this.etype = etype;
		this.bukkitType = bukkitType;
		this.typeId = typeId;
		this.superType = null;
	}

	NetworkEntityType(EType etype, EntityType bukkitType) {
		this(etype, bukkitType, null);
	}

	NetworkEntityType(EType etype) {
		this(etype, null, null);
	}

}
