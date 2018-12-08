package protocolsupport.api;

import java.text.MessageFormat;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.zplatform.ServerPlatform;

public class MaterialAPI {

	/**
	 * Retunrs all possible block data states for this material
	 * @param material block material
	 * @return all possible block data states
	 */
	@SuppressWarnings("deprecation")
	public static List<BlockData> getBlockDataList(Material material) {
		if (material.isLegacy()) {
			throw new IllegalArgumentException(MessageFormat.format("Material {0} is legacy", material));
		}
		if (!material.isBlock()) {
			throw new IllegalArgumentException(MessageFormat.format("Material {0} is not a block", material));
		}
		return ServerPlatform.get().getMiscUtils().getBlockDataList(material);
	}

	/**
	 * Returns current blockdata network id
	 * @param blockdata blockdata
	 * @return current blockdata network id
	 */
	public static int getBlockDataNetworkId(BlockData blockdata) {
		return ServerPlatform.get().getMiscUtils().getBlockDataNetworkId(blockdata);
	}

	/**
	 * Returns blockdata by network id
	 * @param id blockdata runtime id
	 * @return blockdata for network id
	 */
	public static BlockData getBlockDataByNetworkId(int id) {
		return ServerPlatform.get().getMiscUtils().getBlockDataByNetworkId(id);
	}

	/**
	 * Returns current blockdata network <em>type</em> id.
	 * Type id is not commonly used, but is
	 * present in stuff like blockactions.
	 * @param blockdata blockdata
	 * @return current blockdata network type id
	 */
	public static int getBlockDataNetworkTypeId(BlockData blockdata) {
		return ServerPlatform.get().getMiscUtils().getBlockDataNetworkTypeId(blockdata);
	}

	/**
	 * Returns blockdata by network <em>type</em> id.
	 * Type id is not commonly used, but is
	 * present in stuff like blockactions.
	 * @param id blockdata network type id.
	 * @return blockdata for network type id
	 */
	public static BlockData getBlockDataByNetworkTypeId(int id) {
		return ServerPlatform.get().getMiscUtils().getBlockDataByNetworkTypeId(id);
	}

	/**
	 * Retunrs item network id
	 * @param item item material
	 * @return item network id
	 */
	public static int getItemNetworkId(Material item) {
		return ItemMaterialLookup.getRuntimeId(item);
	}

	/**
	 * Returns material by network id <br>
	 * Returns null if no material is registered for this item id
	 * @param id material network id
	 * @return material for network id
	 */
	public static Material getItemByNetworkId(int id) {
		 return ItemMaterialLookup.getByRuntimeId(id);
	}

}
