package protocolsupport.api.remapper;

import org.apache.commons.lang3.Validate;
import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.LegacyItemType;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.ItemMaterialLookup;

public class ItemRemapperControl {

	/**
	 * Resets all items remaps to default ones
	 */
	public static void resetToDefault() {
		LegacyItemType.REGISTRY.applyDefaultRemaps();
	}

	private final ArrayBasedIdRemappingTable table;

	public ItemRemapperControl(ProtocolVersion version) {
		Validate.isTrue(version.isSupported(), "Can't control item remapping for unsupported version");
		table = LegacyItemType.REGISTRY.getTable(version);
	}

	/**
	 * Returns remap for specified material
	 * @param material {@link Material}
	 * @return remap for specified material
	 */
	public Material getRemap(Material material) {
		return ItemMaterialLookup.getByRuntimeId(getRemap(ItemMaterialLookup.getRuntimeId(material)));
	}

	/**
	 * Returns remap for specified item id
	 * @param id item id
	 * @return remap for specified item id
	 */
	public int getRemap(int id) {
		return table.getRemap(id);
	}

	/**
	 * Sets remap from one material to another
	 * @param from {@link Material} which will be remapped
	 * @param to {@link Material} to which remap will occur
	 */
	public void setRemap(Material from, Material to) {
		setRemap(ItemMaterialLookup.getRuntimeId(from), ItemMaterialLookup.getRuntimeId(to));
	}

	/**
	 * Sets remap from one runtime id to another
	 * @param from runtime item id which will be remapped
	 * @param to runtime item id to which remap will occur
	 */
	public void setRemap(int from, int to) {
		table.setRemap(from, to);
	}

}
