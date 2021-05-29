package protocolsupport.protocol.utils;

import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData.BlockDataInfo;
import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData.BlockInfo;
import protocolsupportbuildprocessor.Preload;

@Preload
public class BlockBlockDataLookup {

	private BlockBlockDataLookup() {
	}

	protected static final int[] blockToBlockdata = new int[MinecraftBlockData.BLOCK_COUNT];
	protected static final int[] blockdataToBlock = new int[MinecraftBlockData.BLOCKDATA_COUNT];

	static {
		for (BlockInfo block : MinecraftBlockData.getBlockInfoCollection()) {
			int blockId = block.getNetworkId();
			BlockDataInfo[] blockdataArray = block.getBlockDataArray();
			blockToBlockdata[blockId] = blockdataArray[0].getNetworkId();
			for (BlockDataInfo blockdata : blockdataArray) {
				blockdataToBlock[blockdata.getNetworkId()] = blockId;
			}
		}
	}

	public static int getBlockDataId(int blockId) {
		return blockToBlockdata[blockId];
	}

	public static int getBlockId(int blockdataId) {
		return blockdataToBlock[blockdataId];
	}

}
