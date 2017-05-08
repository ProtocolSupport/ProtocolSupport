package protocolsupport.protocol.typeremapper.watchedentity;

import java.text.MessageFormat;
import java.util.Arrays;

import org.bukkit.entity.EntityType;

import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

/***
 * All the types network entities can be.
 */
public enum WatchedType {

	NONE(EType.NONE, -1),
	ENTITY(EType.NONE, -1),
	LIVING(EType.NONE, -1, WatchedType.ENTITY),
	INSENTIENT(EType.NONE, -1, WatchedType.LIVING),
	PLAYER(EType.NONE, -1, WatchedType.LIVING),
	AGEABLE(EType.NONE, -1, WatchedType.INSENTIENT),
	TAMEABLE(EType.NONE, -1, WatchedType.AGEABLE),
	ARMOR_STAND(EType.NONE, -1, WatchedType.LIVING),
	COW(EType.MOB, EntityType.COW, WatchedType.AGEABLE),
	MUSHROOM_COW(EType.MOB, EntityType.MUSHROOM_COW, WatchedType.COW),
	CHICKEN(EType.MOB, EntityType.CHICKEN, WatchedType.AGEABLE),
	SQUID(EType.MOB, EntityType.SQUID, WatchedType.INSENTIENT),
	BASE_HORSE(EType.NONE, -1, WatchedType.AGEABLE),
	BATTLE_HORSE(EType.NONE, -1, BASE_HORSE),
	CARGO_HORSE(EType.NONE, -1, BASE_HORSE),
	BASE_SKELETON(EType.NONE, -1, INSENTIENT),
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
	VEX(EType.MOB, EntityType.VEX, INSENTIENT),
	ARMOR_STAND_MOB(EType.MOB, EntityType.ARMOR_STAND, ARMOR_STAND),
	// Objects (Different networking values)
	BOAT(EType.OBJECT, 1, ENTITY),
	TNT(EType.OBJECT, 50, ENTITY),
	SNOWBALL(EType.OBJECT, 61, ENTITY),
	EGG(EType.OBJECT, 62, ENTITY),
	FIREBALL(EType.OBJECT, 63, ENTITY),
	FIRECHARGE(EType.OBJECT, 64, ENTITY),
	ENDERPEARL(EType.OBJECT, 65, ENTITY),
	WITHER_SKULL(EType.OBJECT, 66, FIREBALL),
	FALLING_OBJECT(EType.OBJECT, 70, ENTITY),
	ENDEREYE(EType.OBJECT, 72, ENTITY),
	POTION(EType.OBJECT, 73, ENTITY),
	EXP_BOTTLE(EType.OBJECT, 75, ENTITY),
	LEASH_KNOT(EType.OBJECT, 77, ENTITY),
	FISHING_FLOAT(EType.OBJECT, 90, ENTITY),
	ITEM(EType.OBJECT, 2, ENTITY),
	ARROW(EType.OBJECT, 60, ENTITY),
	SPECTRAL_ARROW(EType.OBJECT, 91, ARROW),
	TIPPED_ARROW(EType.OBJECT, 92, ARROW),
	FIREWORK(EType.OBJECT, 76, ENTITY),
	ITEM_FRAME(EType.OBJECT, 71, ENTITY),
	ENDER_CRYSTAL(EType.OBJECT, 51, ENTITY),
	ARMOR_STAND_OBJECT(EType.OBJECT, 78, ARMOR_STAND),
	AREA_EFFECT_CLOUD(EType.OBJECT, 3, ENTITY),
	SHULKER_BULLET(EType.OBJECT, 67, ENTITY),
	DRAGON_FIREBALL(EType.OBJECT, 93, ENTITY),
	EVOCATOR_FANGS(EType.OBJECT, 79, ENTITY),
	MINECART(EType.OBJECT, 10,  ENTITY),
	// Hack, using unsused ids; the only object where different types are send using objectData.
	MINECART_CHEST(EType.OBJECT, 211, MINECART),
	MINECART_FURNACE(EType.OBJECT, 212, MINECART),
	MINECART_TNT(EType.OBJECT, 213, MINECART),
	MINECART_MOB_SPAWNER(EType.OBJECT, 214, MINECART),
	MINECART_HOPPER(EType.OBJECT, 215, MINECART),
	MINECART_COMMAND(EType.OBJECT, 216, MINECART);

	private final EType etype;
	private final int typeId;
	private final WatchedType superType;

	public WatchedType getSuperType() {
		return superType;
	}

	public int getTypeId() {
		if (isOfType(MINECART)) {
			return MINECART.typeId;
		}
		return typeId;
	}

	public boolean isOfType(WatchedType type) {
		return ((type == this) || ((getSuperType() != null) && getSuperType().isOfType(type)));
	}

	public enum EType {
		NONE, OBJECT, MOB
	}

	private static final ArrayMap<WatchedType> OBJECT_BY_TYPE_ID = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(WatchedType.values()).filter(w -> w.etype == EType.OBJECT), WatchedType::getTypeId);
	private static final ArrayMap<WatchedType> MOB_BY_TYPE_ID = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(WatchedType.values()).filter(w -> w.etype == EType.MOB), WatchedType::getTypeId);

	public static WatchedType getObjectByTypeId(int objectTypeId) {
		WatchedType type = OBJECT_BY_TYPE_ID.get(objectTypeId);
		if (type == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown object network type id {0}", objectTypeId));
		}
		return type;
	}

	public static WatchedType getObjectByTypeAndData(int objectTypeId, int objectData) {
		WatchedType w = getObjectByTypeId(objectTypeId);
		if (w.isOfType(MINECART)) {
			return getMinecartByData(objectData);
		}
		return w;
	}

	public static WatchedType getMobByTypeId(int mobTypeId) {
		WatchedType type = MOB_BY_TYPE_ID.get(mobTypeId);
		if (type == null) {
			throw new IllegalArgumentException(MessageFormat.format("Unknown mob network type id {0}", mobTypeId));
		}
		return type;
	}

	public static WatchedType getMinecartByData(int objectData) {
		if (objectData == 0) {
			return MINECART;
		}
		return getObjectByTypeId(210 + objectData);
	}

	WatchedType(EType etype, int typeId, WatchedType superType) {
		this.etype = etype;
		this.typeId = typeId;
		this.superType = superType;
	}

	@SuppressWarnings("deprecation")
	WatchedType(EType etype, EntityType bukkitType, WatchedType superType) {
		this(etype, bukkitType.getTypeId(), superType);
	}

	WatchedType(EType etype, int typeId) {
		this(etype, typeId, null);
	}

	WatchedType(EType etype, EntityType bukkitType) {
		this(etype, bukkitType, null);
	}

	public static void init() {
	}

}
