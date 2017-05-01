package protocolsupport.protocol.typeremapper.watchedentity.types;

import java.util.Arrays;

import org.bukkit.entity.EntityType;

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
	FIREWORK(EType.OBJECT, 76, EntityType.FIREWORK, ENTITY),
	ITEM_FRAME(EType.OBJECT, 71, EntityType.ITEM_FRAME, ENTITY),
	ENDER_CRYSTAL(EType.OBJECT, 51, EntityType.ENDER_CRYSTAL, ENTITY),
	ARMOR_STAND_OBJECT(EType.OBJECT, 78, EntityType.ARMOR_STAND, ARMOR_STAND),
	AREA_EFFECT_CLOUD(EType.OBJECT, 3, EntityType.AREA_EFFECT_CLOUD, ENTITY),
	SHULKER_BULLET(EType.OBJECT, 67, EntityType.SHULKER_BULLET, ENTITY),
	DRAGON_FIREBALL(EType.OBJECT, 93, EntityType.DRAGON_FIREBALL, ENTITY),
	EVOCATOR_FANGS(EType.OBJECT, 79, EntityType.EVOKER_FANGS, ENTITY),
	MINECART(EType.OBJECT, 10, EntityType.MINECART, ENTITY),
	// Hack, using unsused ids; the only object where different types are send using objectData.
	MINECART_CHEST(EType.OBJECT, 211, EntityType.MINECART_CHEST, MINECART),
	MINECART_FURNACE(EType.OBJECT, 212, EntityType.MINECART_FURNACE, MINECART),
	MINECART_TNT(EType.OBJECT, 213, EntityType.MINECART_TNT, MINECART),
	MINECART_MOB_SPAWNER(EType.OBJECT, 214, EntityType.MINECART_MOB_SPAWNER, MINECART),
	MINECART_HOPPER(EType.OBJECT, 215, EntityType.MINECART_HOPPER, MINECART),
	MINECART_COMMAND(EType.OBJECT, 216, EntityType.MINECART_COMMAND, MINECART);

	private final EType etype;
	private final EntityType bukkitType;
	private final int typeId;
	private final WatchedType superType;

	/***
	 * Gets the type's parent.
	 *
	 * @return the type's parent.
	 */
	public WatchedType getSuperType() {
		return superType;
	}

	/***
	 * Gets the networkId.
	 *
	 * @return the typeId.
	 */
	public int getTypeId() {
		if (isOfType(MINECART)) {
			return MINECART.typeId;
		}
		return typeId;
	}

	/***
	 * Gets the type's form.
	 *
	 * @return the type's eType.
	 */
	public EType getEType() {
		return etype;
	}

	/***
	 * Gets the type's bukkitType.
	 *
	 * @return the bukkit type of the WatchedType or null.
	 */
	public EntityType getBukkitType() {
		return bukkitType;
	}

	/***
	 * Gets the type's bukkitTypeId.
	 *
	 * @return the bukkit typeId of the WatchedType or 0.
	 */
	@SuppressWarnings("deprecation")
	public int getBukkitTypeId() {
		if (bukkitType != null) {
			return bukkitType.getTypeId();
		}
		return -1;
	}

	/***
	 * Checks whether the WatchedEntity is the same as <strong>type</strong> or
	 * a some child of <strong>type</strong>.
	 *
	 * @param type another type
	 * @return true, if there is a connection
	 */
	public boolean isOfType(WatchedType type) {
		return ((type == this) || ((getSuperType() != null) && getSuperType().isOfType(type)));
	}

	/***
	 * Entities can be either a mob or an object.
	 */
	public enum EType {
		NONE, OBJECT, MOB
	}

	private static final WatchedType[] OBJECT_BY_TYPE_ID = new WatchedType[256];
	private static final WatchedType[] TYPE_BUKKIT_ID = new WatchedType[256];
	private static final WatchedType[] MOB_BY_TYPE_ID = new WatchedType[256];

	// Fill the static values.
	static {
		Arrays.fill(OBJECT_BY_TYPE_ID, WatchedType.NONE);
		Arrays.fill(MOB_BY_TYPE_ID, WatchedType.NONE);
		Arrays.fill(TYPE_BUKKIT_ID, WatchedType.NONE);
		for (WatchedType type : values()) {
			if (type.typeId != -1) {
				switch (type.etype) {
					case OBJECT: {
						OBJECT_BY_TYPE_ID[type.typeId] = type;
						break;
					}
					case MOB: {
						MOB_BY_TYPE_ID[type.typeId] = type;
						break;
					}
					default: {
						break;
					}
				}
				if (type.getBukkitTypeId() != -1) {
					TYPE_BUKKIT_ID[type.getBukkitTypeId()] = type;
				}
			}
		}
	}

	/***
	 * Gets the WatchedType for an object using it's ID. Does not form minecarts!
	 *
	 * @param objectTypeId object network type id
	 * @return the corresponding WatchedType
	 */
	public static WatchedType getObjectByTypeId(int objectTypeId) {
		if ((objectTypeId < 0) || (objectTypeId >= OBJECT_BY_TYPE_ID.length)) {
			return WatchedType.NONE;
		}
		return OBJECT_BY_TYPE_ID[objectTypeId];
	}

	/***
	 * Gets the WatchedType for an object using it's ID and objectData.
	 *
	 * @param objectTypeId object network type id
	 * @param objectData object network data
	 * @return the corresponding WatchedType
	 */
	public static WatchedType getObjectByTypeAndData(int objectTypeId, int objectData) {
		WatchedType w = getObjectByTypeId(objectTypeId);
		if (w.isOfType(MINECART)) {
			return minecartFromData(objectData);
		}
		return w;
	}

	/***
	 * Gets the WatchedType for a mob using it's ID.
	 *
	 * @param mobTypeId mob network type id
	 * @return the corresponding WatchedType
	 */
	public static WatchedType getMobByTypeId(int mobTypeId) {
		if ((mobTypeId < 0) || (mobTypeId >= MOB_BY_TYPE_ID.length)) {
			return WatchedType.NONE;
		}
		return MOB_BY_TYPE_ID[mobTypeId];
	}

	/***
	 * Gets the WatchedType for any entity using it's bukkitId.
	 *
	 * @param bukkitId bukkit type id
	 * @return the corresponding WatchedType
	 */
	public static WatchedType fromBukkitTypeId(int bukkitId) {
		if ((bukkitId < 0) || (bukkitId >= TYPE_BUKKIT_ID.length)) {
			return WatchedType.NONE;
		}
		return TYPE_BUKKIT_ID[bukkitId];
	}

	/***
	 * Gets the WatchedType for any entity using it's EntityType.
	 *
	 * @param entityType {@link EntityType}
	 * @return the corresponding WatchedType
	 */
	@SuppressWarnings("deprecation")
	public static WatchedType fromEntityType(EntityType entityType) {
		return fromBukkitTypeId(entityType.getTypeId());
	}

	/***
	 * Gets the WatchedType of a minecart based on the objectData.
	 *
	 * @param objectData minecart network object data
	 * @return the corresponding WatchedType
	 */
	public static WatchedType minecartFromData(int objectData) {
		if (objectData == 0) {
			return MINECART;
		}
		return getObjectByTypeId(210 + objectData);
	}

	WatchedType(EType etype, int typeId, EntityType bukkitType, WatchedType superType) {
		this.etype = etype;
		this.typeId = typeId;
		this.bukkitType = bukkitType;
		this.superType = superType;
	}

	WatchedType(EType etype, int typeId, WatchedType superType) {
		this(etype, typeId, null, superType);
	}

	@SuppressWarnings("deprecation")
	WatchedType(EType etype, EntityType bukkitType, WatchedType superType) {
		this(etype, bukkitType.getTypeId(), bukkitType, superType);
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
