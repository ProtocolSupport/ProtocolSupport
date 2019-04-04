package protocolsupport.protocol.typeremapper.itemstack;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.RemappingRegistry.IdRemappingRegistry;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyItemType {

	public static final ItemIdRemappingRegistry REGISTRY = new ItemIdRemappingRegistry();

	public static class ItemIdRemappingRegistry extends IdRemappingRegistry<ArrayBasedIdRemappingTable> {
		{
			applyDefaultRemaps();
		}
		public void applyDefaultRemaps() {

			//TODO: actually adapt the legacyblocktype registry instead of extracting data from it
			Arrays.stream(Material.values())
			.filter(m -> m.isItem() && m.isBlock())
			.forEach(materialFrom -> {
				int networkIdFrom = MaterialAPI.getBlockDataNetworkId(materialFrom.createBlockData());
				Arrays.stream(ProtocolVersion.getAllSupported())
				.forEach(version -> {
					Material materialTo = MaterialAPI.getBlockDataByNetworkId(LegacyBlockData.REGISTRY.getTable(version).getRemap(networkIdFrom)).getMaterial();
					if (!materialFrom.equals(materialTo) && !materialTo.equals(Material.AIR) && materialTo.isItem()) {
						registerRemapEntry(materialFrom, materialTo, version);
					}
				});
			});


			registerRemapEntry(Material.CHEST, Material.CHEST, ProtocolVersionsHelper.ALL_PC);
			registerRemapEntry(Material.TRAPPED_CHEST, Material.TRAPPED_CHEST, ProtocolVersionsHelper.ALL_PC);


			registerRemapEntry(Arrays.asList(Material.ACACIA_WOOD, Material.STRIPPED_ACACIA_WOOD), Material.ACACIA_LOG, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Arrays.asList(Material.DARK_OAK_WOOD, Material.STRIPPED_DARK_OAK_WOOD), Material.DARK_OAK_LOG, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Arrays.asList(Material.BIRCH_WOOD, Material.STRIPPED_BIRCH_WOOD), Material.BIRCH_LOG, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Arrays.asList(Material.JUNGLE_WOOD, Material.STRIPPED_JUNGLE_WOOD), Material.JUNGLE_LOG, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Arrays.asList(Material.SPRUCE_WOOD, Material.STRIPPED_SPRUCE_WOOD), Material.SPRUCE_LOG, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Arrays.asList(Material.OAK_WOOD, Material.STRIPPED_OAK_WOOD), Material.OAK_LOG, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.KELP, Material.GRASS, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.DRIED_KELP, Material.POTATO, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(
				Arrays.asList(Material.COD_BUCKET, Material.PUFFERFISH_BUCKET, Material.SALMON_BUCKET, Material.TROPICAL_FISH_BUCKET),
				Material.WATER_BUCKET,
				ProtocolVersionsHelper.BEFORE_1_13
			);
			registerRemapEntry(Material.HEART_OF_THE_SEA, Material.LIGHT_BLUE_DYE, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.NAUTILUS_SHELL, Material.LIGHT_GRAY_DYE, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.PHANTOM_MEMBRANE, Material.GRAY_DYE, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.SCUTE, Material.LIME_DYE, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.TURTLE_HELMET, Material.CHAINMAIL_HELMET, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.TRIDENT, Material.DIAMOND_HOE, ProtocolVersionsHelper.BEFORE_1_13);
			registerRemapEntry(Material.DEBUG_STICK, Material.STICK, ProtocolVersionsHelper.BEFORE_1_13);


			registerRemapEntry(Material.KNOWLEDGE_BOOK, Material.BOOK, ProtocolVersionsHelper.BEFORE_1_12);
			registerRemapEntry(Material.IRON_NUGGET, Material.GOLD_NUGGET, ProtocolVersionsHelper.BEFORE_1_11_1);
			registerRemapEntry(Material.SHULKER_SHELL, Material.COBBLESTONE, ProtocolVersionsHelper.BEFORE_1_11);
			registerRemapEntry(Material.TOTEM_OF_UNDYING, Material.COBBLESTONE, ProtocolVersionsHelper.BEFORE_1_11);


			registerRemapEntry(Material.BEETROOT, Material.BROWN_MUSHROOM, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_SOUP, Material.MUSHROOM_STEW, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_SEEDS, Material.WHEAT_SEEDS, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_FRUIT, Material.POTATO, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.POPPED_CHORUS_FRUIT, Material.BAKED_POTATO, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.DRAGON_BREATH, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPLASH_POTION, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.LINGERING_POTION, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.ELYTRA, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_CRYSTAL, Material.STONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SHIELD, Material.WOODEN_SWORD, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(
				Arrays.asList(Material.SPECTRAL_ARROW, Material.TIPPED_ARROW),
				Material.ARROW,
				ProtocolVersionsHelper.BEFORE_1_9
			);
			registerRemapEntry(
				Arrays.asList(
					Material.ACACIA_BOAT, Material.BIRCH_BOAT, Material.DARK_OAK_BOAT,
					Material.JUNGLE_BOAT, Material.SPRUCE_BOAT
				),
				Material.OAK_BOAT,
				ProtocolVersionsHelper.BEFORE_1_9
			);


			registerRemapEntry(Material.RABBIT, Material.CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.COOKED_RABBIT, Material.COOKED_CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT_STEW, Material.MUSHROOM_STEW, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.MUTTON, Material.CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.COOKED_MUTTON, Material.COOKED_CHICKEN, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.PRISMARINE_SHARD, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.PRISMARINE_CRYSTALS, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT_FOOT, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.RABBIT_HIDE, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(Material.ARMOR_STAND, Material.STONE, ProtocolVersionsHelper.BEFORE_1_8);

			registerRemapEntry(
				Arrays.asList(
					Material.ACACIA_WOOD, Material.STRIPPED_ACACIA_WOOD,
					Material.DARK_OAK_WOOD, Material.STRIPPED_DARK_OAK_WOOD
				),
				Material.OAK_LOG,
				ProtocolVersionsHelper.BEFORE_1_7
			);

			registerRemapEntry(Material.IRON_HORSE_ARMOR, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.GOLDEN_HORSE_ARMOR, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.DIAMOND_HORSE_ARMOR, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.LEAD, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(Material.NAME_TAG, Material.STONE, ProtocolVersionsHelper.BEFORE_1_6);


			registerRemapEntry(Material.QUARTZ, Material.FEATHER, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.TNT_MINECART, Material.MINECART, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.HOPPER_MINECART, Material.MINECART, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.NETHER_BRICK, Material.CLAY_BALL, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(Material.TRAPPED_CHEST, Material.CHEST, ProtocolVersionsHelper.BEFORE_1_5);
		}

		protected void registerRemapEntry(List<Material> from, Material to, ProtocolVersion... versions) {
			from.forEach(material -> registerRemapEntry(material, to, versions));
		}

		protected void registerRemapEntry(Material from, Material to, ProtocolVersion... versions) {
			registerRemapEntry(ItemMaterialLookup.getRuntimeId(from), ItemMaterialLookup.getRuntimeId(to), versions);
		}

		@Override
		protected ArrayBasedIdRemappingTable createTable() {
			return new ArrayBasedIdRemappingTable(MinecraftData.ITEM_COUNT);
		}

	}

}
