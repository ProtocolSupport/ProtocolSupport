package protocolsupport.protocol.typeremapper.id;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.EnumRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.EnumRemappingTable;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.WindowType;

public class IdRemapper {



	public static final EnumRemappingRegistry<NetworkEntityType, EnumRemappingTable<NetworkEntityType>> ENTITY = new EnumRemappingRegistry<NetworkEntityType, EnumRemappingTable<NetworkEntityType>>() {
		final class Mapping {
			private final NetworkEntityType from;
			private final ArrayList<ImmutablePair<NetworkEntityType, ProtocolVersion[]>> remaps = new ArrayList<>();
			public Mapping(NetworkEntityType from) {
				this.from = from;
			}
			public Mapping addMapping(NetworkEntityType to, ProtocolVersion... versions) {
				remaps.add(ImmutablePair.of(to, versions));
				return this;
			}
			public void register() {
				for (ImmutablePair<NetworkEntityType, ProtocolVersion[]> pair : remaps) {
					registerRemapEntry(from, pair.getLeft(), pair.getRight());
				}
			}
		}
		{
			new Mapping(NetworkEntityType.PHANTOM)
			.addMapping(NetworkEntityType.BLAZE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.DOLPHIN)
			.addMapping(NetworkEntityType.SQUID, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.TURTLE)
			.addMapping(NetworkEntityType.SQUID, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.COD)
			.addMapping(NetworkEntityType.BAT, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.PUFFERFISH)
			.addMapping(NetworkEntityType.BAT, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.SALMON)
			.addMapping(NetworkEntityType.BAT, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.TROPICAL_FISH)
			.addMapping(NetworkEntityType.BAT, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.DROWNED)
			.addMapping(NetworkEntityType.ZOMBIE, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.THROWN_TRIDENT)
			.addMapping(NetworkEntityType.ARROW, ProtocolVersionsHelper.BEFORE_1_13)
			.register();
			new Mapping(NetworkEntityType.PARROT)
			.addMapping(NetworkEntityType.OCELOT, ProtocolVersionsHelper.BEFORE_1_12)
			.register();
			new Mapping(NetworkEntityType.ILLUSIONER)
			.addMapping(NetworkEntityType.WITCH, ProtocolVersionsHelper.BEFORE_1_12)
			.register();
			new Mapping(NetworkEntityType.VINDICATOR)
			.addMapping(NetworkEntityType.WITCH, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.EVOKER)
			.addMapping(NetworkEntityType.WITCH, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.VEX)
			.addMapping(NetworkEntityType.BLAZE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.ZOMBIE_VILLAGER)
			.addMapping(NetworkEntityType.ZOMBIE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.HUSK)
			.addMapping(NetworkEntityType.ZOMBIE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.EVOCATOR_FANGS)
			.addMapping(NetworkEntityType.FIRECHARGE, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.LAMA_SPIT)
			.addMapping(NetworkEntityType.SNOWBALL, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.SKELETON_HORSE)
			.addMapping(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.ZOMBIE_HORSE)
			.addMapping(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.DONKEY)
			.addMapping(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.MULE)
			.addMapping(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.LAMA)
			.addMapping(NetworkEntityType.COMMON_HORSE, ProtocolVersionsHelper.RANGE__1_6__1_10)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
			new Mapping(NetworkEntityType.WITHER_SKELETON)
			.addMapping(NetworkEntityType.SKELETON, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.STRAY)
			.addMapping(NetworkEntityType.SKELETON, ProtocolVersionsHelper.BEFORE_1_11)
			.register();
			new Mapping(NetworkEntityType.POLAR_BEAR)
			.addMapping(NetworkEntityType.SPIDER, ProtocolVersionsHelper.BEFORE_1_10)
			.register();
			new Mapping(NetworkEntityType.SHULKER)
			.addMapping(NetworkEntityType.BLAZE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.SHULKER_BULLET)
			.addMapping(NetworkEntityType.FIRECHARGE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.DRAGON_FIREBALL)
			.addMapping(NetworkEntityType.FIRECHARGE, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.SPECTRAL_ARROW)
			.addMapping(NetworkEntityType.ARROW, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.TIPPED_ARROW)
			.addMapping(NetworkEntityType.ARROW, ProtocolVersionsHelper.BEFORE_1_9)
			.register();
			new Mapping(NetworkEntityType.ENDERMITE)
			.addMapping(NetworkEntityType.SILVERFISH, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.RABBIT)
			.addMapping(NetworkEntityType.CHICKEN, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.ELDER_GUARDIAN)
			.addMapping(NetworkEntityType.GUARDIAN, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_10))
			.addMapping(NetworkEntityType.SQUID, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.GUARDIAN)
			.addMapping(NetworkEntityType.SQUID, ProtocolVersionsHelper.BEFORE_1_8)
			.register();
			new Mapping(NetworkEntityType.COMMON_HORSE)
			.addMapping(NetworkEntityType.COW, ProtocolVersionsHelper.BEFORE_1_6)
			.register();
		}
		@Override
		protected EnumRemappingTable<NetworkEntityType> createTable() {
			return new EnumRemappingTable<>(NetworkEntityType.class);
		}
	};

	public static final EnumRemappingRegistry<WindowType, EnumRemappingTable<WindowType>> INVENTORY = new EnumRemappingRegistry<WindowType, EnumRemappingTable<WindowType>>() {
		{
			registerRemapEntry(WindowType.SHULKER, WindowType.CHEST, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(WindowType.DROPPER, WindowType.DISPENSER, ProtocolVersionsHelper.BEFORE_1_5);
		}
		@Override
		protected EnumRemappingTable<WindowType> createTable() {
			return new EnumRemappingTable<>(WindowType.class);
		}
	};

}
