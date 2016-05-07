package protocolsupport.protocol.typeremapper.id;

import org.bukkit.Material;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.remapper.BlockRemapperControl;
import protocolsupport.utils.ProtocolVersionsHelper;

public class IdRemapper {

	public static void init() {
	}

	public static final RemappingRegistry BLOCK = new RemappingRegistry() {
		{
			registerRemapEntry(Material.CHORUS_FLOWER, Material.WOOD, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_PLANT, Material.WOOD, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_GATEWAY, Material.ENDER_PORTAL, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_ROD, Material.GLOWSTONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_BRICKS, Material.ENDER_STONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.FROSTED_ICE, Material.ICE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.GRASS_PATH, Material.GRASS, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.PURPUR_BLOCK, Material.STONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.PURPUR_PILLAR, Material.STONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.PURPUR_STAIRS, Material.COBBLESTONE_STAIRS, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.PURPUR_SLAB, Material.STONE_SLAB2, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.PURPUR_DOUBLE_SLAB, Material.DOUBLE_STONE_SLAB2, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.STRUCTURE_BLOCK, Material.BEDROCK, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_BLOCK, Material.CROPS, ProtocolVersionsHelper.BEFORE_1_9);
			// slime -> emerald block
			registerRemapEntry(165, 133, ProtocolVersionsHelper.BEFORE_1_8);
			// barrier -> glass
			registerRemapEntry(166, 20, ProtocolVersionsHelper.BEFORE_1_8);
			// iron trapdoor -> trapdoor
			registerRemapEntry(167, 96, ProtocolVersionsHelper.BEFORE_1_8);
			// prismarine -> mossy cobblestone
			registerRemapEntry(168, 48, ProtocolVersionsHelper.BEFORE_1_8);
			// sea lantern -> glowstone
			registerRemapEntry(169, 89, ProtocolVersionsHelper.BEFORE_1_8);
			// standing banner -> standing sign
			registerRemapEntry(176, 63, ProtocolVersionsHelper.BEFORE_1_8);
			// wall banner -> wall sign
			registerRemapEntry(177, 68, ProtocolVersionsHelper.BEFORE_1_8);
			// red sandstone -> sandstone
			registerRemapEntry(179, 24, ProtocolVersionsHelper.BEFORE_1_8);
			// red sandstone stairs -> sandstone stairs
			registerRemapEntry(180, 128, ProtocolVersionsHelper.BEFORE_1_8);
			// red sandstone doubleslab -> double step
			registerRemapEntry(181, 43, ProtocolVersionsHelper.BEFORE_1_8);
			// red sandstone slab -> step
			registerRemapEntry(182, 44, ProtocolVersionsHelper.BEFORE_1_8);
			// all fence gates -> fence gate
			registerRemapEntry(183, 107, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(184, 107, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(185, 107, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(186, 107, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(187, 107, ProtocolVersionsHelper.BEFORE_1_8);
			// all fences -> fence
			registerRemapEntry(188, 85, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(189, 85, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(190, 85, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(191, 85, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(192, 85, ProtocolVersionsHelper.BEFORE_1_8);
			// all doors -> door
			registerRemapEntry(193, 64, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(194, 64, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(195, 64, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(196, 64, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(197, 64, ProtocolVersionsHelper.BEFORE_1_8);
			// inverted daylight detector -> daylight detector
			registerRemapEntry(178, 151, ProtocolVersionsHelper.BEFORE_1_8);
			// stained glass -> glass
			registerRemapEntry(95, 20, ProtocolVersionsHelper.BEFORE_1_7);
			// stained glass pane -> glass pane
			registerRemapEntry(160, 102, ProtocolVersionsHelper.BEFORE_1_7);
			// leaves2 -> leaves
			registerRemapEntry(161, 18, ProtocolVersionsHelper.BEFORE_1_7);
			// log2 -> log
			registerRemapEntry(162, 17, ProtocolVersionsHelper.BEFORE_1_7);
			// acacia stairs -> oak stairs
			registerRemapEntry(163, 53, ProtocolVersionsHelper.BEFORE_1_7);
			// dark oak stairs -> oak stairs
			registerRemapEntry(164, 53, ProtocolVersionsHelper.BEFORE_1_7);
			// tall plant -> yellow flower
			registerRemapEntry(175, 38, ProtocolVersionsHelper.BEFORE_1_7);
			// packed ice -> snow
			registerRemapEntry(174, 80, ProtocolVersionsHelper.BEFORE_1_7);
			// stained clay -> stone
			registerRemapEntry(159, 1, ProtocolVersionsHelper.BEFORE_1_6);
			// hay bale -> stone
			registerRemapEntry(170, 1, ProtocolVersionsHelper.BEFORE_1_6);
			// carpet -> stone pressure plate
			registerRemapEntry(171, 70, ProtocolVersionsHelper.BEFORE_1_6);
			// hardened clay -> clay
			registerRemapEntry(172, 82, ProtocolVersionsHelper.BEFORE_1_6);
			// coal block -> stone
			registerRemapEntry(173, 1, ProtocolVersionsHelper.BEFORE_1_6);
			// dropper -> stone
			registerRemapEntry(178, 1, ProtocolVersionsHelper.BEFORE_1_5);
			// hopper -> stone
			registerRemapEntry(154, 1, ProtocolVersionsHelper.BEFORE_1_5);
			// quartz -> snow
			registerRemapEntry(155, 80, ProtocolVersionsHelper.BEFORE_1_5);
			// quartz stairs -> stairs
			registerRemapEntry(156, 109, ProtocolVersionsHelper.BEFORE_1_5);
			// quartz slab -> slab
			registerRemapEntry(156, 44, ProtocolVersionsHelper.BEFORE_1_5);
			// inverted daylight detector -> stone
			registerRemapEntry(178, 1, ProtocolVersionsHelper.BEFORE_1_5);
			// daylight detector -> slab
			registerRemapEntry(151, 44, ProtocolVersionsHelper.BEFORE_1_5);
			// trapped chest -> chest
			registerRemapEntry(146, 54, ProtocolVersionsHelper.BEFORE_1_5);
			// redstone block -> glowing redstone ore
			registerRemapEntry(146, 73, ProtocolVersionsHelper.BEFORE_1_5);
			// activator rail -> some other rail
			registerRemapEntry(157, 28, ProtocolVersionsHelper.BEFORE_1_5);
			// nether quartz ore -> nettherrack
			registerRemapEntry(153, 87, ProtocolVersionsHelper.BEFORE_1_5);
			// wpressure plate light -> wood pressure plate
			registerRemapEntry(147, 72, ProtocolVersionsHelper.BEFORE_1_5);
			// wpressure plate heavy -> stone pressure plate
			registerRemapEntry(148, 70, ProtocolVersionsHelper.BEFORE_1_5);
			// redstone comparator -> repeater
			registerRemapEntry(149, 93, ProtocolVersionsHelper.BEFORE_1_5);
		}
		@SuppressWarnings("deprecation")
		protected void registerRemapEntry(Material from, Material to, ProtocolVersion... versions) {
			registerRemapEntry(from.getId(), to.getId(), versions);
		}
		public void registerRemapEntry(int from, int to, ProtocolVersion... versions) {
			for (int i = 0; i < 16; i++) {
				super.registerRemapEntry((from << 4) + i, (to << 4) + i, versions);
			}
		}
		@Override
		protected RemappingTable createTable() {
			return new RemappingTable(4096 * 16);
		}
	};

	public static final RemappingRegistry ITEM = new RemappingRegistry() {
		{
			for (ProtocolVersion version : ProtocolVersion.values()) {
				if (version.isSupported()) {
					BlockRemapperControl ctrl = ProtocolSupportAPI.getBlockRemapper(version);
					for (int i = 0; i < 4096; i++) {
						registerRemapEntry(i, ctrl.getRemap(i), version);
					}
				}
			}
			registerRemapEntry(Material.BEETROOT, Material.BROWN_MUSHROOM, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_SOUP, Material.MUSHROOM_SOUP, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BEETROOT_SEEDS, Material.SEEDS, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_FRUIT, Material.POTATO_ITEM, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.CHORUS_FRUIT_POPPED, Material.BAKED_POTATO, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.DRAGONS_BREATH, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPLASH_POTION, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.LINGERING_POTION, Material.POTION, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.ELYTRA, Material.LEATHER_CHESTPLATE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.END_CRYSTAL, Material.STONE, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SHIELD, Material.WOOD_SWORD, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.SPECTRAL_ARROW, Material.ARROW, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.TIPPED_ARROW, Material.ARROW, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_ACACIA, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_BIRCH, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_DARK_OAK, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_JUNGLE, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(Material.BOAT_SPRUCE, Material.BOAT, ProtocolVersionsHelper.BEFORE_1_9);
			// all doors -> door
			registerRemapEntry(427, 324, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(428, 324, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(429, 324, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(430, 324, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(431, 324, ProtocolVersionsHelper.BEFORE_1_8);
			// rabbit raw meat -> chicken raw meat
			registerRemapEntry(411, 365, ProtocolVersionsHelper.BEFORE_1_8);
			// rabbit cooked meat -> chicken cooked meat
			registerRemapEntry(412, 366, ProtocolVersionsHelper.BEFORE_1_8);
			// rabbit stew -> mushroom stew
			registerRemapEntry(413, 282, ProtocolVersionsHelper.BEFORE_1_8);
			// raw mutton -> chicken raw meat
			registerRemapEntry(423, 365, ProtocolVersionsHelper.BEFORE_1_8);
			// cooked mutton -> chicken cooked meat
			registerRemapEntry(424, 366, ProtocolVersionsHelper.BEFORE_1_8);
			// banner -> sign
			registerRemapEntry(425, 323, ProtocolVersionsHelper.BEFORE_1_8);
			// everything else -> stone
			registerRemapEntry(409, 1, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(410, 1, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(414, 1, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(415, 1, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(416, 1, ProtocolVersionsHelper.BEFORE_1_8);
			registerRemapEntry(417, 1, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(418, 1, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(419, 1, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(420, 1, ProtocolVersionsHelper.BEFORE_1_6);
			registerRemapEntry(421, 1, ProtocolVersionsHelper.BEFORE_1_6);
			// minecarts -> default minecart
			registerRemapEntry(407, 328, ProtocolVersionsHelper.BEFORE_1_5);
			registerRemapEntry(408, 328, ProtocolVersionsHelper.BEFORE_1_5);
			// comparator -> repeater
			registerRemapEntry(404, 356, ProtocolVersionsHelper.BEFORE_1_5);
			// nether brick -> brick
			registerRemapEntry(405, 336, ProtocolVersionsHelper.BEFORE_1_5);
			// quartz -> feather
			registerRemapEntry(406, 288, ProtocolVersionsHelper.BEFORE_1_5);
		}
		@SuppressWarnings("deprecation")
		private void registerRemapEntry(Material from, Material to, ProtocolVersion... versions) {
			registerRemapEntry(from.getId(), to.getId(), versions);
		}
		@Override
		protected RemappingTable createTable() {
			return new RemappingTable(4096);
		}
	};

	public static final RemappingRegistry ENTITY_LIVING = new RemappingRegistry() {
		{
			// sulker -> blaze
			registerRemapEntry(69, 61, ProtocolVersionsHelper.BEFORE_1_9);
			// endermite -> silverfish
			registerRemapEntry(67, 60, ProtocolVersionsHelper.BEFORE_1_8);
			// guardian -> sqiud
			registerRemapEntry(68, 94, ProtocolVersionsHelper.BEFORE_1_8);
			// rabbit -> chicken
			registerRemapEntry(101, 93, ProtocolVersionsHelper.BEFORE_1_8);
			// horse -> cow
			registerRemapEntry(100, 92, ProtocolVersionsHelper.BEFORE_1_6);
		}
		@Override
		protected RemappingTable createTable() {
			return new RemappingTable(256);
		}
	};

	public static final RemappingRegistry ENTITY_OBJECT = new RemappingRegistry() {
		{
			//shulker bullet -> firecharge
			registerRemapEntry(67, 64, ProtocolVersionsHelper.BEFORE_1_9);
			//dragon fireball -> firecharge
			registerRemapEntry(93, 64, ProtocolVersionsHelper.BEFORE_1_9);
			//spectral arrow -> arrow
			registerRemapEntry(91, 60, ProtocolVersionsHelper.BEFORE_1_9);
			//tipped arrow -> arrow
			registerRemapEntry(92, 60, ProtocolVersionsHelper.BEFORE_1_9);
		}
		@Override
		protected RemappingTable createTable() {
			return new RemappingTable(256);
		}
	};

	public static final RemappingRegistry MAPCOLOR = new RemappingRegistry() {
		{
			//see http://minecraft.gamepedia.com/Map_item_format (i don't event know a names for half of those colors)
			registerRemapEntry(14, 8, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(15, 10, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(16, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(17, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(18, 2, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(19, 1, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(20, 4, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(21, 11, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(22, 11, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(23, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(24, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(25, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(26, 10, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(27, 7, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(28, 4, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(29, 11, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(30, 2, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(31, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(32, 5, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(33, 7, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(34, 10, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(35, 4, ProtocolVersionsHelper.BEFORE_1_7);
			registerRemapEntry(36, 10, ProtocolVersionsHelper.BEFORE_1_7);
		}
		@Override
		protected RemappingTable createTable() {
			return new RemappingTable(64) {
				@Override
				public int getRemap(int id) {
					int realColor = (id & 0xFF) >> 2;
					return ((table[realColor] << 2) + (id & 0b11));
				}
			};
		}
	};

	public static final RemappingRegistry EFFECT = new RemappingRegistry() {
		{
			registerRemapEntry(1003, 1002, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1004, 1002, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1005, 1003, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1006, 1003, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1007, 1003, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1008, 1003, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1009, 1004, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1010, 1005, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1011, 1006, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1012, 1006, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1013, 1006, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1014, 1006, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1015, 1007, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1016, 1008, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1017, 1008, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1018, 1009, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1019, 1010, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1020, 1011, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1021, 1012, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1022, 1012, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1023, 1013, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1024, 1014, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1025, 1015, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1026, 1016, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1027, 1017, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1028, 1018, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1029, 1020, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1030, 1021, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1031, 1022, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1036, 1003, ProtocolVersionsHelper.BEFORE_1_9);
			registerRemapEntry(1037, 1006, ProtocolVersionsHelper.BEFORE_1_9);
		}
		@Override
		protected RemappingTable createTable() {
			return new RemappingTable.HashRemappingTable();
		}
	};

}
