package protocolsupport.protocol.utils.types;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.entity.EntityType;

import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

@SuppressWarnings("deprecation")
public enum NetworkEntityType {

	NONE(EType.NONE, -1),
	ENTITY(EType.NONE, -1),
	LIVING(EType.NONE, -1, NetworkEntityType.ENTITY),
	INSENTIENT(EType.NONE, -1, NetworkEntityType.LIVING),
	PLAYER(EType.NONE, -1, NetworkEntityType.LIVING),
	AGEABLE(EType.NONE, -1, NetworkEntityType.INSENTIENT),
	TAMEABLE(EType.NONE, -1, NetworkEntityType.AGEABLE),
	ARMOR_STAND(EType.NONE, -1, NetworkEntityType.LIVING),
	COW(EType.MOB, EntityType.COW, NetworkEntityType.AGEABLE),
	MUSHROOM_COW(EType.MOB, EntityType.MUSHROOM_COW, NetworkEntityType.COW),
	CHICKEN(EType.MOB, EntityType.CHICKEN, NetworkEntityType.AGEABLE),
	SQUID(EType.MOB, EntityType.SQUID, NetworkEntityType.INSENTIENT),
	BASE_HORSE(EType.NONE, -1, NetworkEntityType.AGEABLE),
	BATTLE_HORSE(EType.NONE, -1, BASE_HORSE),
	CARGO_HORSE(EType.NONE, -1, BASE_HORSE),
	BASE_SKELETON(EType.NONE, -1, INSENTIENT),
	EXP_ORB(EType.NONE, -1),
	// Mobs (Network and game values are the same)
	COMMON_HORSE(EType.MOB, EntityType.HORSE, BATTLE_HORSE),
	ZOMBIE_HORSE(EType.MOB, EntityType.ZOMBIE_HORSE, BATTLE_HORSE),
	SKELETON_HORSE(EType.MOB, EntityType.SKELETON_HORSE, BATTLE_HORSE),
	DONKEY(EType.MOB, EntityType.DONKEY, CARGO_HORSE),
	MULE(EType.MOB, EntityType.MULE, CARGO_HORSE),
	LAMA(EType.MOB, EntityType.LLAMA, CARGO_HORSE),
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
	BLAZE(EType.MOB, EntityType.BLAZE, INSENTIENT),
	SPIDER(EType.MOB, EntityType.SPIDER, LIVING),
	CAVE_SPIDER(EType.MOB, EntityType.CAVE_SPIDER, SPIDER),
	CREEPER(EType.MOB, EntityType.CREEPER, INSENTIENT),
	GHAST(EType.MOB, EntityType.GHAST, INSENTIENT),
	SLIME(EType.MOB, EntityType.SLIME, INSENTIENT),
	MAGMA_CUBE(EType.MOB, EntityType.MAGMA_CUBE, SLIME),
	SKELETON(EType.MOB, EntityType.SKELETON, BASE_SKELETON),
	WITHER_SKELETON(EType.MOB, EntityType.WITHER_SKELETON, BASE_SKELETON),
	STRAY(EType.MOB, EntityType.STRAY, BASE_SKELETON),
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
	ARMOR_STAND_MOB(EType.MOB, EntityType.ARMOR_STAND, ARMOR_STAND),
	// Objects (Different networking values)
	BOAT(EType.OBJECT, 1, EntityType.BOAT, ENTITY),
	TNT(EType.OBJECT, 50, EntityType.PRIMED_TNT, ENTITY),
	SNOWBALL(EType.OBJECT, 61, EntityType.SNOWBALL, ENTITY),
	EGG(EType.OBJECT, 62, EntityType.EGG, ENTITY),
	FIREBALL(EType.OBJECT, 63, EntityType.FIREBALL, ENTITY),
	FIRECHARGE(EType.OBJECT, 64, EntityType.SMALL_FIREBALL, ENTITY),
	ENDERPEARL(EType.OBJECT, 65, EntityType.ENDER_PEARL, ENTITY),
	WITHER_SKULL(EType.OBJECT, 66, EntityType.WITHER_SKULL ,FIREBALL),
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
	FIREWORK(EType.OBJECT, 76, EntityType.FIREWORK, ENTITY),
	ITEM_FRAME(EType.OBJECT, 71, EntityType.ITEM_FRAME, ENTITY),
	ENDER_CRYSTAL(EType.OBJECT, 51, EntityType.ENDER_CRYSTAL, ENTITY),
	ARMOR_STAND_OBJECT(EType.OBJECT, 78, EntityType.ARMOR_STAND, ARMOR_STAND),
	AREA_EFFECT_CLOUD(EType.OBJECT, 3, EntityType.AREA_EFFECT_CLOUD, ENTITY),
	SHULKER_BULLET(EType.OBJECT, 67, EntityType.SHULKER_BULLET, ENTITY),
	LAMA_SPIT(EType.OBJECT, 68, EntityType.LLAMA_SPIT, ENTITY),
	DRAGON_FIREBALL(EType.OBJECT, 93, EntityType.DRAGON_FIREBALL, ENTITY),
	EVOCATOR_FANGS(EType.OBJECT, 79, EntityType.EVOKER_FANGS, ENTITY),
	MINECART(EType.OBJECT, 10,  EntityType.MINECART, ENTITY),
	// Hack, using unsused ids; the only object where different types are send using objectData.
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

