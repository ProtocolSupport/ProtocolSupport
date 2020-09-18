package protocolsupport.protocol.typeremapper.entity;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.LegacyNetworkEntityRegistry.LegacyNetworkEntityTable;
import protocolsupport.protocol.typeremapper.utils.MappingRegistry;
import protocolsupport.protocol.typeremapper.utils.MappingTable;
import protocolsupport.protocol.types.VillagerData;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.BitUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyNetworkEntityRegistry extends MappingRegistry<LegacyNetworkEntityTable> {

	public static final LegacyNetworkEntityRegistry INSTANCE = new LegacyNetworkEntityRegistry();

	protected LegacyNetworkEntityRegistry() {
		registerNoop(NetworkEntityType.PLAYER);

		registerNoop(NetworkEntityType.EXP_ORB);

		registerNoop(NetworkEntityType.PAINTING);

		registerNoop(NetworkEntityType.COW);

		registerNoop(NetworkEntityType.MUSHROOM_COW);

		registerNoop(NetworkEntityType.CHICKEN);

		registerNoop(NetworkEntityType.SQUID);

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

		registerNoop(NetworkEntityType.ENDERMAN);

		registerNoop(NetworkEntityType.GIANT);

		registerNoop(NetworkEntityType.SILVERFISH);

		new Mapping(NetworkEntityType.ENDERMITE)
		.add(NetworkEntityType.ENDERMITE, ProtocolVersionsHelper.UP_1_8)
		.add(NetworkEntityType.SILVERFISH, ProtocolVersionsHelper.DOWN_1_7_10)
		.register();

		registerNoop(NetworkEntityType.ENDER_DRAGON);

		registerNoop(NetworkEntityType.SNOWMAN);

		registerNoop(NetworkEntityType.ZOMBIE);

		//TODO: zombie villager did exist, but it was a meta flag on zombie type before, so this should be noop
		new Mapping(NetworkEntityType.ZOMBIE_VILLAGER)
		.add(NetworkEntityType.ZOMBIE_VILLAGER, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.ZOMBIE, ProtocolVersionsHelper.DOWN_1_10)
		.register();

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

		//TODO elder guardian did exist, but was a meta flag, so it shouldn't map to guardian, and the legacy format should take care of that instead
		new Mapping(NetworkEntityType.ELDER_GUARDIAN)
		.add(NetworkEntityType.ELDER_GUARDIAN, ProtocolVersionsHelper.UP_1_11)
		.add(NetworkEntityType.GUARDIAN, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_8))
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
		.add(NetworkEntityType.SQUID, ProtocolVersionsHelper.DOWN_1_12_2)
		.register();

		new Mapping(NetworkEntityType.TURTLE)
		.add(NetworkEntityType.TURTLE, ProtocolVersionsHelper.UP_1_13)
		.add(NetworkEntityType.SQUID, ProtocolVersionsHelper.DOWN_1_12_2)
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

		//TODO: technically did exists, just it was ocelot with special metadata, so it should be noop
		new Mapping(NetworkEntityType.CAT)
		.add(NetworkEntityType.CAT, ProtocolVersionsHelper.UP_1_14)
		.add(NetworkEntityType.OCELOT, ProtocolVersionsHelper.DOWN_1_13_2)
		.register();

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
		.add(LegacyNetworkEntityEntry.NONE, ProtocolVersionsHelper.DOWN_1_7_10)
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

		registerNoop(NetworkEntityType.POTION);

		registerNoop(NetworkEntityType.EXP_BOTTLE);

		new Mapping(NetworkEntityType.LEASH_KNOT)
		.add(NetworkEntityType.LEASH_KNOT, ProtocolVersionsHelper.UP_1_6)
		.add(LegacyNetworkEntityEntry.NONE, ProtocolVersionsHelper.DOWN_1_15_2)
		.register();

		registerNoop(NetworkEntityType.FISHING_FLOAT);

		registerNoop(NetworkEntityType.ITEM);

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

		registerNoop(NetworkEntityType.FIREWORK);

		registerNoop(NetworkEntityType.ITEM_FRAME);

		registerNoop(NetworkEntityType.ENDER_CRYSTAL);

		new Mapping(NetworkEntityType.AREA_EFFECT_CLOUD)
		.add(NetworkEntityType.AREA_EFFECT_CLOUD, ProtocolVersionsHelper.UP_1_9)
		.add(LegacyNetworkEntityEntry.NONE, ProtocolVersionsHelper.DOWN_1_8)
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
		.add(LegacyNetworkEntityEntry.NONE, ProtocolVersionsHelper.DOWN_1_7_10)
		.register();

		registerNoop(NetworkEntityType.MINECART);

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

			public final LegacyNetworkEntityEntry entity;
			public final ProtocolVersion[] versions;

			public MappingEntry(LegacyNetworkEntityEntry entity, ProtocolVersion[] versions) {
				this.entity = entity;
				this.versions = versions;
			}

		}

		public Mapping(NetworkEntityType from) {
			this.type = from;
		}

		public Mapping add(NetworkEntityType type, ProtocolVersion... versions) {
			return add(new LegacyNetworkEntityEntry(type), versions);
		}

		public Mapping add(NetworkEntityType type, Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> metaTransformer, ProtocolVersion... versions) {
			return add(new LegacyNetworkEntityEntry(type, metaTransformer), versions);
		}

		public Mapping add(LegacyNetworkEntityEntry entity, ProtocolVersion... versions) {
			mappings.add(new MappingEntry(entity, versions));
			return this;
		}

		public void register() {
			for (MappingEntry mapping : mappings) {
				LegacyNetworkEntityRegistry.this.register(type, mapping.entity, mapping.versions);
			}
		}
	}

	protected void registerNoop(NetworkEntityType type) {
		register(type, new LegacyNetworkEntityEntry(type), ProtocolVersionsHelper.ALL_PC);
	}

	protected void register(NetworkEntityType type, LegacyNetworkEntityEntry entry, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			getTable(version).set(type, entry);
		}
	}

	@Override
	protected LegacyNetworkEntityTable createTable() {
		return new LegacyNetworkEntityTable();
	}

	public static class LegacyNetworkEntityTable extends MappingTable {

		protected final Map<NetworkEntityType, LegacyNetworkEntityEntry> table = new EnumMap<>(NetworkEntityType.class);

		public void set(NetworkEntityType type, LegacyNetworkEntityEntry entry) {
			table.put(type, entry);
		}

		public LegacyNetworkEntityEntry get(NetworkEntityType type) {
			return table.get(type);
		}

	}

	public static class LegacyNetworkEntityEntry {

		public static final LegacyNetworkEntityEntry NONE = new LegacyNetworkEntityEntry(NetworkEntityType.NONE);

		protected final NetworkEntityType type;
		protected final Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> metaTransformer;

		public LegacyNetworkEntityEntry(NetworkEntityType type) {
			this(type, NoopMetadataTransformer.INSTANCE);
		}

		public LegacyNetworkEntityEntry(NetworkEntityType type, Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> metaTransformer) {
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

	public static class NoopMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

		public static final NoopMetadataTransformer INSTANCE = new NoopMetadataTransformer();

		@Override
		public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
		}

	}

	public static class BatInitDefaultMetadataTranfromer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

		public static final BatInitDefaultMetadataTranfromer INSTANCE = new BatInitDefaultMetadataTranfromer();

		@Override
		public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
			NetworkEntityMetadataObjectIndex.Bat.HANGING.setObject(t, new NetworkEntityMetadataObjectByte((byte) 0));
		}

	}

	public static class SpiderInitDefaultMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

		public static final SpiderInitDefaultMetadataTransformer INSTANCE = new SpiderInitDefaultMetadataTransformer();

		@Override
		public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
			NetworkEntityMetadataObjectIndex.Spider.CLIMBING.setObject(t, new NetworkEntityMetadataObjectByte((byte) 0));
		}

	}

	public static class ParrotInitDefaultMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

		public static final ParrotInitDefaultMetadataTransformer INSTANCE = new ParrotInitDefaultMetadataTransformer();

		@Override
		public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
			NetworkEntityMetadataObjectIndex.Parrot.VARIANT.setObject(t, new NetworkEntityMetadataObjectVarInt(0));
		}

	}

	public static class BlazeInitDefaultMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

		public static final BlazeInitDefaultMetadataTransformer INSTANCE = new BlazeInitDefaultMetadataTransformer();

		@Override
		public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
			NetworkEntityMetadataObjectIndex.Blaze.ON_FIRE.setObject(t, new NetworkEntityMetadataObjectByte((byte) 0));
		}

	}

	public static class WitchInitDefaultMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

		public static final WitchInitDefaultMetadataTransformer INSTANCE = new WitchInitDefaultMetadataTransformer();

		@Override
		public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
			NetworkEntityMetadataObjectIndex.Witch.DRINKING_POTION.setObject(t, new NetworkEntityMetadataObjectBoolean(false));
		}

	}

	public static class VillagerInitDefaultMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

		public static final VillagerInitDefaultMetadataTransformer INSTANCE = new VillagerInitDefaultMetadataTransformer();

		@Override
		public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
			NetworkEntityMetadataObjectIndex.Villager.VDATA.setObject(t, new NetworkEntityMetadataObjectVillagerData(new VillagerData(0, 0, 0)));
		}

	}

	public static class BattleHorseInitDefaultMetadataTransformer implements Consumer<ArrayMap<NetworkEntityMetadataObject<?>>> {

		public static final BattleHorseInitDefaultMetadataTransformer INSTANCE = new BattleHorseInitDefaultMetadataTransformer();

		@Override
		public void accept(ArrayMap<NetworkEntityMetadataObject<?>> t) {
			NetworkEntityMetadataObjectIndex.BattleHorse.VARIANT.setObject(t, new NetworkEntityMetadataObjectVarInt(0));
			NetworkEntityMetadataObjectIndex.BattleHorse.ARMOR.setObject(t, new NetworkEntityMetadataObjectVarInt(0));
		}

	}

}
