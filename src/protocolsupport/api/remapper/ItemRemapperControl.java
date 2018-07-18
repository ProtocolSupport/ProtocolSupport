package protocolsupport.api.remapper;

import org.apache.commons.lang3.Validate;
import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.remapper.BlockRemapperControl.MaterialAndData;
import protocolsupport.protocol.typeremapper.itemstack.LegacyItemType;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;

@SuppressWarnings("deprecation")
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









	/**
	 * Sets remap from one material to another material and data for all data
	 * @param from {@link Material} which will be remapped
	 * @param matTo {@link Material} to which remap will occur
	 * @param dataTo data to which remap will occur
	 * @deprecated data no longer exists
	 */
	@Deprecated
	public void setRemap(Material from, Material matTo, int dataTo) {
		setRemap(from, matTo);
	}

	/**
	 * Sets remap from one id to another for all data
	 * @param from item id which will be remapped
	 * @param idTo item id to which remap will occur
	 * @param dataTo data to which remap will occur
	 * @deprecated data no longer exists
	 */
	public void setRemap(int from, int idTo, int dataTo) {
		setRemap(from, idTo);
	}

	/**
	 * Returns remap for specified material and data
	 * @param entry {@link MaterialAndData}
	 * @return remap for specified material and data
	 * @deprecated data no longer exists
	 */
	public MaterialAndData getRemap(MaterialAndData entry) {
		Validate.inclusiveBetween(0, MinecraftData.ID_MAX, entry.getId());
		return new MaterialAndData(getRemap(entry.getId()), 0);
	}

	/**
	 * Sets remap for specified material and data
	 * @param from {@link MaterialAndData} which will be remapped
	 * @param to {@link MaterialAndData} to which remap will occur
	 * @deprecated data no longer exists
	 */
	public void setRemap(MaterialAndData from, MaterialAndData to) {
		setRemap(from.getId(), to.getId());
	}

	/**
	 * Sets remap for specified material and data
	 * @param matFrom {@link Material} which will be remapped
	 * @param dataFrom item data which will be remapped
	 * @param matTo {@link Material} to which remap will occur
	 * @param dataTo item data to which remap will occur
	 * @deprecated data no longer exists
	 */
	public void setRemap(Material matFrom, int dataFrom, Material matTo, int dataTo) {
		setRemap(matFrom, matTo);
	}

	/**
	 * Sets remap for specified material and data
	 * @param idFrom item id which will be remapped
	 * @param dataFrom item data which will be remapped
	 * @param idTo item id to which remap will occur
	 * @param dataTo item data to which remap will occur
	 * @deprecated data no longer exists
	 */
	public void setRemap(int idFrom, int dataFrom, int idTo, int dataTo) {
		setRemap(idFrom, idTo);
	}

}
