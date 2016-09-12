package protocolsupport.api.remapper;

import org.bukkit.Material;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable.ArrayBasedIdRemappingTable;

public class BlockRemapperControl {

	private final ArrayBasedIdRemappingTable table;

	public BlockRemapperControl(ProtocolVersion version) {
		switch (version) {
			case UNKNOWN: {
				throw new IllegalArgumentException(version+" is not a valid protocol version");
			}
			default: {
				table = IdRemapper.BLOCK.getTable(version);
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void setRemap(Material from, Material to) {
		setRemap(from.getId(), to.getId());
	}

	public void setRemap(int from, int to) {
		for (int i = 0; i < 16; i++) {
			setRemap(from, i, to, i);
		}
	}

	@Deprecated
	public Material getRemap(Material material) {
		return Material.getMaterial(getRemap(material.getId()));
	}

	@Deprecated
	public int getRemap(int id) {
		return id(table.getRemap(combinedId(id, 0)));
	}

	public MaterialAndData getRemap(MaterialAndData entry) {
		int combinedId = table.getRemap(combinedId(entry.getId(), entry.getData()));
		return new MaterialAndData(id(combinedId), data(combinedId));
	}

	public void setRemap(MaterialAndData from, MaterialAndData to) {
		setRemap(from.getId(), from.getData(), to.getId(), to.getData());
	}

	@SuppressWarnings("deprecation")
	public void setRemap(Material matFrom, int dataFrom, Material matTo, int dataTo) {
		setRemap(matFrom.getId(), dataFrom, matTo.getId(), dataTo);
	}

	public void setRemap(int idFrom, int dataFrom, int idTo, int dataTo) {
		table.setRemap(combinedId(idFrom, dataFrom), combinedId(idTo, dataTo));
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

	private static int combinedId(int id, int data) {
		return (id << 4) | (data & 0xF);
	}

	private static int id(int combinedId) {
		return combinedId >> 4;
	}

	private static int data(int combinedId) {
		return combinedId & 0xF;
	}

}
