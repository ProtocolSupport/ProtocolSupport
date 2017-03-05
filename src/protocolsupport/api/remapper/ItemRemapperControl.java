package protocolsupport.api.remapper;

import org.apache.commons.lang3.Validate;
import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;

public class ItemRemapperControl {

	private final ArrayBasedIdRemappingTable table;

	public ItemRemapperControl(ProtocolVersion version) {
		Validate.isTrue(version.isSupported(), "Can't control item remapping for unsupported version");
		table = ItemStackRemapper.ITEM_ID_REMAPPING_REGISTRY.getTable(version);
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
	 * Sets remap from one id to another for all data
	 * @param from item id which will be remapped
	 * @param to item id to which remap will occur
	 */
	public void setRemap(int from, int to) {
		table.setRemap(from, to);
	}

	/**
	 * Returns remap for specified material
	 * @param material {@link Material}
	 * @return remap for specified material
	 */
	@SuppressWarnings("deprecation")
	public Material getRemap(Material material) {
		return Material.getMaterial(getRemap(material.getId()));
	}

	/**
	 * Returns remap for specified item id
	 * @param id item id
	 * @return remap for specified item id
	 */
	public int getRemap(int id) {
		return table.getRemap(id);
	}

}
