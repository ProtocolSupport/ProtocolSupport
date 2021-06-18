package protocolsupport.protocol.types.networkentity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

import protocolsupport.protocol.utils.minecraftdata.MinecraftEntityData;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupportbuildprocessor.Preload;

@SuppressWarnings("deprecation")
@Preload
public enum NetworkEntityType {

	NONE(EType.NONE, EntityType.UNKNOWN),
	// Specials (Spawned by separate packets)
	EXP_ORB(EType.SPECIAL, EntityType.EXPERIENCE_ORB),
	PAINTING(EType.SPECIAL, EntityType.PAINTING),
	PLAYER(EType.SPECIAL, EntityType.PLAYER),
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
	BEE(EType.MOB, EntityType.BEE),
	ENDERMAN(EType.MOB, EntityType.ENDERMAN),
	GIANT(EType.MOB, EntityType.GIANT),
	SILVERFISH(EType.MOB, EntityType.SILVERFISH),
	ENDERMITE(EType.MOB, EntityType.ENDERMITE),
	ENDER_DRAGON(EType.MOB, EntityType.ENDER_DRAGON),
	SNOWMAN(EType.MOB, EntityType.SNOWMAN),
	ZOMBIE(EType.MOB, EntityType.ZOMBIE),
	ZOMBIE_VILLAGER(EType.MOB, EntityType.ZOMBIE_VILLAGER, ZOMBIE),
	HUSK(EType.MOB, EntityType.HUSK, ZOMBIE),
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
	PIGLIN(EType.MOB, EntityType.PIGLIN),
	PIGLIN_BRUTE(EType.MOB, EntityType.PIGLIN_BRUTE),
	ZOMBIFIED_PIGLIN(EType.MOB, EntityType.ZOMBIFIED_PIGLIN, ZOMBIE),
	HOGLIN(EType.MOB, EntityType.HOGLIN),
	ZOGLIN(EType.MOB, EntityType.ZOGLIN),
	STRIDER(EType.MOB, EntityType.STRIDER),
	AXOLOTL(EType.MOB, EntityType.AXOLOTL),
	GLOW_SQUID(EType.MOB, EntityType.GLOW_SQUID),
	GOAT(EType.MOB, EntityType.GOAT),
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
	MINECART(EType.OBJECT, EntityType.MINECART),
	MINECART_CHEST(EType.OBJECT, EntityType.MINECART_CHEST, MINECART),
	MINECART_FURNACE(EType.OBJECT, EntityType.MINECART_FURNACE, MINECART),
	MINECART_TNT(EType.OBJECT, EntityType.MINECART_TNT, MINECART),
	MINECART_MOB_SPAWNER(EType.OBJECT, EntityType.MINECART_MOB_SPAWNER, MINECART),
	MINECART_HOPPER(EType.OBJECT, EntityType.MINECART_HOPPER, MINECART),
	MINECART_COMMAND(EType.OBJECT, EntityType.MINECART_COMMAND, MINECART),
	GLOW_ITEM_FRAME(EType.OBJECT, EntityType.GLOW_ITEM_FRAME),
	ARMOR_STAND_OBJECT(EType.OBJECT, EntityType.ARMOR_STAND),
	THUNDERBOLT(EType.OBJECT, EntityType.LIGHTNING);

	private final EType etype;
	private final int typeId;
	private final EntityType bukkitType;
	private final NetworkEntityType superType;

	public @Nonnull EType getEType() {
		return etype;
	}

	public @Nullable NetworkEntityType getSuperType() {
		return superType;
	}

	public @CheckForSigned int getNetworkTypeId() {
		return typeId;
	}

	public @Nonnull String getKey() {
		return bukkitType != EntityType.UNKNOWN ? bukkitType.getKey().toString() : "";
	}

	public @Nonnull EntityType getBukkitType() {
		return bukkitType;
	}

	public boolean isOfType(@Nonnull NetworkEntityType type) {
		return ((type == this) || ((getSuperType() != null) && getSuperType().isOfType(type)));
	}

	public enum EType {
		NONE, SPECIAL, OBJECT, MOB
	}

	private static final ArrayMap<NetworkEntityType> BY_N_ID = CollectionsUtils.makeEnumMappingArrayMap(Arrays.stream(NetworkEntityType.values()), (w -> w.typeId));
	private static final Map<EntityType, NetworkEntityType> BY_B_TYPE = CollectionsUtils.makeEnumMappingEnumMap(Arrays.stream(NetworkEntityType.values()), EntityType.class, NetworkEntityType::getBukkitType);
	private static final Map<String, NetworkEntityType> BY_R_STRING_ID = new HashMap<>();
	static {
		Arrays.stream(NetworkEntityType.values())
		.forEach(w -> {
			String rName = w.bukkitType.getName();
			if (rName != null) {
				BY_R_STRING_ID.put(rName, w);
				BY_R_STRING_ID.put(NamespacedKey.minecraft(rName).toString(), w);
			}
		});
	}

	public static @Nonnull NetworkEntityType getByNetworkTypeId(@Nonnegative int typeId) {
		NetworkEntityType type = BY_N_ID.get(typeId);
		return type != null ? type : NONE;
	}

	public static @Nonnull NetworkEntityType getObjectByNetworkTypeId(@Nonnegative int objectTypeId) {
		NetworkEntityType type = BY_N_ID.get(objectTypeId);
		return (type != null) && (type.getEType() == EType.OBJECT) ? type : NONE;
	}

	public static @Nonnull NetworkEntityType getMobByNetworkTypeId(@Nonnegative int mobTypeId) {
		NetworkEntityType type = BY_N_ID.get(mobTypeId);
		return (type != null) && (type.getEType() == EType.MOB) ? type : NONE;
	}

	public static @Nonnull NetworkEntityType getByRegistrySTypeId(@Nonnull String name) {
		return BY_R_STRING_ID.getOrDefault(name, NONE);
	}

	public static @Nonnull NetworkEntityType getByBukkitType(@Nonnull EntityType btype) {
		return BY_B_TYPE.getOrDefault(btype, NONE);
	}

	NetworkEntityType(@Nonnull EType etype, @Nonnull EntityType bukkitType, @Nullable NetworkEntityType superType) {
		this.etype = etype;
		this.bukkitType = bukkitType;
		this.typeId = MinecraftEntityData.getIdByName(getKey());
		this.superType = superType;
	}

	NetworkEntityType(@Nonnull EType etype, @Nonnull EntityType bukkitType) {
		this(etype, bukkitType, null);
	}

}
