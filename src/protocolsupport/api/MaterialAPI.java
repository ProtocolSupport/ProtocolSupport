package protocolsupport.api;

import java.text.MessageFormat;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;

import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.zplatform.ServerPlatform;

public class MaterialAPI {

	/**
	 * Returns all possible block data states for this material
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
	 * Returns blockdata network id
	 * @param blockdata blockdata
	 * @return blockdata network id
	 */
	public static int getBlockDataNetworkId(BlockData blockdata) {
		return ServerPlatform.get().getMiscUtils().getBlockDataNetworkId(blockdata);
	}

	/**
	 * Returns blockdata by network id <br>
	 * Returns null if no blockdata exists for this network id
	 * @param id blockdata network id
	 * @return blockdata
	 */
	public static BlockData getBlockDataByNetworkId(int id) {
		return ServerPlatform.get().getMiscUtils().getBlockDataByNetworkId(id);
	}

	/**
	 * Returns block material network id
	 * @param material block material
	 * @return network id
	 */
	@SuppressWarnings("deprecation")
	public static int getBlockNetworkId(Material material) {
		if (material.isLegacy()) {
			throw new IllegalArgumentException(MessageFormat.format("Material {0} is legacy", material));
		}
		if (!material.isBlock()) {
			throw new IllegalArgumentException(MessageFormat.format("Material {0} is not a block", material));
		}
		return ServerPlatform.get().getMiscUtils().getBlockNetworkId(material);
	}

	/**
	 * Returns block material by network id <br>
	 * Returns null if no block exists for this network id
	 * @param id block network id
	 * @return block material
	 */
	public static Material getBlockByNetworkId(int id) {
		return ServerPlatform.get().getMiscUtils().getBlockByNetworkId(id);
	}

	/**
	 * Returns item network id
	 * @param material item material
	 * @return item network id
	 */
	@SuppressWarnings("deprecation")
	public static int getItemNetworkId(Material material) {
		if (material.isLegacy()) {
			throw new IllegalArgumentException(MessageFormat.format("Material {0} is legacy", material));
		}
		if (!material.isItem()) {
			throw new IllegalArgumentException(MessageFormat.format("Material {0} is not an item", material));
		}
		return ItemMaterialLookup.getRuntimeId(material);
	}

	/**
	 * Returns item material by network id <br>
	 * Returns null if no item exists for this item id
	 * @param id item network id
	 * @return item material
	 */
	public static Material getItemByNetworkId(int id) {
		return ItemMaterialLookup.getByRuntimeId(id);
	}

	/**
	 * Returns living entity type network id
	 * @param type entity type
	 * @return id
	 */
	public static int getEntityLivingTypeNetworkId(EntityType type) {
		return NetworkEntityType.getByBukkitType(type).getNetworkTypeId();
	}

	/**
	 * Returns object entity type network id
	 * @param type entity type
	 * @return id
	 */
	public static int getEntityObjectTypeNetworkId(EntityType type) {
		return NetworkEntityType.getByBukkitType(type).getNetworkTypeId();
	}

	/**
	 * Returns living entity type by network id <br>
	 * Returns null if no entity type exists for this id
	 * @param id entity network id
	 * @return entity type
	 */
	public static EntityType getEntityLivingTypeByNetworkId(int id) {
		try {
			return NetworkEntityType.getMobByNetworkTypeId(id).getBukkitType();
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Returns object entity type by network id <br>
	 * Returns null if no entity type exists for this id
	 * @param id entity network id
	 * @return entity type
	 */
	public static EntityType getEntityObjectTypeByNetworkId(int id) {
		try {
			return NetworkEntityType.getObjectByNetworkTypeId(id).getBukkitType();
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
