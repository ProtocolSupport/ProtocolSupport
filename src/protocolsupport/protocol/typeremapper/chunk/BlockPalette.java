package protocolsupport.protocol.typeremapper.chunk;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;

public class BlockPalette {

	protected final Int2IntLinkedOpenHashMap blockstateToRuntimeId = new Int2IntLinkedOpenHashMap();
	protected int nextRuntimeId = 0;

	public BlockPalette() {
		//always register air
		getRuntimeId(0);
	}

	public int getRuntimeId(int blockstate) {
		return blockstateToRuntimeId.computeIfAbsent(blockstate, k -> nextRuntimeId++);
	}

	public int[] getBlockStates() {
		return blockstateToRuntimeId.keySet().toIntArray();
	}

}