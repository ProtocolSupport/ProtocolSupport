package protocolsupport.api.remapper;

import java.util.Arrays;

import org.apache.commons.lang3.Validate;
import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class BlockRemapperControl {

	/**
	 * Resets all block remaps to default ones
	 */
	public static void resetToDefault() {
		IdRemapper.BLOCK.applyDefaultRemaps();
	}

	protected final ArrayBasedIdRemappingTable table;

	public BlockRemapperControl(ProtocolVersion version) {
		Validate.isTrue(version.isSupported(), "Can't control block remapping for unsupported version");
		table = IdRemapper.BLOCK.getTable(version);
	}

	/**
	 * Sets remap from one blockstate runtime id to another
	 * @param from blockstate runtime which will be remapped
	 * @param to blockstate runtime to which remap will occur
	 */
	public void setRemap(int from, int to) {
		table.setRemap(from, to);
	}

	/**
	 * Returns remap for specified blockstate runtime id
	 * @param id blockstate runtime id
	 * @return remap for specified blockstate runtime id
	 */
	public int getRemap(int id) {
		return table.getRemap(id);
	}







	/**
	 * Returns remap for specified material (right now it just returns itself)
	 * @param material {@link Material}
	 * @return remap for specified material
	 * @deprecated blockdata now represents full blockstate
	 */
	public Material getRemap(Material material) {
		return material;
	}

	/**
	 * Sets remap from one material to another for all data (right now does nothing)
	 * @param from {@link Material} which will be remapped
	 * @param to {@link Material} to which remap will occur
	 */
	public void setRemap(Material from, Material to) {
	}

	/**
	 * Returns remap for specified material and data (right now it just returns itself)
	 * @param entry {@link MaterialAndData}
	 * @return remap for specified material and data
	 * @deprecated data no longer exists
	 */
	public MaterialAndData getRemap(MaterialAndData entry) {
		return entry;
	}

	/**
	 * Sets remap for specified material and data (right now does nothing)
	 * @param from {@link MaterialAndData} which will be remapped
	 * @param to {@link MaterialAndData} to which remap will occur
	 * @deprecated data no longer exists
	 */
	public void setRemap(MaterialAndData from, MaterialAndData to) {
		setRemap(from.getId(), to.getId());
	}

	/**
	 * Sets remap for specified material and data (right now does nothing)
	 * @param matFrom {@link Material} which will be remapped
	 * @param dataFrom block data which will be remapped
	 * @param matTo {@link Material} to which remap will occur
	 * @param dataTo block data to which remap will occur
	 * @deprecated data no longer exists
	 */
	public void setRemap(Material matFrom, int dataFrom, Material matTo, int dataTo) {
	}

	/**
	 * Sets remap for specified material and data (right now does nothing)
	 * @param idFrom block id which will be remapped
	 * @param dataFrom block data which will be remapped
	 * @param idTo block id to which remap will occur
	 * @param dataTo block data to which remap will occur
	 * @deprecated data no longer exists
	 */
	public void setRemap(int idFrom, int dataFrom, int idTo, int dataTo) {
	}

	/**
	 * @deprecated data no longer exists
	 */
	public static class MaterialAndData {
		private final int id;
		private final int data;

		public MaterialAndData(Material material, int data) {
			this(material.getId(), data);
		}

		public MaterialAndData(int id, int data) {
			this.id = id;
			this.data = data;
		}

		public Material getMaterial() {
			return Arrays.stream(Material.values()).filter(m -> m.getId() == id).findAny().orElse(null);
		}

		public int getId() {
			return id;
		}

		public int getData() {
			return data;
		}
	}

}