	public NetworkEntityType getSuperType() {
		return superType;
	}

	public int getNetworkTypeId() {
		if (isOfType(MINECART)) {
			return MINECART.typeId;
		}
		return typeId;
	}

	public EntityType getBukkitType() {
		return bukkitType;
	}

	public String getRegistrySTypeId() {
		return bukkitType != null ? MinecraftData.addNamespacePrefix(bukkitType.getName()) : "";
	}

	public int getRegistryITypeId() {
		return bukkitType != null ? bukkitType.getTypeId() : 0;
	}

	public boolean isOfType(NetworkEntityType type) {
		return ((type == this) || ((getSuperType() != null) && getSuperType().isOfType(type)));
	}

	public enum EType {
		NONE, OBJECT, MOB
	}

	private static final ArrayMap<NetworkEntityType> OBJECT_BY_N_ID = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(NetworkEntityType.values()).filter(w -> w.etype == EType.OBJECT), (w -> w.typeId));
	private static final ArrayMap<NetworkEntityType> MOB_BY_N_ID = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(NetworkEntityType.values()).filter(w -> w.etype == EType.MOB), (w -> w.typeId));
	private static final ArrayMap<NetworkEntityType> BY_R_INT_ID = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(NetworkEntityType.values()), (w -> w.bukkitType.getTypeId()));
	private static final HashMap<String, NetworkEntityType> BY_R_STRING_ID = new HashMap<>();
	static {
		Arrays.stream(NetworkEntityType.values()).forEach(w -> {
			BY_R_STRING_ID.put(w.bukkitType.getName(), w);
			BY_R_STRING_ID.put(MinecraftData.addNamespacePrefix(w.bukkitType.getName()), w);
		});
	}

	public static NetworkEntityType getObjectByNetworkTypeId(int objectTypeId) {
		NetworkEntityType type = OBJECT_BY_N_ID.get(objectTypeId);
		if (type == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown object network type id {0}", objectTypeId));
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
			throw new IllegalArgumentException(MessageFormat.format("Unknown mob network type id {0}", mobTypeId));
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

	public static NetworkEntityType getByRegistryITypeId(int typeId) {
		NetworkEntityType type = BY_R_INT_ID.get(typeId);
		return type != null ? type : NONE;
	}

	NetworkEntityType(EType etype, int typeId, EntityType bukkitType, NetworkEntityType superType) {
		this.etype = etype;
		this.typeId = typeId;
		this.bukkitType = bukkitType;
		this.superType = superType;
	}

	NetworkEntityType(EType etype, int typeId, NetworkEntityType superType) {
		this(etype, typeId, EntityType.UNKNOWN, superType);
	}

	NetworkEntityType(EType etype, EntityType bukkitType, NetworkEntityType superType) {
		this(etype, bukkitType.getTypeId(), bukkitType, superType);
	}

	NetworkEntityType(EType etype, int typeId) {
		this(etype, typeId, null);
	}

	NetworkEntityType(EType etype, EntityType bukkitType) {
		this(etype, bukkitType, null);
	}

}
