package protocolsupport.api.remapper;

import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;

public class ItemRemapperControl {

	private ProtocolVersion version;

	public ItemRemapperControl(ProtocolVersion version) {
		this.version = version;
		switch (version) {
			case MINECRAFT_1_8: {
				throw new IllegalArgumentException("Remapper for version "+version+" doesn't exist");
			}
			case UNKNOWN:
			case NOT_SET: {
				throw new IllegalArgumentException(version+" is not a valid protocol version");
			}
			default: {
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void setRemap(Material from, Material to) {
		setRemap(from.getId(), to.getId());
	}

	public void setRemap(int from, int to) {
		switch (version) {
			case MINECRAFT_1_7_10:
			case MINECRAFT_1_7_5: {
				protocolsupport.protocol.transformer.v_1_7.remappers.ItemIDRemapper.setRemap(from, to);
				break;
			}
			case MINECRAFT_1_6_4:
			case MINECRAFT_1_6_2: {
				protocolsupport.protocol.transformer.v_1_6.remappers.ItemIDRemapper.setRemap(from, to);
				break;
			}
			case MINECRAFT_1_5_2: {
				protocolsupport.protocol.transformer.v_1_5.remappers.ItemIDRemapper.setRemap(from, to);
				break;
			}
			default: {
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public Material getRemap(Material material) {
		return Material.getMaterial(getRemap(material.getId()));
	}

	public int getRemap(int id) {
		switch (version) {
			case MINECRAFT_1_7_10:
			case MINECRAFT_1_7_5: {
				return protocolsupport.protocol.transformer.v_1_7.remappers.ItemIDRemapper.replaceItemId(id);
			}
			case MINECRAFT_1_6_4:
			case MINECRAFT_1_6_2: {
				return protocolsupport.protocol.transformer.v_1_6.remappers.ItemIDRemapper.replaceItemId(id);
			}
			case MINECRAFT_1_5_2: {
				return protocolsupport.protocol.transformer.v_1_5.remappers.ItemIDRemapper.replaceItemId(id);
			}
			default: {
				return -1;
			}
		}
	}

}
