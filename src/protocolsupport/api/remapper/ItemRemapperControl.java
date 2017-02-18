package protocolsupport.api.remapper;

import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;

public class ItemRemapperControl {

	private final ArrayBasedIdRemappingTable table;

	public ItemRemapperControl(ProtocolVersion version) {
		switch (version) {
			case UNKNOWN: {
				throw new IllegalArgumentException(version+" is not a valid protocol version");
			}
			default: {
				table = ItemStackRemapper.ITEM_ID_REMAPPING_REGISTRY.getTable(version);
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void setRemap(Material from, Material to) {
		setRemap(from.getId(), to.getId());
	}

	public void setRemap(int from, int to) {
		table.setRemap(from, to);
	}

	@SuppressWarnings("deprecation")
	public Material getRemap(Material material) {
		return Material.getMaterial(getRemap(material.getId()));
	}

	public int getRemap(int id) {
		return table.getRemap(id);
	}

}
