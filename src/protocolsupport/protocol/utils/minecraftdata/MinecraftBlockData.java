package protocolsupport.protocol.utils.minecraftdata;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.block.data.BlockData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import protocolsupport.protocol.utils.NamespacedKeyUtils;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class MinecraftBlockData {

	private MinecraftBlockData() {
	}

	public static final int BLOCK_COUNT;

	public static final int BLOCKDATA_COUNT;

	private static final Map<Material, BlockInfo> blockInfoByMaterial = new EnumMap<>(Material.class);
	private static final BlockInfo[] blockInfoByNetworkId;

	private static final Map<BlockData, BlockDataInfo> blockdataInfoByBlockdata = new HashMap<>();
	private static final BlockDataInfo[] blockdataInfoByNetworkId;

	static {
		JsonArray blockJsonArray = ResourceUtils.getAsJsonArray(MinecraftDataResourceUtils.getResourcePath("block.json"));
		BlockInfo[] blocks = new BlockInfo[blockJsonArray.size()];
		int blockdataCount = 0;
		for (int blockIndex = 0; blockIndex < blocks.length; blockIndex++) {
			JsonObject blockJsonObject = JsonUtils.getAsJsonObject(blockJsonArray.get(blockIndex), "block array element");
			JsonArray blockdataJsonArray = JsonUtils.getJsonArray(blockJsonObject, "data");
			BlockDataInfo[] blockdataArray = new BlockDataInfo[blockdataJsonArray.size()];
			blockdataCount += blockdataArray.length;
			for (int blockdataIndex = 0; blockdataIndex < blockdataJsonArray.size(); blockdataIndex++) {
				JsonObject blockdataJsonObject = JsonUtils.getAsJsonObject(blockdataJsonArray.get(blockdataIndex), "block data array element");
				blockdataArray[blockdataIndex] = new BlockDataInfo(
					JsonUtils.getString(blockdataJsonObject, "name"),
					JsonUtils.getInt(blockdataJsonObject, "network_id")
				);
			}
			blocks[blockIndex] = new BlockInfo(
				JsonUtils.getString(blockJsonObject, "name"),
				JsonUtils.getInt(blockJsonObject, "network_id"),
				blockdataArray
			);
		}

		BLOCK_COUNT = blocks.length;
		BLOCKDATA_COUNT  = blockdataCount;
		blockInfoByNetworkId = new BlockInfo[BLOCK_COUNT];
		blockdataInfoByNetworkId = new BlockDataInfo[BLOCKDATA_COUNT];
		for (BlockInfo block : blocks) {
			blockInfoByMaterial.put(block.material, block);
			blockInfoByNetworkId[block.networkId] = block;
			for (BlockDataInfo data : block.data) {
				blockdataInfoByBlockdata.put(data.blockdata, data);
				blockdataInfoByNetworkId[data.networkId] = data;
			}
		}
	}

	public static @Nonnull Collection<BlockInfo> getBlockInfoCollection() {
		return Collections.unmodifiableCollection(blockInfoByMaterial.values());
	}

	public static @Nonnull Collection<BlockDataInfo> getBlockDataInfoCollection() {
		return Collections.unmodifiableCollection(blockdataInfoByBlockdata.values());
	}

	public static @Nullable BlockInfo getBlockInfoByMaterial(@Nonnull Material material) {
		return blockInfoByMaterial.get(material);
	}

	public static @Nullable BlockInfo getBlockInfoByNetworkId(@Nonnegative int networkId) {
		if ((networkId >= 0) && (networkId < blockInfoByNetworkId.length)) {
			return blockInfoByNetworkId[networkId];
		} else {
			return null;
		}
	}

	public static @Nullable BlockDataInfo getBlockDataInfoByBlockData(@Nonnull BlockData blockdata) {
		return blockdataInfoByBlockdata.get(blockdata);
	}

	public static @Nullable BlockDataInfo getBlockDataInfoByNetworkId(@Nonnegative int networkId) {
		if ((networkId >= 0) && (networkId < blockdataInfoByNetworkId.length)) {
			return blockdataInfoByNetworkId[networkId];
		} else {
			return null;
		}
	}

	public static class BlockInfo {

		protected final Material material;
		protected final int networkId;
		protected BlockDataInfo[] data;

		protected BlockInfo(@Nonnull String name, @Nonnegative int networkId, @Nonnull BlockDataInfo[] data) {
			this.material = Registry.MATERIAL.get(NamespacedKeyUtils.fromString(name));
			this.networkId = networkId;
			this.data = data;
			for (BlockDataInfo info : data) {
				info.block = this;
			}
		}

		public @Nonnull String getName() {
			return material.getKey().toString();
		}

		public @Nonnull Material getMaterial() {
			return material;
		}

		public @Nonnegative int getNetworkId() {
			return networkId;
		}

		public @Nonnull BlockDataInfo[] getBlockDataArray() {
			return data.clone();
		}

	}

	public static class BlockDataInfo {

		protected BlockInfo block;

		protected final BlockData blockdata;
		protected final int networkId;

		protected BlockDataInfo(@Nonnull String name, @Nonnegative int networkId) {
			this.blockdata = Bukkit.createBlockData(name);
			this.networkId = networkId;
		}

		public @Nonnull BlockInfo getBlock() {
			return block;
		}

		public @Nonnull String getName() {
			return blockdata.getAsString();
		}

		public @Nonnull BlockData getBlockData() {
			return blockdata.clone();
		}

		public @Nonnegative int getNetworkId() {
			return networkId;
		}

	}

}
