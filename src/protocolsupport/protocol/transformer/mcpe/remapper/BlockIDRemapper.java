package protocolsupport.protocol.transformer.mcpe.remapper;

import java.util.Arrays;

import net.minecraft.server.v1_8_R3.Block;

public class BlockIDRemapper {

	private static int[] replacements = new int[4096];
	static {
		Arrays.fill(replacements, 248);
		//see https://github.com/DragonetMC/Dragonet/blob/master/Dragonet-core/src/main/java/org/dragonet/net/translator/ItemTranslator_v0_11.java
		putSelf(0, 22);
		replacements[24] = 24;
		putSelf(26, 27);
		putSelf(30, 68);
		replacements[71] = 71;
		replacements[73] = 73;
		replacements[74] = 74;
		putSelf(78, 83);
		putSelf(85, 87);
		replacements[89] = 89;
		putSelf(91, 92);
		putSelf(95, 96);
		putSelf(99, 112);
		replacements[114] = 114;
		putSelf(120, 121);
		putSelf(127, 129);
		putSelf(133, 136);
		replacements[139] = 139;
		putSelf(141, 142);
		putSelf(155, 159);
		replacements[160] = 102;
		putSelf(163, 164);
		replacements[169] = 89;
		putSelf(170, 174);
		putSelf(243, 249);
	}

	private static void putSelf(int start, int end) {
		for (int i = start; i <= end; i++) {
			replacements[i] = i;
		}
	}

	public static int replaceBlockId(int oldId) {
		return replacements[oldId];
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
