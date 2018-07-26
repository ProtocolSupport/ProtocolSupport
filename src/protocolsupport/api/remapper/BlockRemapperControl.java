package protocolsupport.api.remapper;

import org.apache.commons.lang3.Validate;
import org.bukkit.block.data.BlockData;

import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.ServerPlatform;

public class BlockRemapperControl {

	/**
	 * Resets all block remaps to default ones
	 */
	public static void resetToDefault() {
		LegacyBlockData.REGISTRY.applyDefaultRemaps();
	}

	protected final ArrayBasedIdRemappingTable table;

	public BlockRemapperControl(ProtocolVersion version) {
		Validate.isTrue(version.isSupported(), "Can't control block remapping for unsupported version");
		table = LegacyBlockData.REGISTRY.getTable(version);
	}

	/**
	 * Sets remap from one blockstate runtime id to another
	 * @param from blockstate runtime id which will be remapped
	 * @param to blockstate runtime id to which remap will occur
	 */
	public void setRemap(int from, int to) {
		table.setRemap(from, to);
	}

	/**
	 * Returns remap for specified blockstate runtime id
	 * @param id blockstate runtime od id
	 * @return remap for specified blockstate runtime id
	 */
	public int getRemap(int id) {
		return table.getRemap(id);
	}

	/**
	 * Sets remap from one blockstate runtime id to another
	 * @param from blockstate which will be remapped
	 * @param to blockstate to which remap will occur
	 */
	public void setRemap(BlockData from, BlockData to) {
		PlatformUtils utils = ServerPlatform.get().getMiscUtils();
		table.setRemap(utils.getBlockDataNetworkId(from), utils.getBlockDataNetworkId(to));
	}

	/**
	 * Returns remap for specified blockstate runtime id
	 * @param id blockstate runtime od id
	 * @return remap for specified blockstate runtime id
	 */
	public BlockData getRemap(BlockData id) {
		return MaterialAPI.getBlockDataByNetworkId(table.getRemap(MaterialAPI.getBlockDataNetworkId(id)));
	}

}
