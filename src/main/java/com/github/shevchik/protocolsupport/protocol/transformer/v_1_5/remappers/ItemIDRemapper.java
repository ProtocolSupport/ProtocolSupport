package com.github.shevchik.protocolsupport.protocol.transformer.v_1_5.remappers;

import net.minecraft.server.v1_8_R3.Item;

public class ItemIDRemapper {

	private static int[] replacements = new int[4096];
	static {
		for (int i = 0; i < replacements.length; i++) {
			replacements[i] = -1;
		}
		// stained glass -> glass
		replacements[95] = 20;
		// stained clay -> clay
		replacements[159] = 82;
		// stained glass pane -> glass pane
		replacements[160] = 102;
		// leaves2 -> leaves
		replacements[161] = 18;
		// log2 -> log
		replacements[162] = 17;
		// acacia stairs -> oak stairs
		replacements[163] = 53;
		// dark oak stairs -> oak stairs
		replacements[164] = 53;
		// slime -> emerald block
		replacements[165] = 133;
		// barrier -> bedrock
		replacements[166] = 7;
		// iron trapdoor -> trapdoor
		replacements[167] = 96;
		// prismarine -> mossy cobblestone
		replacements[168] = 48;
		// sea lantern -> glowstone
		replacements[169] = 89;
		// hay bale -> stone
		replacements[170] = 1;
		// carpet -> stone pressure plate
		replacements[171] = 70;
		// hardened clay -> clay
		replacements[172] = 82;
		// coal block -> stone
		replacements[173] = 1;
		// packed ice -> ice
		replacements[174] = 79;
		// tall plant -> yellow flower
		replacements[175] = 38;
		// red sandstone -> sandstone
		replacements[179] = 24;
		// red sandstone stairs -> sandstone stairs
		replacements[180] = 128;
		// red sandstone doubleslab -> double step
		replacements[181] = 43;
		// red sandstone slab -> step
		replacements[182] = 44;
		// all fence gates -> fence gate
		replacements[183] = 107;
		replacements[184] = 107;
		replacements[185] = 107;
		replacements[186] = 107;
		replacements[187] = 107;
		// all fences -> fence
		replacements[188] = 85;
		replacements[189] = 85;
		replacements[190] = 85;
		replacements[191] = 85;
		replacements[192] = 85;
		// all doors -> door
		replacements[427] = 324;
		replacements[428] = 324;
		replacements[429] = 324;
		replacements[430] = 324;
		replacements[431] = 324;
		// rabbit raw meat -> chicken raw meat
		replacements[411] = 365;
		// rabbit cooked meat -> chicken cooked meat
		replacements[412] = 366;
		// rabbit stew -> mushroom stew
		replacements[413] = 282;
		// raw mutton -> chicken raw meat
		replacements[423] = 365;
		// cooked mutton -> chicken cooked meat
		replacements[424] = 366;
		// banner -> sign
		replacements[425] = 323;
		// everything else -> stone
		replacements[409] = 1;
		replacements[410] = 1;
		replacements[414] = 1;
		replacements[415] = 1;
		replacements[416] = 1;
		replacements[417] = 1;
		replacements[418] = 1;
		replacements[419] = 1;
		replacements[420] = 1;
		replacements[421] = 1;
	}

	public static int replaceItemId(int oldId) {
		if (replacements[oldId] != -1) {
			return replacements[oldId];
		}
		return oldId;
	}

	public static void setRemap(int oldId, int newId) {
		if ((oldId < 1) || (oldId > 4095) || (newId < 1) || (newId > 4095)) {
			throw new IllegalArgumentException("Id should be in range of 1-4095");
		}
		if (Item.getById(newId) == null) {
			throw new IllegalArgumentException("Item with id "+newId+" doesn't exist");
		}
		replacements[oldId] = newId;
	}

}
