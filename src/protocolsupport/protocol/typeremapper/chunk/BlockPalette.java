package protocolsupport.protocol.typeremapper.chunk;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntCollection;

public class BlockPalette {

	protected final Int2IntLinkedOpenHashMap blockstateToRuntimeId = new Int2IntLinkedOpenHashMap();
	protected int nextRuntimeId = 0;

	{
		//always register air
		getRuntimeId(0);
	}

	public int getRuntimeId(int blockstate) {
		int runtimeId = blockstateToRuntimeId.getOrDefault(blockstate, -1);
		if (runtimeId == -1) {
			runtimeId = nextRuntimeId++;
			blockstateToRuntimeId.put(blockstate, runtimeId);
		}
		return runtimeId;
	}

	public int getSize() {
		return blockstateToRuntimeId.size();
	}

	public IntCollection getBlockStates() {
		return blockstateToRuntimeId.keySet();
	}

}