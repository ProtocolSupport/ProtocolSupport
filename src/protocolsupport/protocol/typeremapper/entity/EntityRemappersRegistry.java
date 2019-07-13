package protocolsupport.protocol.typeremapper.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.EntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AbstractMerchantEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.LivingEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.BatEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.BlazeEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.CreeperEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.EnderDragonEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.EndermanEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.EvokerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.GhastEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.GiantEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.GuardianEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.IronGolemEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.PhantomEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.PillagerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ShulkerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SlimeEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SnowmanEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SpiderEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.VexEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.VindicatorEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.WitchEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.WitherEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.FoxEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.PandaEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.PigEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.PolarBearEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.RabbitEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.SheepEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.TurtleEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.VillagerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish.FishEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish.PufferFishEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish.TropicalFishEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.BattleHorseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.CargoHorseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LamaEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacyDonkeyEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacyMuleEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacySkeletonHorseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacyZombieHorseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton.LegacyStrayEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton.LegacyWitherSkeletonEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton.SkeletonEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable.CatEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable.ParrotEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable.WolfEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie.ZombieEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie.ZombieVillagerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.AreaEffectCloudEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.BoatEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.EnderCrystalEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.FireworkEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.ItemEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.ItemFrameEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.PotionEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.TNTEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.WitherSkullEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow.ArrowEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow.TippedArrowEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow.TridentEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart.MinecartCommandEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart.MinecartEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart.MinecartFurnaceEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.minecart.MinecartSpawnerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.special.ArmorStandEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.special.PlayerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class EntityRemappersRegistry {

	public static class EntityRemappingTable extends RemappingTable {

		protected final EnumMap<NetworkEntityType, Pair<NetworkEntityType, List<NetworkEntityMetadataObjectRemapper>>> table = new EnumMap<>(NetworkEntityType.class);

		public Pair<NetworkEntityType, List<NetworkEntityMetadataObjectRemapper>> getRemap(NetworkEntityType from) {
			return table.get(from);
		}

		public void setRemap(NetworkEntityType from, NetworkEntityType to, List<NetworkEntityMetadataObjectRemapper> metadataRemapper) {
			table.put(from, ImmutablePair.of(to, metadataRemapper));
		}

	}

	public static class EntityRemapperRegistry extends RemappingRegistry<EntityRemappingTable> {

		@Override
		protected EntityRemappingTable createTable() {
			return new EntityRemappingTable();
		}

		public void registerRemapEntry(NetworkEntityType from, NetworkEntityType to, EntityMetadataRemapper metadataRemapper, ProtocolVersion... versions) {
			Arrays.stream(versions).forEach(version -> getTable(version).setRemap(from, to, metadataRemapper.getRemaps(version)));
		}

	}

	public static final EntityRemapperRegistry REGISTRY = new EntityRemapperRegistry() {
		final class Mapping {
			private final NetworkEntityType from;
			private final ArrayList<ImmutableTriple<NetworkEntityType, EntityMetadataRemapper, ProtocolVersion[]>> remaps = new ArrayList<>();
			public Mapping(NetworkEntityType from) {
				this.from = from;
			}
			public Mapping addMapping(NetworkEntityType to, EntityMetadataRemapper metadataRemapper, ProtocolVersion... versions) {
				remaps.add(ImmutableTriple.of(to, metadataRemapper, versions));
				return this;
			}
			public void register() {
				for (ImmutableTriple<NetworkEntityType, EntityMetadataRemapper, ProtocolVersion[]> triple : remaps) {
					registerRemapEntry(from, triple.getLeft(), triple.getMiddle(), triple.getRight());
				}
			}
		}
		{
			new Mapping(NetworkEntityType.PLAYER)
			.addMapping(NetworkEntityType.PLAYER, new PlayerEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.EXP_ORB)
			.addMapping(NetworkEntityType.EXP_ORB, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.PAINTING)
			.addMapping(NetworkEntityType.PAINTING, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.THUNDERBOLT)
			.addMapping(NetworkEntityType.THUNDERBOLT, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.COW)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.MUSHROOM_COW)
			.addMapping(NetworkEntityType.MUSHROOM_COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.CHICKEN)
			.addMapping(NetworkEntityType.CHICKEN, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.SQUID)
			.addMapping(NetworkEntityType.SQUID, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.COMMON_HORSE)
			.addMapping(NetworkEntityType.COMMON_HORSE, BattleHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_6_AND_PE)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.ZOMBIE_HORSE)
			.addMapping(NetworkEntityType.ZOMBIE_HORSE, BattleHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.COMMON_HORSE, new LegacyZombieHorseEntityMetadataRemapper(), ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.SKELETON_HORSE)
			.addMapping(NetworkEntityType.SKELETON_HORSE, BattleHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.COMMON_HORSE, new LegacySkeletonHorseEntityMetadataRemapper(), ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.DONKEY)
			.addMapping(NetworkEntityType.DONKEY, CargoHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.COMMON_HORSE, new LegacyDonkeyEntityMetadataRemapper(), ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.MULE)
			.addMapping(NetworkEntityType.MULE, CargoHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.COMMON_HORSE, new LegacyMuleEntityMetadataRemapper(), ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.LAMA)
			.addMapping(NetworkEntityType.LAMA, LamaEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.COMMON_HORSE, LamaEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.BAT)
			.addMapping(NetworkEntityType.BAT, new BatEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.OCELOT)
			.addMapping(NetworkEntityType.OCELOT, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.WOLF)
			.addMapping(NetworkEntityType.WOLF, new WolfEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.PIG)
			.addMapping(NetworkEntityType.PIG, new PigEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.RABBIT)
			.addMapping(NetworkEntityType.RABBIT, new RabbitEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_9_AND_PE)
			.addMapping(NetworkEntityType.CHICKEN, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.SHEEP)
			.addMapping(NetworkEntityType.SHEEP, new SheepEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.POLAR_BEAR)
			.addMapping(NetworkEntityType.POLAR_BEAR, new PolarBearEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.SPIDER, LivingEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.VILLAGER)
			.addMapping(NetworkEntityType.VILLAGER, new VillagerEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.ENDERMAN)
			.addMapping(NetworkEntityType.ENDERMAN, new EndermanEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.GIANT)
			.addMapping(NetworkEntityType.GIANT, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.SILVERFISH)
			.addMapping(NetworkEntityType.SILVERFISH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.ENDERMITE)
			.addMapping(NetworkEntityType.ENDERMITE, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_8_AND_PE)
			.addMapping(NetworkEntityType.SILVERFISH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.ENDER_DRAGON)
			.addMapping(NetworkEntityType.ENDER_DRAGON, new EnderDragonEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.SNOWMAN)
			.addMapping(NetworkEntityType.SNOWMAN, new SnowmanEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.ZOMBIE)
			.addMapping(NetworkEntityType.ZOMBIE, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			//TODO: type???
			new Mapping(NetworkEntityType.ZOMBIE_VILLAGER)
			.addMapping(NetworkEntityType.ZOMBIE_VILLAGER, new ZombieVillagerEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.ZOMBIE, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.ZOMBIE_PIGMAN)
			.addMapping(NetworkEntityType.ZOMBIE_PIGMAN, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.HUSK)
			.addMapping(NetworkEntityType.HUSK, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.ZOMBIE, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.DROWNED)
			.addMapping(NetworkEntityType.DROWNED, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13_AND_PE)
			.addMapping(NetworkEntityType.ZOMBIE, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.BLAZE)
			.addMapping(NetworkEntityType.BLAZE, new BlazeEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.SPIDER)
			.addMapping(NetworkEntityType.SPIDER, SpiderEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.CAVE_SPIDER)
			.addMapping(NetworkEntityType.CAVE_SPIDER, SpiderEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.CREEPER)
			.addMapping(NetworkEntityType.CREEPER, new CreeperEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.GHAST)
			.addMapping(NetworkEntityType.GHAST, new GhastEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.SLIME)
			.addMapping(NetworkEntityType.SLIME, SlimeEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.MAGMA_CUBE)
			.addMapping(NetworkEntityType.MAGMA_CUBE, SlimeEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.SKELETON)
			.addMapping(NetworkEntityType.SKELETON, SkeletonEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.WITHER_SKELETON)
			.addMapping(NetworkEntityType.WITHER_SKELETON, SkeletonEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.SKELETON, new LegacyWitherSkeletonEntityMetadataRemapper(), ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.STRAY)
			.addMapping(NetworkEntityType.STRAY, SkeletonEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.SKELETON, new LegacyStrayEntityMetadataRemapper(), ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.WITCH)
			.addMapping(NetworkEntityType.WITCH, new WitchEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.IRON_GOLEM)
			.addMapping(NetworkEntityType.IRON_GOLEM, new IronGolemEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.SHULKER)
			.addMapping(NetworkEntityType.SHULKER, new ShulkerEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_9_AND_PE)
			.addMapping(NetworkEntityType.BLAZE, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.WITHER)
			.addMapping(NetworkEntityType.WITHER, new WitherEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.GUARDIAN)
			.addMapping(NetworkEntityType.GUARDIAN, GuardianEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_8_AND_PE)
			.addMapping(NetworkEntityType.SQUID, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.ELDER_GUARDIAN)
			.addMapping(NetworkEntityType.ELDER_GUARDIAN, GuardianEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.GUARDIAN, GuardianEntityMetadataRemapper.INSTANCE, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_8))
			.addMapping(NetworkEntityType.SQUID, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.VINDICATOR)
			.addMapping(NetworkEntityType.VINDICATOR, new VindicatorEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.WITCH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.EVOKER)
			.addMapping(NetworkEntityType.EVOKER, EvokerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.WITCH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.ILLUSIONER)
			.addMapping(NetworkEntityType.ILLUSIONER, EvokerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.WITCH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.addMapping(NetworkEntityType.WITCH, EntityMetadataRemapper.NOOP, ProtocolVersionsHelper.ALL_PE)
			.register();
			new Mapping(NetworkEntityType.VEX)
			.addMapping(NetworkEntityType.VEX, new VexEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.BLAZE, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.PARROT)
			.addMapping(NetworkEntityType.PARROT, new ParrotEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_12_AND_PE)
			.addMapping(NetworkEntityType.CHICKEN, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_12)
			.register();
			new Mapping(NetworkEntityType.PHANTOM)
			.addMapping(NetworkEntityType.PHANTOM, new PhantomEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_13_AND_PE)
			.addMapping(NetworkEntityType.BLAZE, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.DOLPHIN)
			.addMapping(NetworkEntityType.DOLPHIN, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13_AND_PE)
			.addMapping(NetworkEntityType.SQUID, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.TURTLE)
			.addMapping(NetworkEntityType.TURTLE, new TurtleEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_13_AND_PE)
			.addMapping(NetworkEntityType.SQUID, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.COD)
			.addMapping(NetworkEntityType.COD, FishEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13_AND_PE)
			.addMapping(NetworkEntityType.BAT, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.SALMON)
			.addMapping(NetworkEntityType.SALMON, FishEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13_AND_PE)
			.addMapping(NetworkEntityType.BAT, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.PUFFERFISH)
			.addMapping(NetworkEntityType.PUFFERFISH, new PufferFishEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_13_AND_PE)
			.addMapping(NetworkEntityType.BAT, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.TROPICAL_FISH)
			.addMapping(NetworkEntityType.TROPICAL_FISH, new TropicalFishEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_13_AND_PE)
			.addMapping(NetworkEntityType.BAT, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.TRADER_LAMA)
			.addMapping(NetworkEntityType.TRADER_LAMA, LamaEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.TRADER_LAMA, LamaEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PE)
			.addMapping(NetworkEntityType.LAMA, LamaEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_14)
			.register();
			new Mapping(NetworkEntityType.WANDERING_TRADER)
			.addMapping(NetworkEntityType.WANDERING_TRADER, new AbstractMerchantEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.WANDERING_TRADER, new AbstractMerchantEntityMetadataRemapper(), ProtocolVersionsHelper.ALL_PE)
			.addMapping(NetworkEntityType.VILLAGER, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_14)
			.register();
			new Mapping(NetworkEntityType.PANDA)
			.addMapping(NetworkEntityType.PANDA, new PandaEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.PANDA, new PandaEntityMetadataRemapper(), ProtocolVersionsHelper.ALL_PE)
			.addMapping(NetworkEntityType.POLAR_BEAR, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_11__1_13_2)
			.addMapping(NetworkEntityType.SPIDER, LivingEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.CAT)
			.addMapping(NetworkEntityType.CAT, new CatEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.CAT, new CatEntityMetadataRemapper(), ProtocolVersionsHelper.ALL_PE)
			.addMapping(NetworkEntityType.OCELOT, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_14)
			.register();
			new Mapping(NetworkEntityType.FOX)
			.addMapping(NetworkEntityType.FOX, new FoxEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.FOX, new FoxEntityMetadataRemapper(), ProtocolVersionsHelper.ALL_PE)
			.addMapping(NetworkEntityType.OCELOT, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_14)
			.register();
			new Mapping(NetworkEntityType.PILLAGER)
			.addMapping(NetworkEntityType.PILLAGER, new PillagerEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.PILLAGER, new PillagerEntityMetadataRemapper(), ProtocolVersionsHelper.ALL_PE)
			.addMapping(NetworkEntityType.ILLUSIONER, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_11__1_13_2)
			.addMapping(NetworkEntityType.WITCH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.RAVAGER)
			.addMapping(NetworkEntityType.RAVAGER, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.CAVE_SPIDER, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_14)
			.register();
			new Mapping(NetworkEntityType.ARMOR_STAND_MOB)
			.addMapping(NetworkEntityType.ARMOR_STAND_MOB, ArmorStandEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_8_AND_PE)
			.addMapping(NetworkEntityType.ARMOR_STAND_MOB, EntityMetadataRemapper.NOOP, ProtocolVersionsHelper.BEFORE_1_8)
			.addMapping(NetworkEntityType.ARMOR_STAND_OBJECT, EntityMetadataRemapper.NOOP, ProtocolVersionsHelper.ALL_PE)
			.register();
			new Mapping(NetworkEntityType.GIANT)
			.addMapping(NetworkEntityType.ZOMBIE, GiantEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PE)
			.addMapping(NetworkEntityType.GIANT, GiantEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.BOAT)
			.addMapping(NetworkEntityType.BOAT, new BoatEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.TNT)
			.addMapping(NetworkEntityType.TNT, new TNTEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.SNOWBALL)
			.addMapping(NetworkEntityType.SNOWBALL, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.EGG)
			.addMapping(NetworkEntityType.EGG, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.FIREBALL)
			.addMapping(NetworkEntityType.FIREBALL, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.FIRECHARGE)
			.addMapping(NetworkEntityType.FIRECHARGE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.ENDERPEARL)
			.addMapping(NetworkEntityType.ENDERPEARL, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.WITHER_SKULL)
			.addMapping(NetworkEntityType.WITHER_SKULL, new WitherSkullEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.FALLING_OBJECT)
			.addMapping(NetworkEntityType.FALLING_OBJECT, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.ENDEREYE)
			.addMapping(NetworkEntityType.ENDEREYE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.POTION)
			.addMapping(NetworkEntityType.POTION, new PotionEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.EXP_BOTTLE)
			.addMapping(NetworkEntityType.EXP_BOTTLE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.LEASH_KNOT)
			.addMapping(NetworkEntityType.LEASH_KNOT, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_6_AND_PE)
			.addMapping(NetworkEntityType.LEASH_KNOT, EntityMetadataRemapper.NOOP, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.FISHING_FLOAT)
			.addMapping(NetworkEntityType.FISHING_FLOAT, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.ITEM)
			.addMapping(NetworkEntityType.ITEM, new ItemEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.ARROW)
			.addMapping(NetworkEntityType.ARROW, ArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.SPECTRAL_ARROW)
			.addMapping(NetworkEntityType.SPECTRAL_ARROW, ArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_9)
			.addMapping(NetworkEntityType.ARROW, ArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9_AND_PE)
			.register();
			new Mapping(NetworkEntityType.TIPPED_ARROW)
			.addMapping(NetworkEntityType.TIPPED_ARROW, new TippedArrowEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_9)
			.addMapping(NetworkEntityType.ARROW, ArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9_AND_PE)
			.register();
			new Mapping(NetworkEntityType.THROWN_TRIDENT)
			.addMapping(NetworkEntityType.THROWN_TRIDENT, new TridentEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_13_AND_PE)
			.addMapping(NetworkEntityType.ARROW, ArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.FIREWORK)
			.addMapping(NetworkEntityType.FIREWORK, new FireworkEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.ITEM_FRAME)
			.addMapping(NetworkEntityType.ITEM_FRAME, new ItemFrameEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.ENDER_CRYSTAL)
			.addMapping(NetworkEntityType.ENDER_CRYSTAL, new EnderCrystalEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.AREA_EFFECT_CLOUD)
			.addMapping(NetworkEntityType.AREA_EFFECT_CLOUD, new AreaEffectCloudEntityMetadataRemapper(), ProtocolVersionsHelper.UP_1_9_AND_PE)
			.addMapping(NetworkEntityType.AREA_EFFECT_CLOUD, EntityMetadataRemapper.NOOP, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.SHULKER_BULLET)
			.addMapping(NetworkEntityType.SHULKER_BULLET, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_9_AND_PE)
			.addMapping(NetworkEntityType.FIRECHARGE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.LAMA_SPIT)
			.addMapping(NetworkEntityType.LAMA_SPIT, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.SNOWBALL, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.DRAGON_FIREBALL)
			.addMapping(NetworkEntityType.DRAGON_FIREBALL, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_9_AND_PE)
			.addMapping(NetworkEntityType.FIRECHARGE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.EVOCATOR_FANGS)
			.addMapping(NetworkEntityType.EVOCATOR_FANGS, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11_AND_PE)
			.addMapping(NetworkEntityType.FIRECHARGE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.ARMOR_STAND_OBJECT)
			.addMapping(NetworkEntityType.ARMOR_STAND_OBJECT, ArmorStandEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_8_AND_PE)
			.addMapping(NetworkEntityType.ARMOR_STAND_OBJECT, EntityMetadataRemapper.NOOP, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.MINECART)
			.addMapping(NetworkEntityType.MINECART, MinecartEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.MINECART_CHEST)
			.addMapping(NetworkEntityType.MINECART_CHEST, MinecartEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.MINECART_FURNACE)
			.addMapping(NetworkEntityType.MINECART, MinecartFurnaceEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PE)
			.addMapping(NetworkEntityType.MINECART_FURNACE, MinecartFurnaceEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.MINECART_TNT)
			.addMapping(NetworkEntityType.MINECART_TNT, MinecartEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.MINECART_MOB_SPAWNER)
			.addMapping(NetworkEntityType.MINECART, MinecartSpawnerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PE)
			.addMapping(NetworkEntityType.MINECART_MOB_SPAWNER, MinecartSpawnerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.MINECART_HOPPER)
			.addMapping(NetworkEntityType.MINECART_HOPPER, MinecartEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL)
			.register();
			new Mapping(NetworkEntityType.MINECART_COMMAND)
			.addMapping(NetworkEntityType.MINECART_COMMAND, new MinecartCommandEntityMetadataRemapper(), ProtocolVersionsHelper.ALL)
			.register();
		}
	};

}
