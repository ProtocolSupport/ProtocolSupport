package protocolsupport.protocol.transformer.v_1_5.remappers;

import java.util.Arrays;

import net.minecraft.server.v1_8_R3.Block;

public class BlockIDRemapper {

	private static int[] replacements = new int[4096];
	static {
		Arrays.fill(replacements, -1);
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
		// barrier -> ? (probably not needed) (or maybe glass?)
		replacements[166] = 20;
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
		replacements[174] = 80;
		// tall plant -> yellow flower
		replacements[175] = 38;
		// standing banner -> standing sign
		replacements[176] = 63;
		// wall banner -> wall sign
		replacements[177] = 68;
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
		replacements[193] = 64;
		replacements[194] = 64;
		replacements[195] = 64;
		replacements[196] = 64;
		replacements[197] = 64;
		// inverted daylight detector -> daylight detector
		replacements[178] = 151;
	}

	public static int replaceBlockId(int oldId) {
		if (replacements[oldId] != -1) {
			return replacements[oldId];
		}
		return oldId;
	}

	public static void setRemap(int oldId, int newId) {
		if ((oldId < 1) || (oldId > 4095) || (newId < 1) || (newId > 4095)) {
			throw new IllegalArgumentException("Id should be in range of 1-4095");
		}
		if (Block.getById(newId) == null) {
			throw new IllegalArgumentException("Block with id "+newId+" doesn't exist");
		}
		replacements[oldId] = newId;
	}

}
