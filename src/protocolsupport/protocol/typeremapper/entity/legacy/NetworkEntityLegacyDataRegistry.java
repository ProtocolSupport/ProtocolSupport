package protocolsupport.protocol.typeremapper.entity.legacy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.BlockDataLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.BatInitDefaultMetadataTranfromer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.BattleHorseInitDefaultMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.BlazeInitDefaultMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.GenericEntityDirectBlockDataMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.GenericEntityItemMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.GenericEntityParticleMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.GenericEntityVarIntBlockDataMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.NoopMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.ParrotInitDefaultMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.SheepInitDefaultMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.SpiderInitDefaultMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.VillagerInitDefaultMetadataTransformer;
import protocolsupport.protocol.typeremapper.entity.legacy.metadata.WitchInitDefaultMetadataTransformer;
import protocolsupport.protocol.typeremapper.particle.NetworkParticleLegacyData;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.BitUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityLegacyDataRegistry extends MappingRegistry<NetworkEntityLegacyDataTable> {

	public static final NetworkEntityLegacyDataRegistry INSTANCE = new NetworkEntityLegacyDataRegistry();

	protected NetworkEntityLegacyDataRegistry() {
		registerNoop(NetworkEntityType.PLAYER);

		registerNoop(NetworkEntityType.EXP_ORB);

		registerNoop(NetworkEntityType.PAINTING);

		registerNoop(NetworkEntityType.COW);

		registerNoop(NetworkEntityType.MUSHROOM_COW);

		registerNoop(NetworkEntityType.CHICKEN);

		registerNoop(NetworkEntityType.SQUID);

		new Mapping(NetworkEntityType.GLOW_SQUID)
		.add(NetworkEntityType.GLOW_SQUID, ProtocolVersionsHelper.UP_1_17)
		.add(NetworkEntityType.SQUID, ProtocolVersionsHelper.DOWN_1_16_4)
		.register();

		new Mapping(NetworkEntityType.AXOLOTL)
		.add(NetworkEntityType.AXOLOTL, ProtocolVersionsHelper.UP_1_17)
		.add(NetworkEntityType.DOLPHIN, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_13, ProtocolVersion.MINECRAFT_1_16_4))
		.add(NetworkEntityType.SPIDER, SpiderInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		new Mapping(NetworkEntityType.COMMON_HORSE)
		.add(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.UP_1_6)
		.add(NetworkEntityType.COW, ProtocolVersionsHelper.DOWN_1_5_2)
		.register();

		new Mapping(NetworkEntityType.ZOMBIE_HORSE)
		.add(NetworkEntityType.ZOMBIE_HORSE, ProtocolVersionsHelper.UP_1_6)
		.add(NetworkEntityType.COW, ProtocolVersionsHelper.DOWN_1_5_2)
		.register();

		new Mapping(NetworkEntityType.SKELETON_HORSE)
		.add(NetworkEntityType.SKELETON_HORSE, ProtocolVersionsHelper.UP_1_6)
		.add(NetworkEntityType.COW, ProtocolVersionsHelper.DOWN_1_5_2)
		.register();

		new Mapping(NetworkEntityType.DONKEY)
		.add(NetworkEntityType.DONKEY, ProtocolVersionsHelper.UP_1_6)
		.add(NetworkEntityType.COW, ProtocolVersionsHelper.DOWN_1_5_2)
		.register();

		new Mapping(NetworkEntityType.MULE)
		.add(NetworkEntityType.MULE, ProtocolVersionsHelper.UP_1_6)
		.add(NetworkEntityType.COW, ProtocolVersionsHelper.DOWN_1_5_2)
		.register();

		new Mapping(NetworkEntityType.LAMA)
		.add(NetworkEntityType.LAMA, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.COMMON_HORSE, BattleHorseInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
		.add(NetworkEntityType.COW, ProtocolVersionsHelper.DOWN_1_5_2)
		.register();

		registerNoop(NetworkEntityType.BAT);

		registerNoop(NetworkEntityType.OCELOT);

		registerNoop(NetworkEntityType.WOLF);

		registerNoop(NetworkEntityType.PIG);

		new Mapping(NetworkEntityType.RABBIT)
		.add(NetworkEntityType.RABBIT, ProtocolVersionsHelper.UP_1_8)
		.add(NetworkEntityType.CHICKEN, ProtocolVersionsHelper.DOWN_1_7_10)
		.register();

		registerNoop(NetworkEntityType.SHEEP);

		new Mapping(NetworkEntityType.GOAT)
		.add(NetworkEntityType.GOAT, ProtocolVersionsHelper.UP_1_17)
		.add(NetworkEntityType.SHEEP, SheepInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_16_4)
		.register();

		new Mapping(NetworkEntityType.POLAR_BEAR)
		.add(NetworkEntityType.POLAR_BEAR, ProtocolVersionsHelper.UP_1_10)
		.add(NetworkEntityType.SPIDER, SpiderInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_9_4)
		.register();

		registerNoop(NetworkEntityType.VILLAGER);

		new Mapping(NetworkEntityType.BEE)
		.add(NetworkEntityType.BEE, ProtocolVersionsHelper.UP_1_15)
		.add(NetworkEntityType.PARROT, ParrotInitDefaultMetadataTransformer.INSTANCE, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_12, ProtocolVersion.MINECRAFT_1_14_4))
		.add(NetworkEntityType.CHICKEN, ProtocolVersionsHelper.DOWN_1_11_1)
		.register();

		register(NetworkEntityType.ENDERMAN, version -> new NetworkEntityLegacyDataEntry(NetworkEntityType.ENDERMAN, new GenericEntityDirectBlockDataMetadataTransformer(BlockDataLegacyDataRegistry.INSTANCE.getTable(version), NetworkEntityMetadataObjectIndex.Enderman.CARRIED_BLOCK)));

		registerNoop(NetworkEntityType.GIANT);

		registerNoop(NetworkEntityType.SILVERFISH);

		new Mapping(NetworkEntityType.ENDERMITE)
		.add(NetworkEntityType.ENDERMITE, ProtocolVersionsHelper.UP_1_8)
		.add(NetworkEntityType.SILVERFISH, ProtocolVersionsHelper.DOWN_1_7_10)
		.register();

		registerNoop(NetworkEntityType.ENDER_DRAGON);

		registerNoop(NetworkEntityType.SNOWMAN);

		registerNoop(NetworkEntityType.ZOMBIE);

		registerNoop(NetworkEntityType.ZOMBIE_VILLAGER);

		new Mapping(NetworkEntityType.HUSK)
		.add(NetworkEntityType.HUSK, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.ZOMBIE, ProtocolVersionsHelper.DOWN_1_10)
		.register();

		new Mapping(NetworkEntityType.DROWNED)
		.add(NetworkEntityType.DROWNED, ProtocolVersionsHelper.UP_1_13)
		.add(NetworkEntityType.ZOMBIE, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		registerNoop(NetworkEntityType.BLAZE);

		registerNoop(NetworkEntityType.SPIDER);

		registerNoop(NetworkEntityType.CAVE_SPIDER);

		registerNoop(NetworkEntityType.CREEPER);

		registerNoop(NetworkEntityType.GHAST);

		registerNoop(NetworkEntityType.SLIME);

		registerNoop(NetworkEntityType.MAGMA_CUBE);

		registerNoop(NetworkEntityType.SKELETON);

		registerNoop(NetworkEntityType.WITHER_SKELETON);

		new Mapping(NetworkEntityType.STRAY)
		.add(NetworkEntityType.STRAY, ProtocolVersionsHelper.UP_1_10)
		.add(NetworkEntityType.SKELETON, ProtocolVersionsHelper.DOWN_1_9_4)
		.register();

		registerNoop(NetworkEntityType.WITCH);

		registerNoop(NetworkEntityType.IRON_GOLEM);

		new Mapping(NetworkEntityType.SHULKER)
		.add(NetworkEntityType.SHULKER, ProtocolVersionsHelper.UP_1_9)
		.add(NetworkEntityType.BLAZE, BlazeInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_8)
		.register();

		registerNoop(NetworkEntityType.WITHER);

		new Mapping(NetworkEntityType.GUARDIAN)
		.add(NetworkEntityType.GUARDIAN, ProtocolVersionsHelper.UP_1_8)
		.add(NetworkEntityType.SQUID, ProtocolVersionsHelper.DOWN_1_7_10)
		.register();

		new Mapping(NetworkEntityType.ELDER_GUARDIAN)
		.add(NetworkEntityType.ELDER_GUARDIAN, ProtocolVersionsHelper.UP_1_8)
		.add(NetworkEntityType.SQUID, ProtocolVersionsHelper.DOWN_1_7_10)
		.register();

		new Mapping(NetworkEntityType.VINDICATOR)
		.add(NetworkEntityType.VINDICATOR, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.WITCH, WitchInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
		.register();

		new Mapping(NetworkEntityType.EVOKER)
		.add(NetworkEntityType.EVOKER, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.WITCH, WitchInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
		.register();

		new Mapping(NetworkEntityType.ILLUSIONER)
		.add(NetworkEntityType.ILLUSIONER, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.WITCH, WitchInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
		.register();

		new Mapping(NetworkEntityType.VEX)
		.add(NetworkEntityType.VEX, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.BLAZE, BlazeInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_10)
		.register();

		new Mapping(NetworkEntityType.PARROT)
		.add(NetworkEntityType.PARROT, ProtocolVersionsHelper.UP_1_12)
		.add(NetworkEntityType.CHICKEN, ProtocolVersionsHelper.DOWN_1_11_1)
		.register();

		new Mapping(NetworkEntityType.PHANTOM)
		.add(NetworkEntityType.PHANTOM, ProtocolVersionsHelper.UP_1_13)
		.add(NetworkEntityType.BLAZE, BlazeInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		new Mapping(NetworkEntityType.DOLPHIN)
		.add(NetworkEntityType.DOLPHIN, ProtocolVersionsHelper.UP_1_13)
		.add(NetworkEntityType.SPIDER, SpiderInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		new Mapping(NetworkEntityType.TURTLE)
		.add(NetworkEntityType.TURTLE, ProtocolVersionsHelper.UP_1_13)
		.add(NetworkEntityType.SPIDER, SpiderInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		new Mapping(NetworkEntityType.COD)
		.add(NetworkEntityType.COD, ProtocolVersionsHelper.UP_1_13)
		.add(NetworkEntityType.BAT, BatInitDefaultMetadataTranfromer.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		new Mapping(NetworkEntityType.SALMON)
		.add(NetworkEntityType.SALMON, ProtocolVersionsHelper.UP_1_13)
		.add(NetworkEntityType.BAT, BatInitDefaultMetadataTranfromer.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		new Mapping(NetworkEntityType.PUFFERFISH)
		.add(NetworkEntityType.PUFFERFISH, ProtocolVersionsHelper.UP_1_13)
		.add(NetworkEntityType.BAT, BatInitDefaultMetadataTranfromer.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		new Mapping(NetworkEntityType.TROPICAL_FISH)
		.add(NetworkEntityType.TROPICAL_FISH, ProtocolVersionsHelper.UP_1_13)
		.add(NetworkEntityType.BAT, BatInitDefaultMetadataTranfromer.INSTANCE, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		new Mapping(NetworkEntityType.TRADER_LAMA)
		.add(NetworkEntityType.TRADER_LAMA, ProtocolVersionsHelper.UP_1_14)
		.add(NetworkEntityType.LAMA, ProtocolVersionsHelper.RANGE__1_11__1_13_2)
		.add(NetworkEntityType.COMMON_HORSE, BattleHorseInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_10)
		.add(NetworkEntityType.COW, ProtocolVersionsHelper.DOWN_1_5_2)
		.register();

		new Mapping(NetworkEntityType.WANDERING_TRADER)
		.add(NetworkEntityType.WANDERING_TRADER, ProtocolVersionsHelper.UP_1_14)
		.add(NetworkEntityType.VILLAGER, VillagerInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_13_2)
		.register();

		new Mapping(NetworkEntityType.PANDA)
		.add(NetworkEntityType.PANDA, ProtocolVersionsHelper.UP_1_14)
		.add(NetworkEntityType.POLAR_BEAR, meta -> {
			NetworkEntityMetadataObjectByte pandaFlags = NetworkEntityMetadataObjectIndex.Panda.PANDA_FLAGS.getObject(meta);
			if (pandaFlags != null) {
				NetworkEntityMetadataObjectIndex.PolarBear.STANDING_UP.setObject(meta, new NetworkEntityMetadataObjectBoolean(BitUtils.isIBitSet(pandaFlags.getValue(), 4)));
			}
		}, ProtocolVersionsHelper.RANGE__1_10__1_13_2)
		.add(NetworkEntityType.SPIDER, SpiderInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_9_4)
		.register();

		registerNoop(NetworkEntityType.CAT);

		new Mapping(NetworkEntityType.FOX)
		.add(NetworkEntityType.FOX, ProtocolVersionsHelper.UP_1_14)
		.add(NetworkEntityType.OCELOT, ProtocolVersionsHelper.DOWN_1_13_2)
		.register();

		new Mapping(NetworkEntityType.PILLAGER)
		.add(NetworkEntityType.PILLAGER, ProtocolVersionsHelper.UP_1_14)
		.add(NetworkEntityType.WITCH, WitchInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_13_2)
		.register();

		new Mapping(NetworkEntityType.RAVAGER)
		.add(NetworkEntityType.RAVAGER, ProtocolVersionsHelper.UP_1_14)
		.add(NetworkEntityType.CAVE_SPIDER, SpiderInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_13_2)
		.register();

		new Mapping(NetworkEntityType.PIGLIN)
		.add(NetworkEntityType.PIGLIN, ProtocolVersionsHelper.UP_1_16)
		.add(NetworkEntityType.VILLAGER, VillagerInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_15_2)
		.register();

		new Mapping(NetworkEntityType.PIGLIN_BRUTE)
		.add(NetworkEntityType.PIGLIN_BRUTE, ProtocolVersionsHelper.UP_1_16_2)
		.add(NetworkEntityType.PIGLIN, ProtocolVersionsHelper.RANGE__1_16__1_16_1)
		.add(NetworkEntityType.VILLAGER, VillagerInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_15_2)
		.register();

		registerNoop(NetworkEntityType.ZOMBIFIED_PIGLIN);

		new Mapping(NetworkEntityType.HOGLIN)
		.add(NetworkEntityType.HOGLIN, ProtocolVersionsHelper.UP_1_16)
		.add(NetworkEntityType.SPIDER, SpiderInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_15_2)
		.register();

		new Mapping(NetworkEntityType.ZOGLIN)
		.add(NetworkEntityType.ZOGLIN, ProtocolVersionsHelper.UP_1_16)
		.add(NetworkEntityType.CAVE_SPIDER, SpiderInitDefaultMetadataTransformer.INSTANCE, ProtocolVersionsHelper.DOWN_1_15_2)
		.register();

		new Mapping(NetworkEntityType.STRIDER)
		.add(NetworkEntityType.STRIDER, ProtocolVersionsHelper.UP_1_16)
		.add(NetworkEntityType.PIG, meta -> {
			NetworkEntityMetadataObjectBoolean hasSaddle = NetworkEntityMetadataObjectIndex.Strider.HAS_SADDLE.getObject(meta);
			if (hasSaddle != null) {
				NetworkEntityMetadataObjectIndex.Pig.HAS_SADLLE.setObject(meta, hasSaddle);
			}
		}, ProtocolVersionsHelper.DOWN_1_15_2)
		.register();

		new Mapping(NetworkEntityType.ARMOR_STAND_MOB)
		.add(NetworkEntityType.ARMOR_STAND_MOB, ProtocolVersionsHelper.UP_1_8)
		.add(NetworkEntityLegacyDataEntry.NONE, ProtocolVersionsHelper.DOWN_1_7_10)
		.register();

		registerNoop(NetworkEntityType.BOAT);

		registerNoop(NetworkEntityType.TNT);

		registerNoop(NetworkEntityType.SNOWBALL);

		registerNoop(NetworkEntityType.EGG);

		registerNoop(NetworkEntityType.FIREBALL);

		registerNoop(NetworkEntityType.FIRECHARGE);

		registerNoop(NetworkEntityType.ENDERPEARL);

		registerNoop(NetworkEntityType.WITHER_SKULL);

		registerNoop(NetworkEntityType.FALLING_OBJECT);

		registerNoop(NetworkEntityType.ENDEREYE);

		register(NetworkEntityType.POTION, version -> new NetworkEntityLegacyDataEntry(NetworkEntityType.POTION, new GenericEntityItemMetadataTransformer(version, NetworkEntityMetadataObjectIndex.Potion.ITEM)));

		registerNoop(NetworkEntityType.EXP_BOTTLE);

		new Mapping(NetworkEntityType.LEASH_KNOT)
		.add(NetworkEntityType.LEASH_KNOT, ProtocolVersionsHelper.UP_1_6)
		.add(NetworkEntityLegacyDataEntry.NONE, ProtocolVersionsHelper.DOWN_1_15_2)
		.register();

		registerNoop(NetworkEntityType.FISHING_FLOAT);

		register(NetworkEntityType.ITEM, version -> new NetworkEntityLegacyDataEntry(NetworkEntityType.ITEM, new GenericEntityItemMetadataTransformer(version, NetworkEntityMetadataObjectIndex.Item.ITEM)));

		registerNoop(NetworkEntityType.ARROW);

		new Mapping(NetworkEntityType.SPECTRAL_ARROW)
		.add(NetworkEntityType.SPECTRAL_ARROW, ProtocolVersionsHelper.UP_1_9)
		.add(NetworkEntityType.ARROW, ProtocolVersionsHelper.DOWN_1_8)
		.register();

		new Mapping(NetworkEntityType.TIPPED_ARROW)
		.add(NetworkEntityType.TIPPED_ARROW, ProtocolVersionsHelper.UP_1_9)
		.add(NetworkEntityType.ARROW, ProtocolVersionsHelper.DOWN_1_8)
		.register();

		new Mapping(NetworkEntityType.THROWN_TRIDENT)
		.add(NetworkEntityType.THROWN_TRIDENT, ProtocolVersionsHelper.UP_1_13)
		.add(NetworkEntityType.ARROW, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		register(NetworkEntityType.FIREWORK, version -> new NetworkEntityLegacyDataEntry(NetworkEntityType.FIREWORK, new GenericEntityItemMetadataTransformer(version, NetworkEntityMetadataObjectIndex.Firework.ITEM)));

		register(NetworkEntityType.ITEM_FRAME, version -> new NetworkEntityLegacyDataEntry(NetworkEntityType.ITEM_FRAME, new GenericEntityItemMetadataTransformer(version, NetworkEntityMetadataObjectIndex.ItemFrame.ITEM)));

		new Mapping(NetworkEntityType.GLOW_ITEM_FRAME)
		.add(NetworkEntityType.GLOW_ITEM_FRAME, (Function<ProtocolVersion, Consumer<ArrayMap<NetworkEntityMetadataObject<?>>>>) version -> new GenericEntityItemMetadataTransformer(version, NetworkEntityMetadataObjectIndex.ItemFrame.ITEM), ProtocolVersionsHelper.UP_1_17)
		.add(NetworkEntityType.ITEM_FRAME, (Function<ProtocolVersion, Consumer<ArrayMap<NetworkEntityMetadataObject<?>>>>) version -> new GenericEntityItemMetadataTransformer(version, NetworkEntityMetadataObjectIndex.ItemFrame.ITEM), ProtocolVersionsHelper.DOWN_1_16_4)
		.register();

		registerNoop(NetworkEntityType.ENDER_CRYSTAL);

		new Mapping(NetworkEntityType.AREA_EFFECT_CLOUD)
		.add(NetworkEntityType.AREA_EFFECT_CLOUD, (Function<ProtocolVersion, Consumer<ArrayMap<NetworkEntityMetadataObject<?>>>>) version -> new GenericEntityParticleMetadataTransformer(NetworkParticleLegacyData.REGISTRY.getTable(version), NetworkEntityMetadataObjectIndex.AreaEffectCloud.PARTICLE), ProtocolVersionsHelper.UP_1_9)
		.add(NetworkEntityLegacyDataEntry.NONE, ProtocolVersionsHelper.DOWN_1_8)
		.register();

		new Mapping(NetworkEntityType.SHULKER_BULLET)
		.add(NetworkEntityType.SHULKER_BULLET, ProtocolVersionsHelper.UP_1_9)
		.add(NetworkEntityType.FIRECHARGE, ProtocolVersionsHelper.DOWN_1_8)
		.register();

		new Mapping(NetworkEntityType.LAMA_SPIT)
		.add(NetworkEntityType.LAMA_SPIT, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.SNOWBALL, ProtocolVersionsHelper.DOWN_1_10)
		.register();

		new Mapping(NetworkEntityType.DRAGON_FIREBALL)
		.add(NetworkEntityType.DRAGON_FIREBALL, ProtocolVersionsHelper.UP_1_9)
		.add(NetworkEntityType.FIRECHARGE, ProtocolVersionsHelper.DOWN_1_8)
		.register();

		new Mapping(NetworkEntityType.EVOCATOR_FANGS)
		.add(NetworkEntityType.EVOCATOR_FANGS, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.FIRECHARGE, ProtocolVersionsHelper.DOWN_1_10)
		.register();

		new Mapping(NetworkEntityType.ARMOR_STAND_OBJECT)
		.add(NetworkEntityType.ARMOR_STAND_OBJECT, ProtocolVersionsHelper.UP_1_8)
		.add(NetworkEntityLegacyDataEntry.NONE, ProtocolVersionsHelper.DOWN_1_7_10)
		.register();

		register(NetworkEntityType.MINECART, version -> new NetworkEntityLegacyDataEntry(NetworkEntityType.MINECART, new GenericEntityVarIntBlockDataMetadataTransformer(BlockDataLegacyDataRegistry.INSTANCE.getTable(version), NetworkEntityMetadataObjectIndex.Minecart.BLOCK)));

		registerNoop(NetworkEntityType.MINECART_CHEST);

		registerNoop(NetworkEntityType.MINECART_FURNACE);

		registerNoop(NetworkEntityType.MINECART_TNT);

		registerNoop(NetworkEntityType.MINECART_MOB_SPAWNER);

		registerNoop(NetworkEntityType.MINECART_HOPPER);

		registerNoop(NetworkEntityType.MINECART_COMMAND);

		registerNoop(NetworkEntityType.THUNDERBOLT);
	}

	protected final class Mapping {

		protected final NetworkEntityType type;
		protected final ArrayList<MappingEntry> mappings = new ArrayList<>();

		protected class MappingEntry {

			public final NetworkEntityLegacyDataEntry entity;
			public final ProtocolVersion[] versions;

			public MappingEntry(NetworkEntityLegacyDataEntry entity, ProtocolVersion[] versions) {
				this.entity = entity;
				this.versions = versions;
			}

		}

		public Mapping(NetworkEntityType from) {
			this.type = from;
		}

		public Mapping add(NetworkEntityType type, ProtocolVersion... versions) {
			return add(new NetworkEntityLegacyDataEntry(type), versions);
		}

		public Mapping add(NetworkEntityType type, Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> transformer, ProtocolVersion... versions) {
			return add(new NetworkEntityLegacyDataEntry(type, transformer), versions);
		}

		public Mapping add(NetworkEntityType type, Function<ProtocolVersion, Consumer<ArrayMap<NetworkEntityMetadataObject<?>>>> transformerSupplier, ProtocolVersion... versions) {
			for (ProtocolVersion version : versions) {
				add(type, transformerSupplier.apply(version), version);
			}
			return this;
		}

		public Mapping add(NetworkEntityLegacyDataEntry entity, ProtocolVersion... versions) {
			mappings.add(new MappingEntry(entity, versions));
			return this;
		}

		public void register() {
			for (MappingEntry mapping : mappings) {
				NetworkEntityLegacyDataRegistry.this.register(type, mapping.entity, mapping.versions);
			}
		}
	}

	protected void registerNoop(NetworkEntityType type) {
		register(type, new NetworkEntityLegacyDataEntry(type), ProtocolVersionsHelper.ALL_PC);
	}

	protected void register(NetworkEntityType type, Function<ProtocolVersion, NetworkEntityLegacyDataEntry> function, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			getTable(version).set(type, function.apply(version));
		}
	}

	protected void register(NetworkEntityType type, Function<ProtocolVersion, NetworkEntityLegacyDataEntry> function) {
		register(type, function, ProtocolVersionsHelper.ALL_PC);
	}

	protected void register(NetworkEntityType type, NetworkEntityLegacyDataEntry entry, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			getTable(version).set(type, entry);
		}
	}

	@Override
	protected NetworkEntityLegacyDataTable createTable() {
		return new NetworkEntityLegacyDataTable();
	}

	public static class NetworkEntityLegacyDataTable extends MappingTable {

		protected final Map<NetworkEntityType, NetworkEntityLegacyDataEntry> table = new EnumMap<>(NetworkEntityType.class);

		public void set(NetworkEntityType type, NetworkEntityLegacyDataEntry entry) {
			table.put(type, entry);
		}

		public NetworkEntityLegacyDataEntry get(NetworkEntityType type) {
			return table.get(type);
		}

	}

	public static class NetworkEntityLegacyDataEntry {

		public static final NetworkEntityLegacyDataEntry NONE = new NetworkEntityLegacyDataEntry(NetworkEntityType.NONE);

		protected final NetworkEntityType type;
		protected final Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> metaTransformer;

		public NetworkEntityLegacyDataEntry(NetworkEntityType type) {
			this(type, NoopMetadataTransformer.INSTANCE);
		}

		public NetworkEntityLegacyDataEntry(NetworkEntityType type, Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> metaTransformer) {
			this.type = type;
			this.metaTransformer = metaTransformer;
		}

		public NetworkEntityType getType() {
			return type;
		}

		public void transformMetadata(ArrayMap<NetworkEntityMetadataObject<?>> metadata) {
			metaTransformer.accept(metadata);
		}

	}

}
