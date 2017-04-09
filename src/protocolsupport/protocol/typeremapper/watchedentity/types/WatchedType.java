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
	BASE_MINECART(EType.NONE, -1, WatchedType.ENTITY),
	BATTLE_HORSE(EType.NONE, -1, BASE_HORSE),
	CARGO_HORSE(EType.NONE, -1, BASE_HORSE),
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
	BASE_SKELETON(EType.NONE, -1, INSENTIENT),
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
	BOAT(EType.OBJECT, EntityType.BOAT),
	TNT(EType.OBJECT, EntityType.PRIMED_TNT, ENTITY),
	SNOWBALL(EType.OBJECT, EntityType.SNOWBALL, ENTITY),
	EGG(EType.OBJECT, EntityType.EGG, ENTITY),
	FIREBALL(EType.OBJECT, EntityType.FIREBALL, ENTITY),
	FIRECHARGE(EType.OBJECT, EntityType.SMALL_FIREBALL, ENTITY),
	ENDERPEARL(EType.OBJECT, EntityType.ENDER_PEARL, ENTITY),
	WITHER_SKULL(EType.OBJECT, EntityType.WITHER_SKULL, FIREBALL),
	FALLING_OBJECT(EType.OBJECT, EntityType.FALLING_BLOCK, ENTITY),
	ENDEREYE(EType.OBJECT, EntityType.ENDER_SIGNAL, ENTITY),
	POTION(EType.OBJECT, EntityType.SPLASH_POTION, ENTITY),
	EXP_BOTTLE(EType.OBJECT, EntityType.THROWN_EXP_BOTTLE, ENTITY),
	LEASH_KNOT(EType.OBJECT, EntityType.LEASH_HITCH, ENTITY),
	FISHING_FLOAT(EType.OBJECT, EntityType.FISHING_HOOK, ENTITY),
	ITEM(EType.OBJECT, EntityType.DROPPED_ITEM, ENTITY),
	MINECART(EType.OBJECT, EntityType.MINECART, BASE_MINECART),
	MINECART_CHEST(EType.OBJECT, EntityType.MINECART_CHEST, BASE_MINECART),
	MINECART_COMMAND(EType.OBJECT, EntityType.MINECART_COMMAND, BASE_MINECART),
	MINECART_FURNACE(EType.OBJECT, EntityType.MINECART_FURNACE, BASE_MINECART),
	MINECART_HOPPER(EType.OBJECT, EntityType.MINECART_HOPPER, BASE_MINECART),
	MINECART_MOB_SPAWNER(EType.OBJECT, EntityType.MINECART_MOB_SPAWNER, BASE_MINECART),
	MINECART_TNT(EType.OBJECT, EntityType.MINECART_TNT, BASE_MINECART),
	ARROW(EType.OBJECT, EntityType.ARROW, ENTITY),
	SPECTRAL_ARROW(EType.OBJECT, EntityType.SPECTRAL_ARROW, ARROW),
	TIPPED_ARROW(EType.OBJECT, EntityType.TIPPED_ARROW, ARROW),
	FIREWORK(EType.OBJECT, EntityType.FIREWORK, ENTITY),
	ITEM_FRAME(EType.OBJECT, EntityType.ITEM_FRAME, ENTITY),
	ENDER_CRYSTAL(EType.OBJECT, EntityType.ENDER_CRYSTAL, ENTITY),
	ARMOR_STAND_OBJECT(EType.OBJECT, EntityType.ARMOR_STAND, ARMOR_STAND),
	AREA_EFFECT_CLOUD(EType.OBJECT, EntityType.AREA_EFFECT_CLOUD, ENTITY),
	SHULKER_BULLET(EType.OBJECT, EntityType.SHULKER_BULLET, ENTITY),
	DRAGON_FIREBALL(EType.OBJECT, EntityType.DRAGON_FIREBALL, ENTITY),
	EVOCATOR_FANGS(EType.OBJECT, EntityType.EVOKER_FANGS, ENTITY);
	
	private final EType etype;
	private final int typeId;
	private WatchedType superType;
	
	private static final WatchedType[] OBJECT_BY_TYPE_ID = new WatchedType[256];
	private static final WatchedType[] MOB_BY_TYPE_ID = new WatchedType[256];

	/***
	 * @return the type's parent.
	 */
	public WatchedType getSuperType(){
		return superType;
	}
	
	/***
	 * @return the typeId.
	 */
	public int getTypeId() {
		return typeId;
	}
	
	/***
	 * @return the type's eType.
	 */
	public EType getEType() {
		return etype;
	}
	
	/***
	 * Gets the WatchedType for an object using it's ID.
	 * @param objectTypeId
	 * @return the corresponding WatchedType
	 */
	public static WatchedType getObjectByTypeId(int objectTypeId) {
		if (objectTypeId < 0 || objectTypeId >= OBJECT_BY_TYPE_ID.length) {
			return WatchedType.NONE;
		}
		return OBJECT_BY_TYPE_ID[objectTypeId];
	}

	/***
	 * Gets the WatchedType for a mob using it's ID.
	 * @param mobTypeId
	 * @return the corresponding WatchedType
	 */
	public static WatchedType getMobByTypeId(int mobTypeId) {
		if (mobTypeId < 0 || mobTypeId >= MOB_BY_TYPE_ID.length) {
			return WatchedType.NONE;
		}
		return MOB_BY_TYPE_ID[mobTypeId];
	}
	
	static {
		Arrays.fill(OBJECT_BY_TYPE_ID, WatchedType.NONE);
		Arrays.fill(MOB_BY_TYPE_ID, WatchedType.NONE);
		for (WatchedType type : values()) {
			if(type.typeId != -1){
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
			}
		}
	}
	
	public static void init() {
	}
	
	/***
	 * Entities can be either a mob or an object.
	 */
	public enum EType {
		NONE, OBJECT, MOB
	}
	
	/***
	 * Checks whether the WatchedEntity is the same as <strong>type</strong> or a some child of <strong>type</strong>.
	 * @param type
	 * @return true, if there is a connection
	 */
	public boolean isOfType(WatchedType type){
		return type == this || (getSuperType() != null && getSuperType().isOfType(type));
	}
		
    //=====================================================\\
    //					Constructors					   \\
    //=====================================================\\
	
	WatchedType(EType etype, int typeId) {
		this.etype = etype;
		this.typeId = typeId;
		this.superType = null;
	}
	
	@SuppressWarnings("deprecation")
	WatchedType(EType etype, EntityType type) {
		this(etype, type.getTypeId());
	}
	
	WatchedType(EType etype, int typeId, WatchedType superType) {
		this(etype, typeId);
		this.superType = superType;
	}
	
	WatchedType(EType etype, EntityType type, WatchedType superType) {
		this(etype, type);
		this.superType = superType;
	}
}
