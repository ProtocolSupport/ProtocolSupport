package protocolsupport.protocol.typeremapper.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityDataFormatTransformRegistry.NetworkEntityDataFormatTransformerTable;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.metadata.types.NetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AbstractMerchantNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.BasePiglingNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.BatEntityNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.BlazeNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.CreeperNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.EnderDragonNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.EndermanNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.GhastNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.GuardianNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.IllagerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.IronGolemNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.PhantomNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.PiglinNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.PillagerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ShulkerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SlimeNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SnowmanNetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SpellcasterNetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SpiderNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.VexNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.WitchNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.WitherNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ZoglinNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.BeeNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.FoxNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.HoglinNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.MushroomCowNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.PandaNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.PigNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.PolarBearNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.RabbitNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.SheepNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.StriderNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.TurtleNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.VillagerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish.FishNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish.PufferFishNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish.TropicalFishNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.BattleHorseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.CargoHorseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LamaEntityNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacyDonkeyNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacySkeletonHorseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacyZombieHorseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton.LegacySkeletonNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton.LegacyStrayNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton.LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable.CatNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable.ParrotNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable.WolfNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie.ZombieNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie.ZombieVillagerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.AreaEffectNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.BoatNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.EnderCrystalNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.FireworkNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.FishingFloatNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.ItemEntityNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.ItemFrameNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.PotionNetworkEntityMetadataFormatTransformerFactoryFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.TNTNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.WitherSkullNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow.ArrowEntityNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow.TippedArrowNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow.TridentNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart.MinecartCommandNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart.MinecartFurnaceNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart.MinecartNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.special.ArmorStandNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.metadata.types.special.PlayerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityDataFormatTransformRegistry extends MappingRegistry<NetworkEntityDataFormatTransformerTable> {

	public static final NetworkEntityDataFormatTransformRegistry INSTANCE = new NetworkEntityDataFormatTransformRegistry();

	protected NetworkEntityDataFormatTransformRegistry() {
		registerSimple(NetworkEntityType.PLAYER, PlayerNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.EXP_ORB, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.PAINTING, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.COW, AgeableNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MUSHROOM_COW, MushroomCowNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.CHICKEN, AgeableNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.SQUID, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.COMMON_HORSE, BattleHorseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_6);

		new Mapping(NetworkEntityType.ZOMBIE_HORSE)
		.add(NetworkEntityType.ZOMBIE_HORSE, BattleHorseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.COMMON_HORSE, LegacyZombieHorseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
		.register();

		new Mapping(NetworkEntityType.SKELETON_HORSE)
		.add(NetworkEntityType.SKELETON_HORSE, BattleHorseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.COMMON_HORSE, LegacySkeletonHorseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
		.register();

		new Mapping(NetworkEntityType.DONKEY)
		.add(NetworkEntityType.DONKEY, CargoHorseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.COMMON_HORSE, LegacyDonkeyNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
		.register();

		registerSimple(NetworkEntityType.MULE, CargoHorseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.LAMA, LamaEntityNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.BAT, BatEntityNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.OCELOT, AgeableNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.WOLF, WolfNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.PIG, PigNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.RABBIT, RabbitNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_8);

		registerSimple(NetworkEntityType.SHEEP, SheepNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.POLAR_BEAR, PolarBearNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_10);

		registerSimple(NetworkEntityType.VILLAGER, VillagerNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.BEE, BeeNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_15);

		registerSimple(NetworkEntityType.ENDERMAN, EndermanNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.GIANT, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.SILVERFISH, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.ENDERMITE, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_8);

		registerSimple(NetworkEntityType.ENDER_DRAGON, EnderDragonNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.SNOWMAN, SnowmanNetworkEntityMetadataFormatTransformer.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.ZOMBIE, ZombieNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.ZOMBIE_VILLAGER, ZombieVillagerNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.HUSK, ZombieNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.DROWNED, ZombieNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_13);

		registerSimple(NetworkEntityType.BLAZE, BlazeNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.SPIDER, SpiderNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.CAVE_SPIDER, SpiderNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.CREEPER, CreeperNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.GHAST, GhastNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.SLIME, SlimeNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MAGMA_CUBE, SlimeNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.SKELETON, LegacySkeletonNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		new Mapping(NetworkEntityType.WITHER_SKELETON)
		.add(NetworkEntityType.WITHER_SKELETON, LegacySkeletonNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.SKELETON, LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
		.register();

		new Mapping(NetworkEntityType.STRAY)
		.add(NetworkEntityType.STRAY, LegacySkeletonNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.SKELETON, LegacyStrayNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersion.MINECRAFT_1_10)
		.register();

		registerSimple(NetworkEntityType.STRAY, LegacySkeletonNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.WITCH, WitchNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.IRON_GOLEM, IronGolemNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.SHULKER, ShulkerNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_9);

		registerSimple(NetworkEntityType.WITHER, WitherNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.GUARDIAN, GuardianNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_8);

		registerSimple(NetworkEntityType.ELDER_GUARDIAN, GuardianNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.VINDICATOR, IllagerNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.EVOKER, SpellcasterNetworkEntityMetadataFormatTransformer.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.ILLUSIONER, SpellcasterNetworkEntityMetadataFormatTransformer.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.VEX, VexNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.PARROT, ParrotNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_12);

		registerSimple(NetworkEntityType.PHANTOM, PhantomNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_13);

		registerSimple(NetworkEntityType.DOLPHIN, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_13);

		registerSimple(NetworkEntityType.TURTLE, TurtleNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_13);

		registerSimple(NetworkEntityType.COD, FishNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_13);

		registerSimple(NetworkEntityType.SALMON, FishNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_13);

		registerSimple(NetworkEntityType.PUFFERFISH, PufferFishNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_13);

		registerSimple(NetworkEntityType.TROPICAL_FISH, TropicalFishNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_13);

		registerSimple(NetworkEntityType.TRADER_LAMA, LamaEntityNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.WANDERING_TRADER, AbstractMerchantNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.PANDA, PandaNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.CAT, CatNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.FOX, FoxNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.PILLAGER, PillagerNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.RAVAGER, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.PIGLIN, PiglinNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_16);

		registerSimple(NetworkEntityType.PIGLIN_BRUTE, BasePiglingNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_16_2);

		registerSimple(NetworkEntityType.ZOMBIFIED_PIGLIN, ZombieNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.HOGLIN, HoglinNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_16);

		registerSimple(NetworkEntityType.ZOGLIN, ZoglinNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_16);

		registerSimple(NetworkEntityType.STRIDER, StriderNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_16);

		registerSimple(NetworkEntityType.ARMOR_STAND_MOB, ArmorStandNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_8);

		registerSimple(NetworkEntityType.BOAT, BoatNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.TNT, TNTNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.SNOWBALL, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.EGG, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.FIREBALL, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.FIRECHARGE, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.ENDERPEARL, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.WITHER_SKULL, WitherSkullNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.FALLING_OBJECT, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.ENDEREYE, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.POTION, PotionNetworkEntityMetadataFormatTransformerFactoryFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.EXP_BOTTLE, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.LEASH_KNOT, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_6);

		registerSimple(NetworkEntityType.FISHING_FLOAT, FishingFloatNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.ITEM, ItemEntityNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.ARROW, ArrowEntityNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.SPECTRAL_ARROW, ArrowEntityNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_9);

		registerSimple(NetworkEntityType.TIPPED_ARROW, TippedArrowNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_9);

		registerSimple(NetworkEntityType.THROWN_TRIDENT, TridentNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_13);

		registerSimple(NetworkEntityType.FIREWORK, FireworkNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.ITEM_FRAME, ItemFrameNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.ENDER_CRYSTAL, EnderCrystalNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.AREA_EFFECT_CLOUD, AreaEffectNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_9);

		registerSimple(NetworkEntityType.SHULKER_BULLET, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_9);

		registerSimple(NetworkEntityType.LAMA_SPIT, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.DRAGON_FIREBALL, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_9);

		registerSimple(NetworkEntityType.EVOCATOR_FANGS, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.ARMOR_STAND_OBJECT, ArmorStandNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_8);

		registerSimple(NetworkEntityType.MINECART, MinecartNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_CHEST, MinecartNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_FURNACE, MinecartFurnaceNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_TNT, MinecartNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_MOB_SPAWNER, MinecartNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_HOPPER, MinecartNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_COMMAND, MinecartCommandNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.THUNDERBOLT, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);
	}

	protected final class Mapping {

		private final NetworkEntityType from;
		private final ArrayList<ImmutableTriple<NetworkEntityType, NetworkEntityMetadataFormatTransformerFactory, ProtocolVersion[]>> remaps = new ArrayList<>();

		public Mapping(NetworkEntityType from) {
			this.from = from;
		}

		public Mapping add(NetworkEntityType to, NetworkEntityMetadataFormatTransformerFactory metadataRemapper, ProtocolVersion... versions) {
			remaps.add(ImmutableTriple.of(to, metadataRemapper, versions));
			return this;
		}

		public void register() {
			for (ImmutableTriple<NetworkEntityType, NetworkEntityMetadataFormatTransformerFactory, ProtocolVersion[]> triple : remaps) {
				NetworkEntityDataFormatTransformRegistry.this.register(from, triple.getLeft(), triple.getMiddle(), triple.getRight());
			}
		}

	}

	protected void registerSimple(NetworkEntityType type, NetworkEntityMetadataFormatTransformerFactory metadataRemapper, ProtocolVersion... versions) {
		register(type, type, metadataRemapper, versions);
	}

	protected void register(NetworkEntityType from, NetworkEntityType to, NetworkEntityMetadataFormatTransformerFactory metadataRemapper, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getTable(version).set(from, to, metadataRemapper.getObjectsTransformers(version)));
	}

	@Override
	protected NetworkEntityDataFormatTransformerTable createTable() {
		return new NetworkEntityDataFormatTransformerTable();
	}

	public static class NetworkEntityDataFormatTransformerTable extends MappingTable {

		protected final EnumMap<NetworkEntityType, Pair<NetworkEntityType, List<NetworkEntityMetadataFormatTransformer>>> table = new EnumMap<>(NetworkEntityType.class);

		public Pair<NetworkEntityType, List<NetworkEntityMetadataFormatTransformer>> get(NetworkEntityType from) {
			return table.get(from);
		}

		public void set(NetworkEntityType from, NetworkEntityType to, List<NetworkEntityMetadataFormatTransformer> metadataRemapper) {
			table.put(from, ImmutablePair.of(to, metadataRemapper));
		}

	}

}
