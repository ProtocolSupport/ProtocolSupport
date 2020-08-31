package protocolsupport.protocol.typeremapper.window;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.netcache.window.WindowEnchantmentCache;
import protocolsupport.protocol.storage.netcache.window.WindowFurnaceCache;
import protocolsupport.protocol.typeremapper.window.WindowsRemapper.NonSlotCountBasedRemapperSelectFunction;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class WindowsRemappersRegistry {

	protected static final Map<ProtocolVersion, WindowsRemapper> registry = new EnumMap<>(ProtocolVersion.class);

	protected static void register(WindowsRemapper remapper, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			registry.put(version, remapper);
		}
	}

	static {
		WindowsRemapper remapper__noop = new WindowsRemapper(
			Pair.of(WindowType.GENERIC_9X1, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.GENERIC_9X2, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.GENERIC_9X3, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.GENERIC_9X4, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.GENERIC_9X5, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.GENERIC_9X6, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.GENERIC_3X3, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.ANVIL, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.BEACON, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.BLAST_FURNACE, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.BREWING_STAND, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.CRAFTING, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.ENCHANTMENT, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.FURNACE, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.GRINDSTONE, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.HOPPER, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.LECTERN, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.LOOM, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.MERCHANT, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.SHULKER_BOX, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.SMITHING, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.SMOKER, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.CARTOGRAPHY, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.STONECUTTER, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.HORSE, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.PLAYER, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE))
		);
		register(remapper__noop, ProtocolVersionsHelper.UP_1_16);

		WindowsRemapper remapper__14__15_2 = new WindowsRemapper(
			remapper__noop,
			Pair.of(WindowType.SMITHING, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.ANVIL)))
		);
		register(remapper__14__15_2, ProtocolVersionsHelper.RANGE__1_14__1_15_2);

		WindowsRemapper remapper__11__13_2 = new WindowsRemapper(
			Pair.of(WindowType.PLAYER, new NonSlotCountBasedRemapperSelectFunction(NoopWindowRemapper.INSTANCE)),
			Pair.of(WindowType.CRAFTING, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.CRAFTING, 0))),
			Pair.of(WindowType.ANVIL, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.ANVIL, 0))),
			Pair.of(WindowType.SMITHING, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.ANVIL, 0))),
			Pair.of(WindowType.ENCHANTMENT, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.ENCHANTMENT, 0))),
			Pair.of(WindowType.BREWING_STAND, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.BREWING_STAND, 5))),
			Pair.of(WindowType.BEACON, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.BEACON, 1))),
			Pair.of(WindowType.HOPPER, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.HOPPER, 5))),
			Pair.of(WindowType.MERCHANT, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.MERCHANT, 3))),
			Pair.of(WindowType.FURNACE, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.FURNACE, 3))),
			Pair.of(WindowType.BLAST_FURNACE, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.FURNACE, 3))),
			Pair.of(WindowType.SMOKER, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.FURNACE, 3))),
			Pair.of(WindowType.GENERIC_3X3, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.GENERIC_3X3, 9))),
			Pair.of(WindowType.SHULKER_BOX, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.SHULKER_BOX, 27))),
			Pair.of(WindowType.GENERIC_9X1, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.GENERIC_9X1, 9))),
			Pair.of(WindowType.GENERIC_9X2, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.GENERIC_9X2, 18))),
			Pair.of(WindowType.GENERIC_9X3, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.GENERIC_9X3, 27))),
			Pair.of(WindowType.GENERIC_9X4, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.GENERIC_9X4, 36))),
			Pair.of(WindowType.GENERIC_9X5, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.GENERIC_9X5, 45))),
			Pair.of(WindowType.GENERIC_9X6, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.GENERIC_9X6, 54))),
			Pair.of(WindowType.HORSE, slots -> new NoopWindowRemapper(WindowType.HORSE, slots))
		);
		register(remapper__11__13_2, ProtocolVersionsHelper.RANGE__1_11__1_13_2);

		WindowsRemapper remapper__10 = new WindowsRemapper(
			remapper__11__13_2,
			Pair.of(WindowType.SHULKER_BOX, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.GENERIC_9X3, 27)))
		);
		register(remapper__10, ProtocolVersion.MINECRAFT_1_10);

		WindowsRemapper remapper__9 = new WindowsRemapper(
			remapper__10,
			Pair.of(WindowType.ENCHANTMENT, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.ENCHANTMENT, 0, WindowEnchantmentCache::new)))
		);
		register(remapper__9, ProtocolVersionsHelper.ALL_1_9);

		WindowsRemapper remapper__8 = new WindowsRemapper(
			remapper__9,
			Pair.of(WindowType.PLAYER, new NonSlotCountBasedRemapperSelectFunction(new SingleWindowIdRemapper(WindowType.PLAYER, 0) {
				@Override
				public int toClientSlot(int slot) {
					if (slot < 45) {
						return slot;
					} else {
						throw SlotDoesntExistException.INSTANCE;
					}
				}
				@Override
				public int fromClientSlot(byte windowId, int slot) {
					return slot;
				}
				@Override
				protected void fillClientItems(ClientItems instance, NetworkItemStack[] content) {
					instance.items = Arrays.copyOf(content, content.length - 1);
				}
			})),
			Pair.of(WindowType.BREWING_STAND, new NonSlotCountBasedRemapperSelectFunction(new SingleWindowIdSkipSlotRemapper(WindowType.BREWING_STAND, 4, 4)))
		);
		register(remapper__8, ProtocolVersion.MINECRAFT_1_8);

		WindowsRemapper remapper__pre_8 = new WindowsRemapper(
			remapper__8,
			Pair.of(WindowType.ENCHANTMENT, new NonSlotCountBasedRemapperSelectFunction(new SingleWindowIdSkipSlotRemapper(WindowType.ENCHANTMENT, 0, 1))),
			Pair.of(WindowType.FURNACE, new NonSlotCountBasedRemapperSelectFunction(new NoopWindowRemapper(WindowType.FURNACE, 3, WindowFurnaceCache::new)))
		);
		register(remapper__pre_8, ProtocolVersionsHelper.DOWN_1_7_10);
	}

	public static WindowsRemapper get(ProtocolVersion version) {
		return registry.get(version);
	}

}
