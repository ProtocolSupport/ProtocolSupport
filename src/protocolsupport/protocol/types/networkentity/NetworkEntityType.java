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

	NONE(NetworkEntityMetaType.NONE, EntityType.UNKNOWN),
	THUNDERBOLT(NetworkEntityMetaType.SPECIAL, EntityType.LIGHTNING),
	EXP_ORB(NetworkEntityMetaType.SPECIAL, EntityType.EXPERIENCE_ORB),
	PLAYER(NetworkEntityMetaType.SPECIAL, EntityType.PLAYER),
	COW(NetworkEntityMetaType.MOB, EntityType.COW),
	MUSHROOM_COW(NetworkEntityMetaType.MOB, EntityType.MUSHROOM_COW),
	CHICKEN(NetworkEntityMetaType.MOB, EntityType.CHICKEN),
	SQUID(NetworkEntityMetaType.MOB, EntityType.SQUID),
	COMMON_HORSE(NetworkEntityMetaType.MOB, EntityType.HORSE),
	ZOMBIE_HORSE(NetworkEntityMetaType.MOB, EntityType.ZOMBIE_HORSE),
	SKELETON_HORSE(NetworkEntityMetaType.MOB, EntityType.SKELETON_HORSE),
	DONKEY(NetworkEntityMetaType.MOB, EntityType.DONKEY),
	MULE(NetworkEntityMetaType.MOB, EntityType.MULE),
	LAMA(NetworkEntityMetaType.MOB, EntityType.LLAMA),
	BAT(NetworkEntityMetaType.MOB, EntityType.BAT),
	OCELOT(NetworkEntityMetaType.MOB, EntityType.OCELOT),
	WOLF(NetworkEntityMetaType.MOB, EntityType.WOLF),
	PIG(NetworkEntityMetaType.MOB, EntityType.PIG),
	RABBIT(NetworkEntityMetaType.MOB, EntityType.RABBIT),
	SHEEP(NetworkEntityMetaType.MOB, EntityType.SHEEP),
	POLAR_BEAR(NetworkEntityMetaType.MOB, EntityType.POLAR_BEAR),
	VILLAGER(NetworkEntityMetaType.MOB, EntityType.VILLAGER),
	BEE(NetworkEntityMetaType.MOB, EntityType.BEE),
	ENDERMAN(NetworkEntityMetaType.MOB, EntityType.ENDERMAN),
	GIANT(NetworkEntityMetaType.MOB, EntityType.GIANT),
	SILVERFISH(NetworkEntityMetaType.MOB, EntityType.SILVERFISH),
	ENDERMITE(NetworkEntityMetaType.MOB, EntityType.ENDERMITE),
	ENDER_DRAGON(NetworkEntityMetaType.MOB, EntityType.ENDER_DRAGON),
	SNOWMAN(NetworkEntityMetaType.MOB, EntityType.SNOWMAN),
	ZOMBIE(NetworkEntityMetaType.MOB, EntityType.ZOMBIE),
	ZOMBIE_VILLAGER(NetworkEntityMetaType.MOB, EntityType.ZOMBIE_VILLAGER, ZOMBIE),
	HUSK(NetworkEntityMetaType.MOB, EntityType.HUSK, ZOMBIE),
	DROWNED(NetworkEntityMetaType.MOB, EntityType.DROWNED, ZOMBIE),
	BLAZE(NetworkEntityMetaType.MOB, EntityType.BLAZE),
	SPIDER(NetworkEntityMetaType.MOB, EntityType.SPIDER),
	CAVE_SPIDER(NetworkEntityMetaType.MOB, EntityType.CAVE_SPIDER, SPIDER),
	CREEPER(NetworkEntityMetaType.MOB, EntityType.CREEPER),
	GHAST(NetworkEntityMetaType.MOB, EntityType.GHAST),
	SLIME(NetworkEntityMetaType.MOB, EntityType.SLIME),
	MAGMA_CUBE(NetworkEntityMetaType.MOB, EntityType.MAGMA_CUBE, SLIME),
	SKELETON(NetworkEntityMetaType.MOB, EntityType.SKELETON),
	WITHER_SKELETON(NetworkEntityMetaType.MOB, EntityType.WITHER_SKELETON, SKELETON),
	STRAY(NetworkEntityMetaType.MOB, EntityType.STRAY, SKELETON),
	WITCH(NetworkEntityMetaType.MOB, EntityType.WITCH),
	IRON_GOLEM(NetworkEntityMetaType.MOB, EntityType.IRON_GOLEM),
	SHULKER(NetworkEntityMetaType.MOB, EntityType.SHULKER),
	WITHER(NetworkEntityMetaType.MOB, EntityType.WITHER),
	GUARDIAN(NetworkEntityMetaType.MOB, EntityType.GUARDIAN),
	ELDER_GUARDIAN(NetworkEntityMetaType.MOB, EntityType.ELDER_GUARDIAN, GUARDIAN),
	VINDICATOR(NetworkEntityMetaType.MOB, EntityType.VINDICATOR),
	EVOKER(NetworkEntityMetaType.MOB, EntityType.EVOKER),
	ILLUSIONER(NetworkEntityMetaType.MOB, EntityType.ILLUSIONER, EVOKER),
	VEX(NetworkEntityMetaType.MOB, EntityType.VEX),
	PARROT(NetworkEntityMetaType.MOB, EntityType.PARROT),
	PHANTOM(NetworkEntityMetaType.MOB, EntityType.PHANTOM),
	DOLPHIN(NetworkEntityMetaType.MOB, EntityType.DOLPHIN),
	TURTLE(NetworkEntityMetaType.MOB, EntityType.TURTLE),
	COD(NetworkEntityMetaType.MOB, EntityType.COD),
	SALMON(NetworkEntityMetaType.MOB, EntityType.SALMON),
	PUFFERFISH(NetworkEntityMetaType.MOB, EntityType.PUFFERFISH),
	TROPICAL_FISH(NetworkEntityMetaType.MOB, EntityType.TROPICAL_FISH),
	TRADER_LAMA(NetworkEntityMetaType.MOB, EntityType.TRADER_LLAMA, LAMA),
	WANDERING_TRADER(NetworkEntityMetaType.MOB, EntityType.WANDERING_TRADER),
	CAT(NetworkEntityMetaType.MOB, EntityType.CAT),
	FOX(NetworkEntityMetaType.MOB, EntityType.FOX),
	PANDA(NetworkEntityMetaType.MOB, EntityType.PANDA),
	PILLAGER(NetworkEntityMetaType.MOB, EntityType.PILLAGER),
	RAVAGER(NetworkEntityMetaType.MOB, EntityType.RAVAGER),
	PIGLIN(NetworkEntityMetaType.MOB, EntityType.PIGLIN),
	PIGLIN_BRUTE(NetworkEntityMetaType.MOB, EntityType.PIGLIN_BRUTE),
	ZOMBIFIED_PIGLIN(NetworkEntityMetaType.MOB, EntityType.ZOMBIFIED_PIGLIN, ZOMBIE),
	HOGLIN(NetworkEntityMetaType.MOB, EntityType.HOGLIN),
	ZOGLIN(NetworkEntityMetaType.MOB, EntityType.ZOGLIN),
	WARDEN(NetworkEntityMetaType.MOB, EntityType.WARDEN),
	STRIDER(NetworkEntityMetaType.MOB, EntityType.STRIDER),
	AXOLOTL(NetworkEntityMetaType.MOB, EntityType.AXOLOTL),
	GLOW_SQUID(NetworkEntityMetaType.MOB, EntityType.GLOW_SQUID),
	GOAT(NetworkEntityMetaType.MOB, EntityType.GOAT),
	ALLAY(NetworkEntityMetaType.MOB, EntityType.ALLAY),
	TAPDOLE(NetworkEntityMetaType.MOB, EntityType.TADPOLE),
	FROG(NetworkEntityMetaType.MOB, EntityType.FROG),
	CAMEL(NetworkEntityMetaType.MOB, EntityType.CAMEL),
	SNIFFER(NetworkEntityMetaType.MOB, EntityType.SNIFFER),
	PAINTING(NetworkEntityMetaType.OBJECT, EntityType.PAINTING),
	BOAT(NetworkEntityMetaType.OBJECT, EntityType.BOAT),
	BOAT_CHEST(NetworkEntityMetaType.OBJECT, EntityType.CHEST_BOAT),
	TNT(NetworkEntityMetaType.OBJECT, EntityType.PRIMED_TNT),
	SNOWBALL(NetworkEntityMetaType.OBJECT, EntityType.SNOWBALL),
	EGG(NetworkEntityMetaType.OBJECT, EntityType.EGG),
	FIREBALL(NetworkEntityMetaType.OBJECT, EntityType.FIREBALL),
	FIRECHARGE(NetworkEntityMetaType.OBJECT, EntityType.SMALL_FIREBALL),
	ENDERPEARL(NetworkEntityMetaType.OBJECT, EntityType.ENDER_PEARL),
	WITHER_SKULL(NetworkEntityMetaType.OBJECT, EntityType.WITHER_SKULL, FIREBALL),
	FALLING_OBJECT(NetworkEntityMetaType.OBJECT, EntityType.FALLING_BLOCK),
	ENDEREYE(NetworkEntityMetaType.OBJECT, EntityType.ENDER_SIGNAL),
	POTION(NetworkEntityMetaType.OBJECT, EntityType.SPLASH_POTION),
	EXP_BOTTLE(NetworkEntityMetaType.OBJECT, EntityType.THROWN_EXP_BOTTLE),
	LEASH_KNOT(NetworkEntityMetaType.OBJECT, EntityType.LEASH_HITCH),
	FISHING_FLOAT(NetworkEntityMetaType.OBJECT, EntityType.FISHING_HOOK),
	ITEM(NetworkEntityMetaType.OBJECT, EntityType.DROPPED_ITEM),
	ARROW(NetworkEntityMetaType.OBJECT, EntityType.ARROW),
	SPECTRAL_ARROW(NetworkEntityMetaType.OBJECT, EntityType.SPECTRAL_ARROW, ARROW),
	TIPPED_ARROW(NetworkEntityMetaType.OBJECT, EntityType.ARROW, ARROW),
	THROWN_TRIDENT(NetworkEntityMetaType.OBJECT, EntityType.TRIDENT, ARROW),
	FIREWORK(NetworkEntityMetaType.OBJECT, EntityType.FIREWORK),
	ITEM_FRAME(NetworkEntityMetaType.OBJECT, EntityType.ITEM_FRAME),
	ENDER_CRYSTAL(NetworkEntityMetaType.OBJECT, EntityType.ENDER_CRYSTAL),
	AREA_EFFECT_CLOUD(NetworkEntityMetaType.OBJECT, EntityType.AREA_EFFECT_CLOUD),
	SHULKER_BULLET(NetworkEntityMetaType.OBJECT, EntityType.SHULKER_BULLET),
	LAMA_SPIT(NetworkEntityMetaType.OBJECT, EntityType.LLAMA_SPIT),
	DRAGON_FIREBALL(NetworkEntityMetaType.OBJECT, EntityType.DRAGON_FIREBALL),
	EVOCATOR_FANGS(NetworkEntityMetaType.OBJECT, EntityType.EVOKER_FANGS),
	MINECART(NetworkEntityMetaType.OBJECT, EntityType.MINECART),
	MINECART_CHEST(NetworkEntityMetaType.OBJECT, EntityType.MINECART_CHEST, MINECART),
	MINECART_FURNACE(NetworkEntityMetaType.OBJECT, EntityType.MINECART_FURNACE, MINECART),
	MINECART_TNT(NetworkEntityMetaType.OBJECT, EntityType.MINECART_TNT, MINECART),
	MINECART_MOB_SPAWNER(NetworkEntityMetaType.OBJECT, EntityType.MINECART_MOB_SPAWNER, MINECART),
	MINECART_HOPPER(NetworkEntityMetaType.OBJECT, EntityType.MINECART_HOPPER, MINECART),
	MINECART_COMMAND(NetworkEntityMetaType.OBJECT, EntityType.MINECART_COMMAND, MINECART),
	GLOW_ITEM_FRAME(NetworkEntityMetaType.OBJECT, EntityType.GLOW_ITEM_FRAME),
	INTERACTION(NetworkEntityMetaType.OBJECT, EntityType.INTERACTION),
	DISPLAY_BLOCK(NetworkEntityMetaType.OBJECT, EntityType.BLOCK_DISPLAY),
	DISPLAY_ITEM(NetworkEntityMetaType.OBJECT, EntityType.ITEM_DISPLAY),
	DISPLAY_TEXT(NetworkEntityMetaType.OBJECT, EntityType.TEXT_DISPLAY),
	ARMOR_STAND(NetworkEntityMetaType.OBJECTMOB, EntityType.ARMOR_STAND);

	private final NetworkEntityMetaType etype;
	private final int typeId;
	private final EntityType bukkitType;
	private final NetworkEntityType superType;

	public @Nonnull NetworkEntityMetaType getMetaType() {
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

	public enum NetworkEntityMetaType {
		NONE, SPECIAL, OBJECT(false, true), MOB(true, false), OBJECTMOB(true, true);

		private boolean mob;
		private boolean object;

		NetworkEntityMetaType() {
		}

		NetworkEntityMetaType(boolean mob, boolean object) {
			this.mob = mob;
			this.object = object;
		}

		public boolean isMob() {
			return mob;
		}

		public boolean isObject() {
			return object;
		}
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
		return (type != null) && (type.getMetaType().isObject()) ? type : NONE;
	}

	public static @Nonnull NetworkEntityType getMobByNetworkTypeId(@Nonnegative int mobTypeId) {
		NetworkEntityType type = BY_N_ID.get(mobTypeId);
		return (type != null) && (type.getMetaType().isMob()) ? type : NONE;
	}

	public static @Nonnull NetworkEntityType getByRegistrySTypeId(@Nonnull String name) {
		return BY_R_STRING_ID.getOrDefault(name, NONE);
	}

	public static @Nonnull NetworkEntityType getByBukkitType(@Nonnull EntityType btype) {
		return BY_B_TYPE.getOrDefault(btype, NONE);
	}

	NetworkEntityType(@Nonnull NetworkEntityMetaType etype, @Nonnull EntityType bukkitType, @Nullable NetworkEntityType superType) {
		this.etype = etype;
		this.bukkitType = bukkitType;
		this.typeId = MinecraftEntityData.getIdByName(getKey());
		this.superType = superType;
	}

	NetworkEntityType(@Nonnull NetworkEntityMetaType etype, @Nonnull EntityType bukkitType) {
		this(etype, bukkitType, null);
	}

}
