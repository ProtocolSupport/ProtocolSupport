package protocolsupport.api;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;

import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData.BlockDataInfo;
import protocolsupport.protocol.utils.minecraftdata.MinecraftBlockData.BlockInfo;

public class MaterialAPI {

	private MaterialAPI() {
	}

	/**
	 * Returns all possible block data states for this material
	 * @param material block material
	 * @return all possible block data states
	 */
	public static @Nonnull List<BlockData> getBlockDataList(@Nonnull Material material) {
		if (material.isLegacy()) {
			throw new IllegalArgumentException(MessageFormat.format("Material {0} is legacy", material));
		}
		if (!material.isBlock()) {
			throw new IllegalArgumentException(MessageFormat.format("Material {0} is not a block", material));
		}
		BlockDataInfo[] blockdataArray = MinecraftBlockData.getBlockInfoByMaterial(material).getBlockDataArray();
		List<BlockData> blockdataList = new ArrayList<>(blockdataArray.length);
		for (BlockDataInfo blockdata : blockdataArray) {
			blockdataList.add(blockdata.getBlockData());
		}
		return blockdataList;
	}

	/**
	 * Returns blockdata network id
	 * @param blockdata blockdata
	 * @return blockdata network id
	 */
	public static @CheckForSigned int getBlockDataNetworkId(@Nonnull BlockData blockdata) {
		BlockDataInfo info = MinecraftBlockData.getBlockDataInfoByBlockData(blockdata);
		return info != null ? info.getNetworkId() : -1;
	}

	/**
	 * Returns blockdata by network id <br>
	 * Returns null if no blockdata exists for this network id
	 * @param id blockdata network id
	 * @return blockdata
	 */
	public static @Nullable BlockData getBlockDataByNetworkId(@Nonnegative int id) {
		BlockDataInfo info = MinecraftBlockData.getBlockDataInfoByNetworkId(id);
		return info != null ? info.getBlockData() : null;
	}

	/**
	 * Returns block material network id
	 * @param material block material
	 * @return network id
	 */
	public static @CheckForSigned int getBlockNetworkId(@Nonnull Material material) {
		if (material.isLegacy()) {
			throw new IllegalArgumentException(MessageFormat.format("Material {0} is legacy", material));
		}
		if (!material.isBlock()) {
			throw new IllegalArgumentException(MessageFormat.format("Material {0} is not a block", material));
		}
		BlockInfo info = MinecraftBlockData.getBlockInfoByMaterial(material);
		return info != null ? info.getNetworkId() : -1;
	}

	/**
	 * Returns block material by network id <br>
	 * Returns null if no block exists for this network id
	 * @param id block network id
	 * @return block material
	 */
	public static @Nullable Material getBlockByNetworkId(@Nonnegative int id) {
		BlockInfo info = MinecraftBlockData.getBlockInfoByNetworkId(id);
		return info != null ? info.getMaterial() : null;
	}

	/**
	 * Returns biome network id
	 * @param biome biome
	 * @return network id
	 * @deprecated biome id mappings are now per connection
	 */
	@Deprecated
	public static int getBiomeNetworkId(Biome biome) {
		return 0;
	}

	/**
	 * Returns biome by network id<br>
	 * Returns null if no biome exists for this network id
	 * @param id biome network id
	 * @return biome biome
	 * @deprecated biome id mappings are now per connection
	 */
	@Deprecated
	public static Biome getBiomeByNetworkId(int id) {
		return Biome.PLAINS;
	}

	/**
	 * Returns item network id
	 * @param material item material
	 * @return item network id
	 */
	public static @CheckForSigned int getItemNetworkId(@Nonnull Material material) {
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
	public static @Nullable Material getItemByNetworkId(@Nonnegative int id) {
		return ItemMaterialLookup.getByRuntimeId(id);
	}

	/**
	 * Returns living entity type network id
	 * @param type entity type
	 * @return id
	 */
	public static @CheckForSigned int getEntityLivingTypeNetworkId(@Nonnull EntityType type) {
		return NetworkEntityType.getByBukkitType(type).getNetworkTypeId();
	}

	/**
	 * Returns object entity type network id
	 * @param type entity type
	 * @return id
	 */
	public static @CheckForSigned int getEntityObjectTypeNetworkId(@Nonnull EntityType type) {
		return NetworkEntityType.getByBukkitType(type).getNetworkTypeId();
	}

	/**
	 * Returns living entity type by network id <br>
	 * Returns null if no entity type exists for this id
	 * @param id entity network id
	 * @return entity type
	 */
	public static @Nullable EntityType getEntityLivingTypeByNetworkId(@Nonnegative int id) {
		return NetworkEntityType.getByNetworkTypeId(id).getBukkitType();
	}

	/**
	 * Returns object entity type by network id <br>
	 * Returns null if no entity type exists for this id
	 * @param id entity network id
	 * @return entity type
	 */
	public static @Nullable EntityType getEntityObjectTypeByNetworkId(@Nonnegative int id) {
		return NetworkEntityType.getByNetworkTypeId(id).getBukkitType();
	}

}
