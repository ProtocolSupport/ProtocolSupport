package protocolsupport.protocol.transformer.mcpe;

import org.bukkit.util.Vector;

import gnu.trove.map.hash.TIntObjectHashMap;

public class ItemInfoStorage {

	private final TIntObjectHashMap<Vector> items = new TIntObjectHashMap<Vector>();

	public void addItemInfo(int entityId, float locX, float locY, float locZ) {
		items.put(entityId, new Vector(locX, locY, locZ));
	}

	public Vector getItemInfo(int entityId) {
		return items.get(entityId);
	}

	public void removeItemsInfo(int[] ids) {
		for (int id : ids) {
			items.remove(id);
		}
	}

}
