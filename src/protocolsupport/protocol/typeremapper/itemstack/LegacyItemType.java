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

			registerRemapEntry(Material.MAP, Material.FILLED_MAP, ProtocolVersionsHelper.BEFORE_1_5);
//			360 	
//			Melon
//		(minecraft:melon)
//		361 	
//			Pumpkin Seeds
//		(minecraft:pumpkin_seeds)
//		362 	
//			Melon Seeds
//		(minecraft:melon_seeds)
//		363 	
//			Raw Beef
//		(minecraft:beef)
//		364 	
//			Steak
//		(minecraft:cooked_beef)
//		365 	
//			Raw Chicken
//		(minecraft:chicken)
//		366 	
//			Cooked Chicken
//		(minecraft:cooked_chicken)
//		367 	
//			Rotten Flesh
//		(minecraft:rotten_flesh)
//		368 	
//			Ender Pearl
//		(minecraft:ender_pearl)
//		369 	
//			Blaze Rod
//		(minecraft:blaze_rod)
//		370 	
//			Ghast Tear
//		(minecraft:ghast_tear)
//		371 	
//			Gold Nugget
//		(minecraft:gold_nugget)
//		372 	
//			Nether Wart
//		(minecraft:nether_wart)
//		373 	
//			Potion
//		(minecraft:potion)
//		374 	
//			Glass Bottle
//		(minecraft:glass_bottle)
//		375 	
//			Spider Eye
//		(minecraft:spider_eye)
//		376 	
//			Fermented Spider Eye
//		(minecraft:fermented_spider_eye)
//		377 	
//			Blaze Powder
//		(minecraft:blaze_powder)
//		378 	
//			Magma Cream
//		(minecraft:magma_cream)
//		379 	
//			Brewing Stand
//		(minecraft:brewing_stand)
//		380 	
//			Cauldron
//		(minecraft:cauldron)
//		381 	
//			Eye of Ender
//		(minecraft:ender_eye)
//		382 	
//			Glistering Melon
//		(minecraft:speckled_melon)
//		383:4 	
//			Spawn Elder Guardian
//		(minecraft:spawn_egg)
//		383:5 	
//			Spawn Wither Skeleton
//		(minecraft:spawn_egg)
//		383:6 	
//			Spawn Stray
//		(minecraft:spawn_egg)
//		383:23 	
//			Spawn Husk
//		(minecraft:spawn_egg)
//		383:27 	
//			Spawn Zombie Villager
//		(minecraft:spawn_egg)
//		383:28 	
//			Spawn Skeleton Horse
//		(minecraft:spawn_egg)
//		383:29 	
//			Spawn Zombie Horse
//		(minecraft:spawn_egg)
//		383:31 	
//			Spawn Donkey
//		(minecraft:spawn_egg)
//		383:32 	
//			Spawn Mule
//		(minecraft:spawn_egg)
//		383:34 	
//			Spawn Evoker
//		(minecraft:spawn_egg)
//		383:35 	
//			Spawn Vex
//		(minecraft:spawn_egg)
//		383:36 	
//			Spawn Vindicator
//		(minecraft:spawn_egg)
//		383:50 	
//			Spawn Creeper
//		(minecraft:spawn_egg)
//		383:51 	
//			Spawn Skeleton
//		(minecraft:spawn_egg)
//		383:52 	
//			Spawn Spider
//		(minecraft:spawn_egg)
//		383:54 	
//			Spawn Zombie
//		(minecraft:spawn_egg)
//		383:55 	
//			Spawn Slime
//		(minecraft:spawn_egg)
//		383:56 	
//			Spawn Ghast
//		(minecraft:spawn_egg)
//		383:57 	
//			Spawn Zombie Pigman
//		(minecraft:spawn_egg)
//		383:58 	
//			Spawn Enderman
//		(minecraft:spawn_egg)
//		383:59 	
//			Spawn Cave Spider
//		(minecraft:spawn_egg)
//		383:60 	
//			Spawn Silverfish
//		(minecraft:spawn_egg)
//		383:61 	
//			Spawn Blaze
//		(minecraft:spawn_egg)
//		383:62 	
//			Spawn Magma Cube
//		(minecraft:spawn_egg)
//		383:65 	
//			Spawn Bat
//		(minecraft:spawn_egg)
//		383:66 	
//			Spawn Witch
//		(minecraft:spawn_egg)
//		383:67 	
//			Spawn Endermite
//		(minecraft:spawn_egg)
//		383:68 	
//			Spawn Guardian
//		(minecraft:spawn_egg)
//		383:69 	
//			Spawn Shulker
//		(minecraft:spawn_egg)
//		383:90 	
//			Spawn Pig
//		(minecraft:spawn_egg)
//		383:91 	
//			Spawn Sheep
//		(minecraft:spawn_egg)
//		383:92 	
//			Spawn Cow
//		(minecraft:spawn_egg)
//		383:93 	
//			Spawn Chicken
//		(minecraft:spawn_egg)
//		383:94 	
//			Spawn Squid
//		(minecraft:spawn_egg)
//		383:95 	
//			Spawn Wolf
//		(minecraft:spawn_egg)
//		383:96 	
//			Spawn Mooshroom
//		(minecraft:spawn_egg)
//		383:98 	
//			Spawn Ocelot
//		(minecraft:spawn_egg)
//		383:100 	
//			Spawn Horse
//		(minecraft:spawn_egg)
//		383:101 	
//			Spawn Rabbit
//		(minecraft:spawn_egg)
//		383:102 	
//			Spawn Polar Bear
//		(minecraft:spawn_egg)
//		383:103 	
//			Spawn Llama
//		(minecraft:spawn_egg)
//		383:105 	
//			Spawn Parrot
//		(minecraft:spawn_egg)
//		383:120 	
//			Spawn Villager
//		(minecraft:spawn_egg)
//		384 	
//			Bottle o' Enchanting
//		(minecraft:experience_bottle)
//		385 	
//			Fire Charge
//		(minecraft:fire_charge)
//		386 	
//			Book and Quill
//		(minecraft:writable_book)
//		387 	
//			Written Book
//		(minecraft:written_book)
//		388 	
//			Emerald
//		(minecraft:emerald)
//		389 	
//			Item Frame
//		(minecraft:item_frame)
//		390 	
//			Flower Pot
//		(minecraft:flower_pot)
//		391 	
//			Carrot
//		(minecraft:carrot)
//		392 	
//			Potato
//		(minecraft:potato)
//		393 	
//			Baked Potato
//		(minecraft:baked_potato)
//		394 	
//			Poisonous Potato
//		(minecraft:poisonous_potato)
//		395 	
//			Empty Map
//		(minecraft:map)
//		396 	
//			Golden Carrot
//		(minecraft:golden_carrot)
//		397 	
//			Mob Head (Skeleton)
//		(minecraft:skull)
//		397:1 	
//			Mob Head (Wither Skeleton)
//		(minecraft:skull)
//		397:2 	
//			Mob Head (Zombie)
//		(minecraft:skull)
//		397:3 	
//			Mob Head (Human)
//		(minecraft:skull)
//		397:4 	
//			Mob Head (Creeper)
//		(minecraft:skull)
//		397:5 	
//			Mob Head (Dragon)
//		(minecraft:skull)
//		398 	
//			Carrot on a Stick
//		(minecraft:carrot_on_a_stick)
//		399 	
//			Nether Star
//		(minecraft:nether_star)
//		400 	
//			Pumpkin Pie
//		(minecraft:pumpkin_pie)
//		401 	
//			Firework Rocket
//		(minecraft:fireworks)
//		402 	
//			Firework Star
//		(minecraft:firework_charge)
//		403 	
//			Enchanted Book
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
