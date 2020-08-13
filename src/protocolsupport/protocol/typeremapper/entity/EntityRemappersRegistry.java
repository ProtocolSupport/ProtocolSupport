package protocolsupport.protocol.typeremapper.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.NetworkEntityMetadataObjectRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.EntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AbstractMerchantEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.AgeableEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.InsentientEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.LivingEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.BasePiglingEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.BatEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.BlazeEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.CreeperEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.EnderDragonEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.EndermanEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.GhastEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.GuardianEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.IllagerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.IronGolemEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.PhantomEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.PiglinEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.PillagerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ShulkerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SlimeEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SnowmanEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SpellcasterIllagerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.SpiderEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.VexEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.WitchEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.WitherEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ZoglinEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.BeeEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.FoxEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.HoglinEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.MushroomCowEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.PandaEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.PigEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.PolarBearEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.RabbitEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.SheepEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.StriderEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.TurtleEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.ageable.VillagerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish.FishEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish.PufferFishEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.fish.TropicalFishEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.BaseHorseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.BattleHorseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.CargoHorseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LamaEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacyDonkeyEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacyMuleEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacySkeletonHorseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.horse.LegacyZombieHorseEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton.LegacySkeletonEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton.LegacyStrayEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.skeleton.LegacyWitherSkeletonEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable.CatEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable.ParrotEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.tameable.WolfEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie.ZombieEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.living.zombie.ZombieVillagerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.AreaEffectCloudEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.BoatEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.EnderCrystalEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.FireworkEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.object.FishingFloatEntityMetadataRemapper;
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
import protocolsupport.protocol.typeremapper.entity.metadata.types.special.ArmorStandEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.entity.metadata.types.special.PlayerEntityMetadataRemapper;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class EntityRemappersRegistry {

	public static class EntityRemappingTable extends MappingTable {

		protected final EnumMap<NetworkEntityType, Pair<NetworkEntityType, List<NetworkEntityMetadataObjectRemapper>>> table = new EnumMap<>(NetworkEntityType.class);

		public Pair<NetworkEntityType, List<NetworkEntityMetadataObjectRemapper>> getRemap(NetworkEntityType from) {
			return table.get(from);
		}

		public void setRemap(NetworkEntityType from, NetworkEntityType to, List<NetworkEntityMetadataObjectRemapper> metadataRemapper) {
			table.put(from, ImmutablePair.of(to, metadataRemapper));
		}

	}

	public static final MappingRegistry<EntityRemappingTable> REGISTRY = new MappingRegistry<EntityRemappingTable>() {

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
			.addMapping(NetworkEntityType.PLAYER, PlayerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.EXP_ORB)
			.addMapping(NetworkEntityType.EXP_ORB, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.PAINTING)
			.addMapping(NetworkEntityType.PAINTING, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.THUNDERBOLT)
			.addMapping(NetworkEntityType.THUNDERBOLT, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.COW)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.MUSHROOM_COW)
			.addMapping(NetworkEntityType.MUSHROOM_COW, MushroomCowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.MUSHROOM_COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_13_2)
			.register();
			new Mapping(NetworkEntityType.CHICKEN)
			.addMapping(NetworkEntityType.CHICKEN, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.SQUID)
			.addMapping(NetworkEntityType.SQUID, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.COMMON_HORSE)
			.addMapping(NetworkEntityType.COMMON_HORSE, BattleHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_6)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_5_2)
			.register();
			new Mapping(NetworkEntityType.ZOMBIE_HORSE)
			.addMapping(NetworkEntityType.ZOMBIE_HORSE, BattleHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.COMMON_HORSE, LegacyZombieHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_5_2)
			.register();
			new Mapping(NetworkEntityType.SKELETON_HORSE)
			.addMapping(NetworkEntityType.SKELETON_HORSE, BattleHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.COMMON_HORSE, LegacySkeletonHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_5_2)
			.register();
			new Mapping(NetworkEntityType.DONKEY)
			.addMapping(NetworkEntityType.DONKEY, CargoHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.COMMON_HORSE, LegacyDonkeyEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_5_2)
			.register();
			new Mapping(NetworkEntityType.MULE)
			.addMapping(NetworkEntityType.MULE, CargoHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.COMMON_HORSE, LegacyMuleEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_5_2)
			.register();
			new Mapping(NetworkEntityType.LAMA)
			.addMapping(NetworkEntityType.LAMA, LamaEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.COMMON_HORSE, BaseHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_5_2)
			.register();
			new Mapping(NetworkEntityType.BAT)
			.addMapping(NetworkEntityType.BAT, BatEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.OCELOT)
			.addMapping(NetworkEntityType.OCELOT, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.WOLF)
			.addMapping(NetworkEntityType.WOLF, WolfEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.PIG)
			.addMapping(NetworkEntityType.PIG, PigEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.RABBIT)
			.addMapping(NetworkEntityType.RABBIT, RabbitEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_8)
			.addMapping(NetworkEntityType.CHICKEN, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_7_10)
			.register();
			new Mapping(NetworkEntityType.SHEEP)
			.addMapping(NetworkEntityType.SHEEP, SheepEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.POLAR_BEAR)
			.addMapping(NetworkEntityType.POLAR_BEAR, PolarBearEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_10)
			.addMapping(NetworkEntityType.SPIDER, LivingEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_9_4)
			.register();
			new Mapping(NetworkEntityType.VILLAGER)
			.addMapping(NetworkEntityType.VILLAGER, VillagerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.BEE)
			.addMapping(NetworkEntityType.BEE, BeeEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_15)
			.addMapping(NetworkEntityType.PARROT, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_12, ProtocolVersion.MINECRAFT_1_14_4))
			.addMapping(NetworkEntityType.CHICKEN, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_11_1)
			.register();
			new Mapping(NetworkEntityType.ENDERMAN)
			.addMapping(NetworkEntityType.ENDERMAN, EndermanEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.GIANT)
			.addMapping(NetworkEntityType.GIANT, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.SILVERFISH)
			.addMapping(NetworkEntityType.SILVERFISH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.ENDERMITE)
			.addMapping(NetworkEntityType.ENDERMITE, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_8)
			.addMapping(NetworkEntityType.SILVERFISH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_7_10)
			.register();
			new Mapping(NetworkEntityType.ENDER_DRAGON)
			.addMapping(NetworkEntityType.ENDER_DRAGON, EnderDragonEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.SNOWMAN)
			.addMapping(NetworkEntityType.SNOWMAN, SnowmanEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.ZOMBIE)
			.addMapping(NetworkEntityType.ZOMBIE, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			//TODO: type???
			new Mapping(NetworkEntityType.ZOMBIE_VILLAGER)
			.addMapping(NetworkEntityType.ZOMBIE_VILLAGER, ZombieVillagerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.ZOMBIE, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.HUSK)
			.addMapping(NetworkEntityType.HUSK, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.ZOMBIE, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.DROWNED)
			.addMapping(NetworkEntityType.DROWNED, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13)
			.addMapping(NetworkEntityType.ZOMBIE, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
			.register();
			new Mapping(NetworkEntityType.BLAZE)
			.addMapping(NetworkEntityType.BLAZE, BlazeEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.SPIDER)
			.addMapping(NetworkEntityType.SPIDER, SpiderEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.CAVE_SPIDER)
			.addMapping(NetworkEntityType.CAVE_SPIDER, SpiderEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.CREEPER)
			.addMapping(NetworkEntityType.CREEPER, CreeperEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.GHAST)
			.addMapping(NetworkEntityType.GHAST, GhastEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.SLIME)
			.addMapping(NetworkEntityType.SLIME, SlimeEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.MAGMA_CUBE)
			.addMapping(NetworkEntityType.MAGMA_CUBE, SlimeEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.SKELETON)
			.addMapping(NetworkEntityType.SKELETON, LegacySkeletonEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.WITHER_SKELETON)
			.addMapping(NetworkEntityType.WITHER_SKELETON, LegacySkeletonEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.SKELETON, LegacyWitherSkeletonEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.STRAY)
			.addMapping(NetworkEntityType.STRAY, LegacySkeletonEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.SKELETON, LegacyStrayEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.WITCH)
			.addMapping(NetworkEntityType.WITCH, WitchEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.IRON_GOLEM)
			.addMapping(NetworkEntityType.IRON_GOLEM, IronGolemEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.SHULKER)
			.addMapping(NetworkEntityType.SHULKER, ShulkerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_9)
			.addMapping(NetworkEntityType.BLAZE, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_8)
			.register();
			new Mapping(NetworkEntityType.WITHER)
			.addMapping(NetworkEntityType.WITHER, WitherEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.GUARDIAN)
			.addMapping(NetworkEntityType.GUARDIAN, GuardianEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_8)
			.addMapping(NetworkEntityType.SQUID, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_7_10)
			.register();
			new Mapping(NetworkEntityType.ELDER_GUARDIAN)
			.addMapping(NetworkEntityType.ELDER_GUARDIAN, GuardianEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.GUARDIAN, GuardianEntityMetadataRemapper.INSTANCE, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_8))
			.addMapping(NetworkEntityType.SQUID, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_7_10)
			.register();
			new Mapping(NetworkEntityType.VINDICATOR)
			.addMapping(NetworkEntityType.VINDICATOR, IllagerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.WITCH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.EVOKER)
			.addMapping(NetworkEntityType.EVOKER, SpellcasterIllagerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.WITCH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.ILLUSIONER)
			.addMapping(NetworkEntityType.ILLUSIONER, SpellcasterIllagerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.WITCH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.VEX)
			.addMapping(NetworkEntityType.VEX, VexEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.BLAZE, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.PARROT)
			.addMapping(NetworkEntityType.PARROT, ParrotEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_12)
			.addMapping(NetworkEntityType.CHICKEN, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_11_1)
			.register();
			new Mapping(NetworkEntityType.PHANTOM)
			.addMapping(NetworkEntityType.PHANTOM, PhantomEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13)
			.addMapping(NetworkEntityType.BLAZE, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
			.register();
			new Mapping(NetworkEntityType.DOLPHIN)
			.addMapping(NetworkEntityType.DOLPHIN, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13)
			.addMapping(NetworkEntityType.SQUID, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
			.register();
			new Mapping(NetworkEntityType.TURTLE)
			.addMapping(NetworkEntityType.TURTLE, TurtleEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13)
			.addMapping(NetworkEntityType.SQUID, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
			.register();
			new Mapping(NetworkEntityType.COD)
			.addMapping(NetworkEntityType.COD, FishEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13)
			.addMapping(NetworkEntityType.BAT, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
			.register();
			new Mapping(NetworkEntityType.SALMON)
			.addMapping(NetworkEntityType.SALMON, FishEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13)
			.addMapping(NetworkEntityType.BAT, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
			.register();
			new Mapping(NetworkEntityType.PUFFERFISH)
			.addMapping(NetworkEntityType.PUFFERFISH, PufferFishEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13)
			.addMapping(NetworkEntityType.BAT, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
			.register();
			new Mapping(NetworkEntityType.TROPICAL_FISH)
			.addMapping(NetworkEntityType.TROPICAL_FISH, TropicalFishEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13)
			.addMapping(NetworkEntityType.BAT, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
			.register();
			new Mapping(NetworkEntityType.TRADER_LAMA)
			.addMapping(NetworkEntityType.TRADER_LAMA, LamaEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.LAMA, LamaEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_13_2)
			.addMapping(NetworkEntityType.COMMON_HORSE, BaseHorseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_5_2)
			.register();
			new Mapping(NetworkEntityType.WANDERING_TRADER)
			.addMapping(NetworkEntityType.WANDERING_TRADER, AbstractMerchantEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.VILLAGER, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_13_2)
			.register();
			new Mapping(NetworkEntityType.PANDA)
			.addMapping(NetworkEntityType.PANDA, PandaEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.POLAR_BEAR, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_10__1_13_2)
			.addMapping(NetworkEntityType.SPIDER, LivingEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_9_4)
			.register();
			new Mapping(NetworkEntityType.CAT)
			.addMapping(NetworkEntityType.CAT, CatEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.OCELOT, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_13_2)
			.register();
			new Mapping(NetworkEntityType.FOX)
			.addMapping(NetworkEntityType.FOX, FoxEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.OCELOT, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_13_2)
			.register();
			new Mapping(NetworkEntityType.PILLAGER)
			.addMapping(NetworkEntityType.PILLAGER, PillagerEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.ILLUSIONER, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_11__1_13_2)
			.addMapping(NetworkEntityType.WITCH, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.RAVAGER)
			.addMapping(NetworkEntityType.RAVAGER, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_14)
			.addMapping(NetworkEntityType.CAVE_SPIDER, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_13_2)
			.register();
			new Mapping(NetworkEntityType.PIGLIN)
			.addMapping(NetworkEntityType.PIGLIN, PiglinEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_16)
			.addMapping(NetworkEntityType.VILLAGER, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_15_2)
			.register();
			new Mapping(NetworkEntityType.PIGLIN_BRUTE)
			.addMapping(NetworkEntityType.PIGLIN_BRUTE, BasePiglingEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_16_2)
			.addMapping(NetworkEntityType.PIGLIN, BasePiglingEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.RANGE__1_16__1_16_1)
			.addMapping(NetworkEntityType.VILLAGER, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_15_2)
			.register();
			new Mapping(NetworkEntityType.ZOMBIFIED_PIGLIN)
			.addMapping(NetworkEntityType.ZOMBIFIED_PIGLIN, ZombieEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.HOGLIN)
			.addMapping(NetworkEntityType.HOGLIN, HoglinEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_16)
			.addMapping(NetworkEntityType.SPIDER, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_15_2)
			.register();
			new Mapping(NetworkEntityType.ZOGLIN)
			.addMapping(NetworkEntityType.ZOGLIN, ZoglinEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_16)
			.addMapping(NetworkEntityType.CAVE_SPIDER, InsentientEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_15_2)
			.register();
			new Mapping(NetworkEntityType.STRIDER)
			.addMapping(NetworkEntityType.STRIDER, StriderEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_16)
			//TODO: remap has saddle
			.addMapping(NetworkEntityType.PIG, AgeableEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_15_2)
			.register();
			new Mapping(NetworkEntityType.ARMOR_STAND_MOB)
			.addMapping(NetworkEntityType.ARMOR_STAND_MOB, ArmorStandEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_8)
			.addMapping(NetworkEntityType.ARMOR_STAND_MOB, EntityMetadataRemapper.NOOP, ProtocolVersionsHelper.DOWN_1_7_10)
			.register();
			new Mapping(NetworkEntityType.BOAT)
			.addMapping(NetworkEntityType.BOAT, BoatEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.TNT)
			.addMapping(NetworkEntityType.TNT, TNTEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.SNOWBALL)
			.addMapping(NetworkEntityType.SNOWBALL, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.EGG)
			.addMapping(NetworkEntityType.EGG, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.FIREBALL)
			.addMapping(NetworkEntityType.FIREBALL, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.FIRECHARGE)
			.addMapping(NetworkEntityType.FIRECHARGE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.ENDERPEARL)
			.addMapping(NetworkEntityType.ENDERPEARL, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.WITHER_SKULL)
			.addMapping(NetworkEntityType.WITHER_SKULL, WitherSkullEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.FALLING_OBJECT)
			.addMapping(NetworkEntityType.FALLING_OBJECT, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.ENDEREYE)
			.addMapping(NetworkEntityType.ENDEREYE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.POTION)
			.addMapping(NetworkEntityType.POTION, PotionEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.EXP_BOTTLE)
			.addMapping(NetworkEntityType.EXP_BOTTLE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.LEASH_KNOT)
			.addMapping(NetworkEntityType.LEASH_KNOT, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_6)
			.addMapping(NetworkEntityType.LEASH_KNOT, EntityMetadataRemapper.NOOP, ProtocolVersionsHelper.DOWN_1_5_2)
			.register();
			new Mapping(NetworkEntityType.FISHING_FLOAT)
			.addMapping(NetworkEntityType.FISHING_FLOAT, FishingFloatEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.ITEM)
			.addMapping(NetworkEntityType.ITEM, ItemEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.ARROW)
			.addMapping(NetworkEntityType.ARROW, ArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.SPECTRAL_ARROW)
			.addMapping(NetworkEntityType.SPECTRAL_ARROW, ArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_9)
			.addMapping(NetworkEntityType.ARROW, ArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_8)
			.register();
			new Mapping(NetworkEntityType.TIPPED_ARROW)
			.addMapping(NetworkEntityType.TIPPED_ARROW, TippedArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_9)
			.addMapping(NetworkEntityType.ARROW, ArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_8)
			.register();
			new Mapping(NetworkEntityType.THROWN_TRIDENT)
			.addMapping(NetworkEntityType.THROWN_TRIDENT, TridentEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_13)
			.addMapping(NetworkEntityType.ARROW, ArrowEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
			.register();
			new Mapping(NetworkEntityType.FIREWORK)
			.addMapping(NetworkEntityType.FIREWORK, FireworkEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.ITEM_FRAME)
			.addMapping(NetworkEntityType.ITEM_FRAME, ItemFrameEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.ENDER_CRYSTAL)
			.addMapping(NetworkEntityType.ENDER_CRYSTAL, EnderCrystalEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.AREA_EFFECT_CLOUD)
			.addMapping(NetworkEntityType.AREA_EFFECT_CLOUD, AreaEffectCloudEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_9)
			.addMapping(NetworkEntityType.AREA_EFFECT_CLOUD, EntityMetadataRemapper.NOOP, ProtocolVersionsHelper.DOWN_1_8)
			.register();
			new Mapping(NetworkEntityType.SHULKER_BULLET)
			.addMapping(NetworkEntityType.SHULKER_BULLET, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_9)
			.addMapping(NetworkEntityType.FIRECHARGE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_8)
			.register();
			new Mapping(NetworkEntityType.LAMA_SPIT)
			.addMapping(NetworkEntityType.LAMA_SPIT, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.SNOWBALL, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.DRAGON_FIREBALL)
			.addMapping(NetworkEntityType.DRAGON_FIREBALL, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_9)
			.addMapping(NetworkEntityType.FIRECHARGE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_8)
			.register();
			new Mapping(NetworkEntityType.EVOCATOR_FANGS)
			.addMapping(NetworkEntityType.EVOCATOR_FANGS, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_11)
			.addMapping(NetworkEntityType.FIRECHARGE, BaseEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
			.register();
			new Mapping(NetworkEntityType.ARMOR_STAND_OBJECT)
			.addMapping(NetworkEntityType.ARMOR_STAND_OBJECT, ArmorStandEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.UP_1_8)
			.addMapping(NetworkEntityType.ARMOR_STAND_OBJECT, EntityMetadataRemapper.NOOP, ProtocolVersionsHelper.DOWN_1_7_10)
			.register();
			new Mapping(NetworkEntityType.MINECART)
			.addMapping(NetworkEntityType.MINECART, MinecartEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.MINECART_CHEST)
			.addMapping(NetworkEntityType.MINECART_CHEST, MinecartEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.MINECART_FURNACE)
			.addMapping(NetworkEntityType.MINECART_FURNACE, MinecartFurnaceEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.MINECART_TNT)
			.addMapping(NetworkEntityType.MINECART_TNT, MinecartEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.MINECART_MOB_SPAWNER)
			.addMapping(NetworkEntityType.MINECART_MOB_SPAWNER, MinecartEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.MINECART_HOPPER)
			.addMapping(NetworkEntityType.MINECART_HOPPER, MinecartEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
			new Mapping(NetworkEntityType.MINECART_COMMAND)
			.addMapping(NetworkEntityType.MINECART_COMMAND, MinecartCommandEntityMetadataRemapper.INSTANCE, ProtocolVersionsHelper.ALL_PC)
			.register();
		}

		@Override
		protected EntityRemappingTable createTable() {
			return new EntityRemappingTable();
		}

		public void registerRemapEntry(NetworkEntityType from, NetworkEntityType to, EntityMetadataRemapper metadataRemapper, ProtocolVersion... versions) {
			Arrays.stream(versions).forEach(version -> getTable(version).setRemap(from, to, metadataRemapper.getRemaps(version)));
		}
	};

}
