package protocolsupport.protocol.typeremapper.id;

import protocolsupport.utils.ProtocolVersionsHelper;

public class IdRemapper {

	public static final RemappingReigstry BLOCK = new RemappingReigstry() {
		{
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
			// stained clay -> clay
			registerRemapEntry(159, 82, ProtocolVersionsHelper.BEFORE_1_6);
			// hay bale -> stone
			registerRemapEntry(170, 1, ProtocolVersionsHelper.BEFORE_1_6);
			// carpet -> stone pressure plate
			registerRemapEntry(171, 70, ProtocolVersionsHelper.BEFORE_1_6);
			// hardened clay -> clay
			registerRemapEntry(172, 82, ProtocolVersionsHelper.BEFORE_1_6);
			// coal block -> stone
			registerRemapEntry(173, 1, ProtocolVersionsHelper.BEFORE_1_6);
			// packed ice -> snow
			registerRemapEntry(174, 80, ProtocolVersionsHelper.BEFORE_1_6);
			// tall plant -> yellow flower
			registerRemapEntry(175, 38, ProtocolVersionsHelper.BEFORE_1_6);
			
			// MCPE
			// stained glass -> glass
			registerRemapEntry(95, 20, ProtocolVersionsHelper.MCPE);
			// stained glass pane -> glass pane
			registerRemapEntry(160, 102, ProtocolVersionsHelper.MCPE);
			// redstone -> carpet (remove when MCPE 0.13 is released! && protocolsupport is updated to MCPE 0.13)
			registerRemapEntry(55, 171, ProtocolVersionsHelper.MCPE);
			// redstone torch -> torch (remove when MCPE 0.13 is released! && protocolsupport is updated to MCPE 0.13)
			registerRemapEntry(75, 50, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(76, 50, ProtocolVersionsHelper.MCPE);
			// all pressure plates -> carpet
			registerRemapEntry(70, 50, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(72, 50, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(147, 50, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(148, 50, ProtocolVersionsHelper.MCPE);
			// sea lantern -> glowstone
			registerRemapEntry(169, 89, ProtocolVersionsHelper.MCPE);
			// prismarine -> mossy cobblestone
			registerRemapEntry(168, 48, ProtocolVersionsHelper.MCPE);
			// wood slab -> wood slab (MCPE ID remap)
			registerRemapEntry(126, 158, ProtocolVersionsHelper.MCPE);
			// all doors -> door
			registerRemapEntry(193, 64, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(194, 64, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(195, 64, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(196, 64, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(197, 64, ProtocolVersionsHelper.MCPE);
			// standing banner -> standing sign
			registerRemapEntry(176, 63, ProtocolVersionsHelper.MCPE);
			// all buttons -> sign (well, I don't know a better replacer for this)
			registerRemapEntry(77, 63, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(143, 63, ProtocolVersionsHelper.MCPE);
			// (sticky) piston -> stone
			registerRemapEntry(29, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(33, 1, ProtocolVersionsHelper.MCPE);
			// piston head -> stone
			registerRemapEntry(34, 1, ProtocolVersionsHelper.MCPE);
			// dispenser -> stone
			registerRemapEntry(23, 1, ProtocolVersionsHelper.MCPE);
			// note block -> wood
			registerRemapEntry(25, 5, ProtocolVersionsHelper.MCPE);
			// detector rail -> powered rail
			registerRemapEntry(28, 27, ProtocolVersionsHelper.MCPE);
			// lever -> sign (well, I don't know a better replacer for this)
			registerRemapEntry(69, 63, ProtocolVersionsHelper.MCPE);
			// jukebox -> wood
			registerRemapEntry(84, 5, ProtocolVersionsHelper.MCPE);
			// repeater (on/off) -> carpet
			registerRemapEntry(93, 171, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(94, 171, ProtocolVersionsHelper.MCPE);
			// cauldron -> stone
			registerRemapEntry(118, 1, ProtocolVersionsHelper.MCPE);
			// end portal -> nether portal
			registerRemapEntry(119, 90, ProtocolVersionsHelper.MCPE);
			// dragon egg -> stone
			registerRemapEntry(122, 1, ProtocolVersionsHelper.MCPE);
			// redstone lamp (on/off) to glowstone
			registerRemapEntry(124, 89, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(123, 89, ProtocolVersionsHelper.MCPE);
			// double oak slab -> double oak slab (MCPE ID remap)
			registerRemapEntry(125, 157, ProtocolVersionsHelper.MCPE);
			// enderchest -> chest
			registerRemapEntry(130, 54, ProtocolVersionsHelper.MCPE);
			// tripwire hook -> sign
			registerRemapEntry(131, 63, ProtocolVersionsHelper.MCPE);
			// tripwire -> air (Because tripwire is a passable block, and there is no replacement for it in MCPE)
			registerRemapEntry(132, 0, ProtocolVersionsHelper.MCPE);
			// command block -> stone
			registerRemapEntry(137, 1, ProtocolVersionsHelper.MCPE);
			// beacon -> nether reactor core
			registerRemapEntry(138, 247, ProtocolVersionsHelper.MCPE);
			// mob head -> mob head (MCPE ID remap)
			registerRemapEntry(144, 143, ProtocolVersionsHelper.MCPE);
			// anvil -> anvil (MCPE ID remap)
			registerRemapEntry(145, 144, ProtocolVersionsHelper.MCPE);
			// trapped chest -> chest
			registerRemapEntry(146, 54, ProtocolVersionsHelper.MCPE);
			// comparator (on/off) -> carpet
			registerRemapEntry(149, 171, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(150, 171, ProtocolVersionsHelper.MCPE);
			// daylight sensor -> slab
			registerRemapEntry(151, 158, ProtocolVersionsHelper.MCPE);
			// hopper -> stone
			registerRemapEntry(154, 1, ProtocolVersionsHelper.MCPE);
		}
		@Override
		protected RemappingTable createTable() {
			return new RemappingTable(4096);
		}
	};

	public static final RemappingReigstry ITEM = new RemappingReigstry() {
		{
			copy(BLOCK);
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
			
			// MCPE
			// banner -> sign
			registerRemapEntry(425, 323, ProtocolVersionsHelper.MCPE);
			// raw mutton -> chicken raw meat
			registerRemapEntry(423, 365, ProtocolVersionsHelper.MCPE);
			// cooked mutton -> chicken cooked meat
			registerRemapEntry(424, 366, ProtocolVersionsHelper.MCPE);
			// nametag -> stone
			registerRemapEntry(421, 1, ProtocolVersionsHelper.MCPE);	
			// lead -> stone
			registerRemapEntry(420, 1, ProtocolVersionsHelper.MCPE);
			
			// all discs -> stone
			registerRemapEntry(2256, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2257, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2258, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2259, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2260, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2261, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2262, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2263, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2264, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2265, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2266, 1, ProtocolVersionsHelper.MCPE);
			registerRemapEntry(2267, 1, ProtocolVersionsHelper.MCPE);
		}
		@Override
		protected RemappingTable createTable() {
			return new RemappingTable(4096);
		}
	};

	public static final RemappingReigstry ENTITY = new RemappingReigstry() {
		{
			// endermite -> silverfish
			registerRemapEntry(67, 60, ProtocolVersionsHelper.BEFORE_1_8);
			// guardian -> sqiud
			registerRemapEntry(68, 94, ProtocolVersionsHelper.BEFORE_1_8);
			// rabbit -> chicken
			registerRemapEntry(101, 93, ProtocolVersionsHelper.BEFORE_1_8);
			// horse -> cow
			registerRemapEntry(100, 92, ProtocolVersionsHelper.BEFORE_1_6);
			
			// MCPE
			// endermite -> silverfish
			registerRemapEntry(67, 60, ProtocolVersionsHelper.MCPE);
			// guardian -> sqiud
			registerRemapEntry(68, 94, ProtocolVersionsHelper.MCPE);
			// rabbit -> chicken
			registerRemapEntry(101, 93, ProtocolVersionsHelper.MCPE);
			// horse -> cow
			registerRemapEntry(100, 92, ProtocolVersionsHelper.MCPE);
		}
		@Override
		protected RemappingTable createTable() {
			return new RemappingTable(256);
		}
	};

	public static final RemappingReigstry MAPCOLOR = new RemappingReigstry() {
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

}
