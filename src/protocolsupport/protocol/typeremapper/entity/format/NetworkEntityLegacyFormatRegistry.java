package protocolsupport.protocol.typeremapper.entity.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.format.metadata.object.NetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.NetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AbstractMerchantNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.AgeableNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.base.InsentientNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.AllayNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.BasePiglingNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.BatEntityNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.BlazeNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.CreeperNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.EnderDragonNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.EndermanNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.GhastNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.GuardianNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.IllagerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.IronGolemNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.PhantomNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.PiglinNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.PillagerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ShulkerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.SlimeNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.SnowmanNetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.SpellcasterNetworkEntityMetadataFormatTransformer;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.SpiderNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.VexNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.WardenNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.WitchNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.WitherNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ZoglinNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.AxolotlNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.BeeNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.FoxNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.FrogNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.GoatNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.HoglinNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.MushroomCowNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.PandaNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.PigNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.PolarBearNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.RabbitNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.SheepNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.StriderNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.TurtleNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.ageable.VillagerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.fish.FishNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.fish.PufferFishNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.fish.TropicalFishNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse.BattleHorseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse.CargoHorseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse.LamaEntityNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse.LegacyDonkeyNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse.LegacyMuleNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse.LegacySkeletonHorseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.horse.LegacyZombieHorseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.skeleton.LegacySkeletonNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.skeleton.LegacyStrayNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.skeleton.LegacyWitherSkeletonNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable.CatNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable.LegacyCatNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable.ParrotNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.tameable.WolfNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.zombie.LegacyZombieVillagerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.zombie.ZombieNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.living.zombie.ZombieVillagerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.AreaEffectNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.BoatNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.EnderCrystalNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.FireworkNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.FishingFloatNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.ItemEntityNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.ItemFrameNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.PotionNetworkEntityMetadataFormatTransformerFactoryFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.TNTNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.WitherSkullNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.arrow.ArrowEntityNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.arrow.TippedArrowNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.arrow.TridentNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.minecart.MinecartCommandNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.minecart.MinecartFurnaceNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.object.minecart.MinecartNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.special.ArmorStandNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.entity.format.metadata.types.special.PlayerNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityLegacyFormatRegistry extends MappingRegistry<NetworkEntityLegacyFormatTable> {

	public static final NetworkEntityLegacyFormatRegistry INSTANCE = new NetworkEntityLegacyFormatRegistry();

	protected NetworkEntityLegacyFormatRegistry() {
		registerSimple(NetworkEntityType.PLAYER, PlayerNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.EXP_ORB, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.PAINTING, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.COW, AgeableNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MUSHROOM_COW, MushroomCowNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.CHICKEN, AgeableNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.SQUID, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.GLOW_SQUID, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_17);

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

		new Mapping(NetworkEntityType.MULE)
		.add(NetworkEntityType.MULE, CargoHorseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.COMMON_HORSE, LegacyMuleNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
		.register();

		registerSimple(NetworkEntityType.CAMEL, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_20); //TODO: data format

		registerSimple(NetworkEntityType.SNIFFER, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_20); //TODO: data format

		registerSimple(NetworkEntityType.LAMA, LamaEntityNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.BAT, BatEntityNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.OCELOT, AgeableNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.WOLF, WolfNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.PIG, PigNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.RABBIT, RabbitNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_8);

		registerSimple(NetworkEntityType.SHEEP, SheepNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.GOAT, GoatNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_17);

		registerSimple(NetworkEntityType.ALLAY, AllayNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_19);

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

		new Mapping(NetworkEntityType.ZOMBIE_VILLAGER)
		.add(NetworkEntityType.ZOMBIE_VILLAGER, ZombieVillagerNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.ZOMBIE, LegacyZombieVillagerNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
		.register();

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

		new Mapping(NetworkEntityType.ELDER_GUARDIAN)
		.add(NetworkEntityType.ELDER_GUARDIAN, GuardianNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.GUARDIAN, new GuardianNetworkEntityMetadataFormatTransformerFactory(true), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_10))
		.register();

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

		registerSimple(NetworkEntityType.TAPDOLE, FishNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_19);

		registerSimple(NetworkEntityType.AXOLOTL, AxolotlNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_17);

		registerSimple(NetworkEntityType.FROG, FrogNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_19);

		registerSimple(NetworkEntityType.TRADER_LAMA, LamaEntityNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.WANDERING_TRADER, AbstractMerchantNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.PANDA, PandaNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		new Mapping(NetworkEntityType.CAT)
		.add(NetworkEntityType.CAT, CatNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14)
		.add(NetworkEntityType.OCELOT, LegacyCatNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.DOWN_1_13_2)
		.register();

		registerSimple(NetworkEntityType.FOX, FoxNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.PILLAGER, PillagerNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.RAVAGER, InsentientNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_14);

		registerSimple(NetworkEntityType.PIGLIN, PiglinNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_16);

		registerSimple(NetworkEntityType.PIGLIN_BRUTE, BasePiglingNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_16_2);

		registerSimple(NetworkEntityType.ZOMBIFIED_PIGLIN, ZombieNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.HOGLIN, HoglinNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_16);

		registerSimple(NetworkEntityType.ZOGLIN, ZoglinNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_16);

		registerSimple(NetworkEntityType.WARDEN, WardenNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_19);

		registerSimple(NetworkEntityType.STRIDER, StriderNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_16);

		registerSimple(NetworkEntityType.BOAT, BoatNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.BOAT_CHEST, BoatNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_19);

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

		registerSimple(NetworkEntityType.GLOW_ITEM_FRAME, ItemFrameNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.ENDER_CRYSTAL, EnderCrystalNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.AREA_EFFECT_CLOUD, AreaEffectNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_9);

		registerSimple(NetworkEntityType.SHULKER_BULLET, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_9);

		registerSimple(NetworkEntityType.LAMA_SPIT, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.DRAGON_FIREBALL, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_9);

		registerSimple(NetworkEntityType.EVOCATOR_FANGS, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_11);

		registerSimple(NetworkEntityType.MINECART, MinecartNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_CHEST, MinecartNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_FURNACE, MinecartFurnaceNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_TNT, MinecartNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_MOB_SPAWNER, MinecartNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_HOPPER, MinecartNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.MINECART_COMMAND, MinecartCommandNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.THUNDERBOLT, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.ALL_PC);

		registerSimple(NetworkEntityType.DISPLAY_BLOCK, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_20); //TODO: data format

		registerSimple(NetworkEntityType.DISPLAY_ITEM, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_20); //TODO: data format

		registerSimple(NetworkEntityType.DISPLAY_TEXT, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_20); //TODO: data format

		registerSimple(NetworkEntityType.INTERACTION, BaseNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_20); //TODO: data format

		registerSimple(NetworkEntityType.ARMOR_STAND, ArmorStandNetworkEntityMetadataFormatTransformerFactory.INSTANCE, ProtocolVersionsHelper.UP_1_8);
	}

	protected final class Mapping {

		private final NetworkEntityType from;
		private final ArrayList<ImmutableTriple<NetworkEntityType, NetworkEntityMetadataFormatTransformerFactory, ProtocolVersion[]>> remaps = new ArrayList<>();

		public Mapping(NetworkEntityType from) {
			this.from = from;
		}

		public Mapping add(NetworkEntityType to, NetworkEntityMetadataFormatTransformerFactory metadataTransformerFactory, ProtocolVersion... versions) {
			remaps.add(ImmutableTriple.of(to, metadataTransformerFactory, versions));
			return this;
		}

		public void register() {
			for (ImmutableTriple<NetworkEntityType, NetworkEntityMetadataFormatTransformerFactory, ProtocolVersion[]> triple : remaps) {
				NetworkEntityLegacyFormatRegistry.this.register(from, triple.getLeft(), triple.getMiddle(), triple.getRight());
			}
		}

	}

	protected void registerSimple(NetworkEntityType type, NetworkEntityMetadataFormatTransformerFactory metadataTransformerFactory, ProtocolVersion... versions) {
		register(type, type, metadataTransformerFactory, versions);
	}

	protected void register(NetworkEntityType from, NetworkEntityType to, NetworkEntityMetadataFormatTransformerFactory metadataTransformerFactory, ProtocolVersion... versions) {
		Arrays.stream(versions).forEach(version -> getTable(version).set(from, new NetworkEntityLegacyFormatEntry(to, metadataTransformerFactory.get(version))));
	}

	@Override
	protected NetworkEntityLegacyFormatTable createTable() {
		return new NetworkEntityLegacyFormatTable();
	}

	public static class NetworkEntityLegacyFormatTable extends MappingTable {

		protected final EnumMap<NetworkEntityType, NetworkEntityLegacyFormatEntry> table = new EnumMap<>(NetworkEntityType.class);

		public NetworkEntityLegacyFormatEntry get(NetworkEntityType from) {
			return table.get(from);
		}

		public void set(NetworkEntityType from, NetworkEntityLegacyFormatEntry entry) {
			table.put(from, entry);
		}

	}

	public static class NetworkEntityLegacyFormatEntry {

		protected final NetworkEntityType type;
		protected final List<NetworkEntityMetadataFormatTransformer> metadataTransformers;

		public NetworkEntityLegacyFormatEntry(NetworkEntityType type, List<NetworkEntityMetadataFormatTransformer> metadataTransformers) {
			this.type = type;
			this.metadataTransformers = metadataTransformers;
		}

		public NetworkEntityType getType() {
			return type;
		}

		public List<NetworkEntityMetadataFormatTransformer> getMetadataTransformers() {
			return metadataTransformers;
		}

	}

}
