package protocolsupport.api.remapper;

import org.apache.commons.lang3.Validate;
import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;

public class BlockRemapperControl {

	private final ArrayBasedIdRemappingTable table;

	public BlockRemapperControl(ProtocolVersion version) {
		Validate.isTrue(version.isSupported(), "Can't control block remapping for unsupported version");
		table = IdRemapper.BLOCK.getTable(version);
	}

	/**
	 * Sets remap from one material to another for all data
	 * @param from {@link Material} which will be remapped
	 * @param to {@link Material} to which remap will occur
	 */
	@SuppressWarnings("deprecation")
	public void setRemap(Material from, Material to) {
		setRemap(from.getId(), to.getId());
	}

	/**
	 * Sets remap from one item id to another for all data
	 * @param from item id which will be remapped
	 * @param to item id to which remap will occur
	 */
	public void setRemap(int from, int to) {
		for (int i = 0; i < MinecraftData.BLOCK_DATA_MAX; i++) {
			setRemap(from, i, to, i);
		}
	}

	/**
	 * Returns remap for specified material
	 * @param material {@link Material}
	 * @return remap for specified material
	 * @deprecated returns material remap for data 0
	 */
	@Deprecated
	public Material getRemap(Material material) {
		return Material.getMaterial(getRemap(material.getId()));
	}

	/**
	 * Returns remap for specified item id
	 * @param id item id
	 * @return remap for specified item id
	 * @deprecated returns item id remap for data 0
	 */
	@Deprecated
	public int getRemap(int id) {
		return MinecraftData.getBlockIdFromState(table.getRemap(MinecraftData.getBlockStateFromIdAndData(id, 0)));
	}

	/**
	 * Returns remap for specified material and data
	 * @param entry {@link MaterialAndData}
	 * @return remap for specified material and data
	 */
	public MaterialAndData getRemap(MaterialAndData entry) {
		Validate.inclusiveBetween(0, MinecraftData.BLOCK_ID_MAX, entry.getId());
		Validate.inclusiveBetween(0, MinecraftData.BLOCK_DATA_MAX, entry.getData());
		int combinedId = table.getRemap(MinecraftData.getBlockStateFromIdAndData(entry.getId(), entry.getData()));
		return new MaterialAndData(MinecraftData.getBlockIdFromState(combinedId), MinecraftData.getBlockDataFromState(combinedId));
	}

	/**
	 * Sets remap for specified material and data
	 * @param from {@link MaterialAndData} which will be remapped
	 * @param to {@link MaterialAndData} to which remap will occur
	 */
	public void setRemap(MaterialAndData from, MaterialAndData to) {
		setRemap(from.getId(), from.getData(), to.getId(), to.getData());
	}

	/**
	 * Sets remap for specified material and data
	 * @param matFrom {@link Material} which will be remapped
	 * @param dataFrom item data which will be remapped
	 * @param matTo {@link Material} to which remap will occur
	 * @param dataTo item data to which remap will occur
	 */
	@SuppressWarnings("deprecation")
	public void setRemap(Material matFrom, int dataFrom, Material matTo, int dataTo) {
		setRemap(matFrom.getId(), dataFrom, matTo.getId(), dataTo);
	}

	/**
	 * Sets remap for specified material and data
	 * @param idFrom item id which will be remapped
	 * @param dataFrom item data which will be remapped
	 * @param idTo item id to which remap will occur
	 * @param dataTo item data to which remap will occur
	 */
	public void setRemap(int idFrom, int dataFrom, int idTo, int dataTo) {
		Validate.inclusiveBetween(0, MinecraftData.BLOCK_ID_MAX, idFrom);
		Validate.inclusiveBetween(0, MinecraftData.BLOCK_DATA_MAX, dataFrom);
		Validate.inclusiveBetween(0, MinecraftData.BLOCK_ID_MAX, idTo);
		Validate.inclusiveBetween(0, MinecraftData.BLOCK_DATA_MAX, dataTo);
		table.setRemap(MinecraftData.getBlockStateFromIdAndData(idFrom, dataFrom), MinecraftData.getBlockStateFromIdAndData(idTo, dataTo));
	}

	public static class MaterialAndData {
		private final int id;
		private final int data;

		@SuppressWarnings("deprecation")
		public MaterialAndData(Material material, int data) {
			this(material.getId(), data);
		}

		public MaterialAndData(int id, int data) {
			this.id = id;
			this.data = data;
		}

		@SuppressWarnings("deprecation")
		public Material getMaterial() {
			return Material.getMaterial(id);
		}

		public int getId() {
			return id;
		}

		public int getData() {
			return data;
		}
	}

}
