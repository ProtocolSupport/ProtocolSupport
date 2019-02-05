package protocolsupport.protocol.utils;

import java.util.List;

import org.bukkit.block.data.BlockData;

import protocolsupport.api.MaterialAPI;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupportbuildprocessor.Preload;

@Preload
public class BlockBlockDataLookup {

	protected static final int[] byBlockId = new int[MinecraftData.BLOCK_COUNT];
	protected static final int[] toBlockId = new int[MinecraftData.BLOCKDATA_COUNT];

	static {
		MinecraftData.getBlocks()
		.forEach(b -> {
			int blockId = MaterialAPI.getBlockNetworkId(b);
			List<BlockData> blockdataList = MaterialAPI.getBlockDataList(b);
			byBlockId[blockId] = MaterialAPI.getBlockDataNetworkId(blockdataList.get(0));
			blockdataList.forEach(blockdata -> toBlockId[MaterialAPI.getBlockDataNetworkId(blockdata)] = blockId);
		});
	}

	public static int getBlockDataId(int blockId) {
		return byBlockId[blockId];
	}

}
