package protocolsupport.protocol.utils.networkentity;

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

	NONE(EType.NONE, -1),
	ENTITY(EType.NONE, -1),
	LIVING(EType.NONE, -1, ENTITY),
	INSENTIENT(EType.NONE, -1, LIVING),
	PLAYER(EType.NONE, -1, LIVING),
	AGEABLE(EType.NONE, -1, INSENTIENT),
	TAMEABLE(EType.NONE, -1, AGEABLE),
	BASE_FISH(EType.NONE, -1, INSENTIENT),
	// Specials (Spawned by separate packets)
	EXP_ORB(EType.NONE, -1),
	PAINTING(EType.NONE, -1),
	// Globals
	THUNDERBOLT(EType.GLOBAL, 1, EntityType.LIGHTNING, ENTITY),
	// Mobs
	COW(EType.MOB, EntityType.COW, NetworkEntityType.AGEABLE),
	MUSHROOM_COW(EType.MOB, EntityType.MUSHROOM_COW, NetworkEntityType.COW),
	CHICKEN(EType.MOB, EntityType.CHICKEN, NetworkEntityType.AGEABLE),
	SQUID(EType.MOB, EntityType.SQUID, NetworkEntityType.INSENTIENT),
	COMMON_HORSE(EType.MOB, EntityType.HORSE, AGEABLE),
	ZOMBIE_HORSE(EType.MOB, EntityType.ZOMBIE_HORSE, AGEABLE),
	SKELETON_HORSE(EType.MOB, EntityType.SKELETON_HORSE, AGEABLE),
	DONKEY(EType.MOB, EntityType.DONKEY, AGEABLE),
	MULE(EType.MOB, EntityType.MULE, AGEABLE),
	LAMA(EType.MOB, EntityType.LLAMA, AGEABLE),
	BAT(EType.MOB, EntityType.BAT, INSENTIENT),
	OCELOT(EType.MOB, EntityType.OCELOT, TAMEABLE),
	WOLF(EType.MOB, EntityType.WOLF, TAMEABLE),
	PIG(EType.MOB, EntityType.PIG, AGEABLE),
	RABBIT(EType.MOB, EntityType.RABBIT, AGEABLE),
	SHEEP(EType.MOB, EntityType.SHEEP, AGEABLE),
	POLAR_BEAR(EType.MOB, EntityType.POLAR_BEAR, AGEABLE),
	VILLAGER(EType.MOB, EntityType.VILLAGER, AGEABLE),
	ENDERMAN(EType.MOB, EntityType.ENDERMAN, INSENTIENT),
	GIANT(EType.MOB, EntityType.GIANT, INSENTIENT),
	SILVERFISH(EType.MOB, EntityType.SILVERFISH, INSENTIENT),
	ENDERMITE(EType.MOB, EntityType.ENDERMITE, INSENTIENT),
	ENDER_DRAGON(EType.MOB, EntityType.ENDER_DRAGON, INSENTIENT),
	SNOWMAN(EType.MOB, EntityType.SNOWMAN, INSENTIENT),
	ZOMBIE(EType.MOB, EntityType.ZOMBIE, INSENTIENT),
	ZOMBIE_VILLAGER(EType.MOB, EntityType.ZOMBIE_VILLAGER, ZOMBIE),
	HUSK(EType.MOB, EntityType.HUSK, ZOMBIE),
	ZOMBIE_PIGMAN(EType.MOB, EntityType.PIG_ZOMBIE, ZOMBIE),
	DROWNED(EType.MOB, EntityType.DROWNED, ZOMBIE),
	BLAZE(EType.MOB, EntityType.BLAZE, INSENTIENT),
	SPIDER(EType.MOB, EntityType.SPIDER, LIVING),
	CAVE_SPIDER(EType.MOB, EntityType.CAVE_SPIDER, SPIDER),
	CREEPER(EType.MOB, EntityType.CREEPER, INSENTIENT),
	GHAST(EType.MOB, EntityType.GHAST, INSENTIENT),
	SLIME(EType.MOB, EntityType.SLIME, INSENTIENT),
	MAGMA_CUBE(EType.MOB, EntityType.MAGMA_CUBE, SLIME),
	SKELETON(EType.MOB, EntityType.SKELETON, INSENTIENT),
	WITHER_SKELETON(EType.MOB, EntityType.WITHER_SKELETON, SKELETON),
	STRAY(EType.MOB, EntityType.STRAY, SKELETON),
	WITCH(EType.MOB, EntityType.WITCH, INSENTIENT),
	IRON_GOLEM(EType.MOB, EntityType.IRON_GOLEM, INSENTIENT),
	SHULKER(EType.MOB, EntityType.SHULKER, INSENTIENT),
	WITHER(EType.MOB, EntityType.WITHER, INSENTIENT),
	GUARDIAN(EType.MOB, EntityType.GUARDIAN, INSENTIENT),
	ELDER_GUARDIAN(EType.MOB, EntityType.ELDER_GUARDIAN, GUARDIAN),
	VINDICATOR(EType.MOB, EntityType.VINDICATOR, INSENTIENT),
	EVOKER(EType.MOB, EntityType.EVOKER, INSENTIENT),
	ILLUSIONER(EType.MOB, EntityType.ILLUSIONER, EVOKER),
	VEX(EType.MOB, EntityType.VEX, INSENTIENT),
	PARROT(EType.MOB, EntityType.PARROT, TAMEABLE),
	PHANTOM(EType.MOB, EntityType.PHANTOM, INSENTIENT),
	DOLPHIN(EType.MOB, EntityType.DOLPHIN, INSENTIENT),
	TURTLE(EType.MOB, EntityType.TURTLE, AGEABLE),
	COD(EType.MOB, EntityType.COD, BASE_FISH),
	SALMON(EType.MOB, EntityType.SALMON, BASE_FISH),
	PUFFERFISH(EType.MOB, EntityType.PUFFERFISH, BASE_FISH),
	TROPICAL_FISH(EType.MOB, EntityType.TROPICAL_FISH, BASE_FISH),
	ARMOR_STAND_MOB(EType.MOB, EntityType.ARMOR_STAND, LIVING),
	// Objects
	BOAT(EType.OBJECT, 1, EntityType.BOAT, ENTITY),
	TNT(EType.OBJECT, 50, EntityType.PRIMED_TNT, ENTITY),
	SNOWBALL(EType.OBJECT, 61, EntityType.SNOWBALL, ENTITY),
	EGG(EType.OBJECT, 62, EntityType.EGG, ENTITY),
	FIREBALL(EType.OBJECT, 63, EntityType.FIREBALL, ENTITY),
	FIRECHARGE(EType.OBJECT, 64, EntityType.SMALL_FIREBALL, ENTITY),
	ENDERPEARL(EType.OBJECT, 65, EntityType.ENDER_PEARL, ENTITY),
	WITHER_SKULL(EType.OBJECT, 66, EntityType.WITHER_SKULL, FIREBALL),
	FALLING_OBJECT(EType.OBJECT, 70, EntityType.FALLING_BLOCK, ENTITY),
	ENDEREYE(EType.OBJECT, 72, EntityType.ENDER_SIGNAL, ENTITY),
	POTION(EType.OBJECT, 73, EntityType.SPLASH_POTION, ENTITY),
	EXP_BOTTLE(EType.OBJECT, 75, EntityType.THROWN_EXP_BOTTLE, ENTITY),
	LEASH_KNOT(EType.OBJECT, 77, EntityType.LEASH_HITCH, ENTITY),
	FISHING_FLOAT(EType.OBJECT, 90, EntityType.FISHING_HOOK, ENTITY),
	ITEM(EType.OBJECT, 2, EntityType.DROPPED_ITEM, ENTITY),
	ARROW(EType.OBJECT, 60, EntityType.ARROW, ENTITY),
	SPECTRAL_ARROW(EType.OBJECT, 91, EntityType.SPECTRAL_ARROW, ARROW),
	TIPPED_ARROW(EType.OBJECT, 92, EntityType.TIPPED_ARROW, ARROW),
	THROWN_TRIDENT(EType.OBJECT, 94, EntityType.TRIDENT, ARROW),
	FIREWORK(EType.OBJECT, 76, EntityType.FIREWORK, ENTITY),
	ITEM_FRAME(EType.OBJECT, 71, EntityType.ITEM_FRAME, ENTITY),
	ENDER_CRYSTAL(EType.OBJECT, 51, EntityType.ENDER_CRYSTAL, ENTITY),
	AREA_EFFECT_CLOUD(EType.OBJECT, 3, EntityType.AREA_EFFECT_CLOUD, ENTITY),
	SHULKER_BULLET(EType.OBJECT, 67, EntityType.SHULKER_BULLET, ENTITY),
	LAMA_SPIT(EType.OBJECT, 68, EntityType.LLAMA_SPIT, ENTITY),
	DRAGON_FIREBALL(EType.OBJECT, 93, EntityType.DRAGON_FIREBALL, ENTITY),
	EVOCATOR_FANGS(EType.OBJECT, 79, EntityType.EVOKER_FANGS, ENTITY),
	ARMOR_STAND_OBJECT(EType.OBJECT, 78, EntityType.ARMOR_STAND, LIVING),
	// Hack, use unused object ids, minecart is the only object where different types are sent using objectdata.
	MINECART(EType.OBJECT, 10, EntityType.MINECART, ENTITY),
	MINECART_CHEST(EType.OBJECT, 211, EntityType.MINECART_CHEST, MINECART),
	MINECART_FURNACE(EType.OBJECT, 212, EntityType.MINECART_FURNACE, MINECART),
	MINECART_TNT(EType.OBJECT, 213, EntityType.MINECART_TNT, MINECART),
	MINECART_MOB_SPAWNER(EType.OBJECT, 214, EntityType.MINECART_MOB_SPAWNER, MINECART),
	MINECART_HOPPER(EType.OBJECT, 215, EntityType.MINECART_HOPPER, MINECART),
	MINECART_COMMAND(EType.OBJECT, 216, EntityType.MINECART_COMMAND, MINECART);

	private final EType etype;
	private final int typeId;
	private final EntityType bukkitType;
	private final NetworkEntityType superType;

	public boolean isReal() {
		return etype != EType.NONE;
	}

	public NetworkEntityType getSuperType() {
		return superType;
	}

	public int getNetworkTypeId() {
		if (isOfType(MINECART)) {
			return MINECART.typeId;
		}
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

	public static NetworkEntityType getObjectByNetworkTypeIdAndData(int objectTypeId, int objectData) {
		NetworkEntityType w = getObjectByNetworkTypeId(objectTypeId);
		if (w.isOfType(MINECART)) {
			return getMinecartByData(objectData);
		}
		return w;
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

	public static NetworkEntityType getMinecartByData(int objectData) {
		if (objectData == 0) {
			return MINECART;
		}
		return getObjectByNetworkTypeId(210 + objectData);
	}

	public static NetworkEntityType getByRegistrySTypeId(String name) {
		return BY_R_STRING_ID.getOrDefault(name, NONE);
	}

	public static NetworkEntityType getByBukkitType(EntityType btype) {
		return BY_B_TYPE.getOrDefault(btype, NONE);
	}

	NetworkEntityType(EType etype, int typeId, EntityType bukkitType, NetworkEntityType superType) {
		this.etype = etype;
		this.typeId = typeId;
		this.bukkitType = bukkitType;
		this.superType = superType;
	}

	NetworkEntityType(EType etype, EntityType bukkitType, NetworkEntityType superType) {
		this.etype = etype;
		this.bukkitType = bukkitType;
		this.typeId = ServerPlatform.get().getMiscUtils().getMobTypeNetworkId(bukkitType);
		this.superType = superType;
	}

	NetworkEntityType(EType etype, int typeId, NetworkEntityType superType) {
		this(etype, typeId, null, superType);
	}

	NetworkEntityType(EType etype, int typeId) {
		this(etype, typeId, null);
	}

}
